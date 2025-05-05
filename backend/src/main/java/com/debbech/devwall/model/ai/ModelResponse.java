package com.debbech.devwall.model.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignore all unknown fields
public class ModelResponse {

    private String created_at;
    private String response;
    private Boolean done;

    @Override
    public String toString() {
        return "ModelResponse{" +
                "created_at='" + created_at + '\'' +
                ", response='" + response + '\'' +
                ", done=" + done +
                '}';
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
