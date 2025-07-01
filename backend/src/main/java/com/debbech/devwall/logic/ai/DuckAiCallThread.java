package com.debbech.devwall.logic.ai;

import com.debbech.devwall.model.ai.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Callable;

public class DuckAiCallThread implements Callable<WriteResponse>  {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private WriteRequest writeRequest;
    private String host;

    public DuckAiCallThread(WriteRequest writeRequest, String hostip){
        this.writeRequest = writeRequest;
        this.host = hostip;
    }

    private String stringSanitize(String data){
        String escaped = data
                .replaceAll("\\\\", "\\\\\\\\")  // Escape backslash first!
                .replaceAll("\"", "\\\\\"")      // Escape double quotes
                .replaceAll("\b", "\\\\b")
                .replaceAll("\f", "\\\\f")
                .replaceAll("\n", "\\\\n")
                .replaceAll("\r", "\\\\r")
                .replaceAll("\t", "\\\\t");
        return escaped;
    }

    @Override
    public WriteResponse call() throws Exception {

        log.info("Started processing write request {}", this.writeRequest.getName());
        long startTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        WriteResponse wres = new WriteResponse();
        wres.setReqName(this.writeRequest.getName());
        wres.setResponseGeneratedAt(String.valueOf(startTimestamp));
        wres.setStartTs(startTimestamp);

        ModelRequest modelRequest = new ModelRequest("meta-llama/Llama-4-Scout-17B-16E-Instruct", writeRequest.getBody(), false);
        String body = generate(modelRequest);
        if(body == null){
            wres.setPlainResponse(null);
            long endTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
            wres.setEndTs(endTimestamp);
            return wres;
        }
        wres.setPlainResponse(body);
        log.info("done generating body for {}", this.writeRequest.getName());

        String sanitizedBody = stringSanitize(body);

        String lookForTitle = "Give me one title for this and nothing more without quotes just a single title: ";
        lookForTitle += sanitizedBody;
        ModelRequest modelRequest1 = new ModelRequest("meta-llama/Llama-4-Scout-17B-16E-Instruct", lookForTitle, false);
        Thread.sleep(5000);
        String title = generate(modelRequest1);
        wres.setTitle(title);
        log.info("done generating title for {}", this.writeRequest.getName());

        String lookForTags = "Give me few tags for this only make it one line and sperated with commas and single words only and nothing more: ";
        lookForTags += sanitizedBody;
        ModelRequest modelRequest2 = new ModelRequest("meta-llama/Llama-4-Scout-17B-16E-Instruct",  lookForTags, false);
        Thread.sleep(5000);
        String tags = generate(modelRequest2);
        wres.setTags(tags);
        log.info("done generating tags for {}", this.writeRequest.getName());

        long endTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        wres.setEndTs(endTimestamp);

        return wres;
    }


    private String generate(ModelRequest modelRequest) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

//        String json = ow.writeValueAsString(modelRequest);
        String json = """
                {
                    "model": "%s",
                    "metadata": {
                        "toolChoice": {
                            "NewsSearch": false,
                            "VideosSearch": false,
                            "LocalSearch": false,
                            "WeatherForecast": false
                        }
                    },
                    "messages": [
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ],
                    "canUseTools": true
                }
                """.formatted(modelRequest.getModel(), modelRequest.getPrompt());

        String resp = this.doNetworkCall(this.host, json);

        if(resp != null) {
            return resp;
        }
        return null;
    }



    private String doNetworkCall(String aiHost, String json){

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMinutes(1))  // Time to establish the connection
                .readTimeout(Duration.ofMinutes(10))     // Time to wait for the response
                .writeTimeout(Duration.ofMinutes(5))    // Time to send data (if applicable)
                .build();

        RequestBody rb = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(aiHost)
                .post(rb)
                .header("User-Agent", "PostmanRuntime/7.44.1")
                .header("x-vqd-hash-1", "eyJzZXJ2ZXJfaGFzaGVzIjpbIjd1dWpaQjI1aTZYQTRIcG44d3BERkZWbEdVMzI4Q05SSDNQZHdvM2t0dk09IiwicEtLSC9hUDA2WWpGZ1hjbTRxY3lwZFRPZDJVc1VnTm53Vzc5STJXbU9EQT0iXSwiY2xpZW50X2hhc2hlcyI6WyJSSDhrVm1RMHRXRGI0czlJZXJ0S3p1ZnRKVnRjVmFwcndlNjlSWVk2VkpBPSIsIjRuYjFtOFdvSHlsbzM3TkxZeVQzMVovcUp5UDljenZKNXcxbDZFanZqMEk9Il0sInNpZ25hbHMiOnt9LCJtZXRhIjp7InYiOiIzIiwiY2hhbGxlbmdlX2lkIjoiZDUwZWY5NjdiMjVmMTFlZjM3ODI1ZmYyYzA5ZjdlM2RjYmY2OTQ3NTA3NTUzZGY2NWJkMTE4ZWU5MGFmYTQyZGg4amJ0IiwidGltZXN0YW1wIjoiMTc1MTIyMDA1MDUyMiIsIm9yaWdpbiI6Imh0dHBzOi8vZHVja2R1Y2tnby5jb20iLCJzdGFjayI6IkVycm9yXG5hdCB1ZSAoaHR0cHM6Ly9kdWNrZHVja2dvLmNvbS9kaXN0L3dwbS5jaGF0Ljk2OTE2ZTFlZjY5MjdmNDE2ZGY4LmpzOjE6MjM3MTUpXG5hdCBhc3luYyBodHRwczovL2R1Y2tkdWNrZ28uY29tL2Rpc3Qvd3BtLmNoYXQuOTY5MTZlMWVmNjkyN2Y0MTZkZjguanM6MToyNTkwMiIsImR1cmF0aW9uIjoiNyJ9fQ")
                .build();

        log.info("connecting to DuckDuckGoAI...");
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Unexpected code " + response);
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            try (BufferedReader reader = new BufferedReader(response.body().charStream())) {
                String line;
                StringBuilder body= new StringBuilder();
                log.info("talking to DuckDuckGoAI...");
                while ((line = reader.readLine()) != null) {
                    if(line.isEmpty()) continue;
                    if(!line.startsWith("data: {\"cre") && !line.startsWith("data: [DON")) {
                        String l = line.substring(6);
                        DuckModelResponse respo;
                        try {
                            respo = mapper.readValue(l, DuckModelResponse.class);
                        }catch (Exception e){
                            continue;
                        }
                        body.append(respo.getMessage());
                    }
                }
                return body.toString();
            }
        } catch (IOException e) {
            log.error(e.getMessage());

            e.printStackTrace();
            return null;
        }
    }
}
