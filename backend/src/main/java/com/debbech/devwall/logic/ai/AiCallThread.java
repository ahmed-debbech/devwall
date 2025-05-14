package com.debbech.devwall.logic.ai;

import com.debbech.devwall.model.ai.ModelRequest;
import com.debbech.devwall.model.ai.ModelResponse;
import com.debbech.devwall.model.ai.WriteRequest;
import com.debbech.devwall.model.ai.WriteResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Callable;

public class AiCallThread implements Callable<WriteResponse> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private WriteRequest writeRequest;
    private String host;

    public AiCallThread(WriteRequest writeRequest, String hostip){
        this.writeRequest = writeRequest;
        this.host = hostip;
    }

    @Override
    public WriteResponse call() throws Exception {


        long startTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        WriteResponse wres = new WriteResponse();
        wres.setReqName(this.writeRequest.getName());
        wres.setResponseGeneratedAt(String.valueOf(startTimestamp));
        wres.setStartTs(startTimestamp);

        ModelRequest modelRequest = new ModelRequest("llama3.2", writeRequest.getBody(), false);
        //String body = generate(modelRequest);
        String body = "the writeer ai....";
        if(body == null){
            wres.setPlainResponse(null);
            long endTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
            wres.setEndTs(endTimestamp);
            return wres;
        }
        wres.setPlainResponse(body);

        ModelRequest modelRequest1 = new ModelRequest("llama3.2", "Give me one title for this and nothing more without quotes just a single title: " + body, false);
        //String title = generate(modelRequest1);
        String title = "hello";
        wres.setTitle(title);

        ModelRequest modelRequest2 = new ModelRequest("llama3.2", "Give me few tags for this only make it one line and sperated with commas and nothing more: " + body, false);
        //String tags = generate(modelRequest2);
        String tags = "java, oop, ko";
        wres.setTags(tags);

        long endTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        wres.setEndTs(endTimestamp);

        return wres;
    }


    private String generate(ModelRequest modelRequest) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(modelRequest);

        String resp = this.doNetworkCall(this.host+":11434", json);

        if(resp != null) {

            ObjectMapper objectMapper = new ObjectMapper();
            ModelResponse responseObj = objectMapper.readValue(resp, ModelResponse.class);

            if(responseObj.isDone()){
                return responseObj.getResponse();
            }
        }
        return null;
    }



    private String doNetworkCall(String aiHost, String json){

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMinutes(1))  // Time to establish the connection
                .readTimeout(Duration.ofMinutes(10))     // Time to wait for the response
                .writeTimeout(Duration.ofMinutes(5))    // Time to send data (if applicable)
                .build();

        RequestBody rb = RequestBody.create(json,MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url("http://"+aiHost+"/api/generate") // Replace with your API
                .post(rb)
                .build();

        try (Response response = client.newCall(request).execute()) { // Auto-closes response
            if (response.isSuccessful() && response.body() != null) {
                //log.info("Response: {}", response.body().string());
                String l = response.body().string();
                return l;
            } else {
                log.error("Request failed with status: {}", response.code());
            }
        } catch (IOException e) {
            log.error("Request failed: {}", e.getMessage());
        }

        return null; //for errors
    }
}
