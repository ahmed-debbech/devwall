package com.debbech.devwall.model.ai;

public class ModelRequest {
    private String model;
    private String prompt;
    private boolean stream;

    public ModelRequest(String model, String prompt, boolean stream) {
        this.model = model;
        this.prompt = prompt;
        this.stream = stream;
    }

    @Override
    public String toString() {
        return "ModelRequest{" +
                "model='" + model + '\'' +
                ", prompt='" + prompt + '\'' +
                ", stream=" + stream +
                '}';
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }
}
