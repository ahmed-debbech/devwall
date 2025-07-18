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
import java.util.concurrent.Callable;

public class OpenRouterAiCallThread implements Callable<WriteResponse> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private WriteRequest writeRequest;
    private String host;

    public OpenRouterAiCallThread(WriteRequest writeRequest, String hostip){
        this.writeRequest = writeRequest;
        this.host = hostip;
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

        //seeing if title exists
        
/*        if(body.get)
        wres.setTitle(title);
        wres.setTags(tags);
        wres.setPlainResponse(body);
*/

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
                .header("Authorization", "Bearer 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")
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

