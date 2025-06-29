package com.debbech.devwall.model.ai;

public class DuckModelResponse {
    private String role;
    private String message;
    private long created;
    private String id;
    private String action;
    private String model;

    public DuckModelResponse(){

    }
    @Override
    public String toString() {
        return "DuckModelResponse{" +
                "role='" + role + '\'' +
                ", message='" + message + '\'' +
                ", created=" + created +
                ", id='" + id + '\'' +
                ", action='" + action + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    // Getters and Setters

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
