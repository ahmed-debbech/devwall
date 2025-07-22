package com.debbech.generator.logic.ai;

import com.debbech.generator.model.ai.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class OpenRouterAiCallThread implements Callable<WriteResponse> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private WriteRequest writeRequest;
    private String host;
    private String token;

    public OpenRouterAiCallThread(WriteRequest writeRequest, String hostip, String token){
        this.writeRequest = writeRequest;
        this.host = hostip;
        this.token = token;
    }


    @Override
    public WriteResponse call() throws Exception {

        log.info("Started processing write request {}", this.writeRequest.getName());
        long startTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        WriteResponse wres = new WriteResponse();
        wres.setReqName(this.writeRequest.getName());
        wres.setResponseGeneratedAt(String.valueOf(startTimestamp));
        wres.setStartTs(startTimestamp);
        ModelRequest modelRequest = new ModelRequest("deepseek/deepseek-r1-0528:free", writeRequest.getBody(), false);

        log.info("done generating article for {}", this.writeRequest.getName());

        log.info("checking if title + tags + body exist...");
        String result = generate(modelRequest);
        //log.info("the result of AI {}", result);
        if(result == null) {
            wres.setPlainResponse(null);
            long endTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
            wres.setEndTs(endTimestamp);
            return wres;
        }

        //check if model is thinking (e.g: <think></think>)
        if(result.trim().startsWith("<think>")){
            result = result.substring(result.trim().indexOf("</think>") + 8);
        }

        //see if title exists
        String title = result.split("\n")[0].trim();
        if(title.startsWith("[") && title.endsWith("]")){
            wres.setTitle(title.substring(1,title.length()-1));
        }

        //see if tags exists
        String tags = result.split("\n")[1].trim();
        if(tags.startsWith("[") && tags.endsWith("]")){
            wres.setTags(tags.substring(1,tags.length()-1));
        }

        //see if body exists
        String body = Arrays.stream(result.split("\n")).skip(2).collect(Collectors.joining("\n"));
        if(!body.isEmpty()){
            wres.setPlainResponse(body);
        }

        long endTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        wres.setEndTs(endTimestamp);
        return wres;
    }


    private String generate(ModelRequest modelRequest) throws Exception {
        String json = """ 
                {
                    "model" : "%s",
                    "messages": [{"role": "user", "content": "%s"}],
                    "stream" : false
                }
        """.formatted(modelRequest.getModel(), modelRequest.getPrompt());

        String resp = this.doNetworkCall(this.host, json);

        if(resp != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(resp);
            String created = node.get("created").asText();
            String response = node.get("choices").get(0).get("message").get("content").asText();

            return response;
        }
        return null;
    }
    private String doNetworkCall(String aiHost, String json) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMinutes(1))  // Time to establish the connection
                .readTimeout(Duration.ofMinutes(10))     // Time to wait for the response
                .writeTimeout(Duration.ofMinutes(5))    // Time to send data (if applicable)
                .build();

        RequestBody rb = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(aiHost)
                .post(rb)
                .header("Authorization", "Bearer "+token)
                .build();
        log.info("connecting to OpenRouter...");
        try (Response response = client.newCall(request).execute()) { // Auto-closes response
            if (response.isSuccessful() && response.body() != null) {
                String l = response.body().string();
                log.info("done connecting to OpenRouter");
                return l;
            } else {
                log.error("Request failed with status: {}", response.code());
            }
        } catch (IOException e) {
            log.error("Request failed: {}", e.getMessage());
        }
        return null; // for errors
    }
}

