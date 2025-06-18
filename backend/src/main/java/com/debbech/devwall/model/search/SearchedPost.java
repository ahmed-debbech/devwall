package com.debbech.devwall.model.search;


public class SearchedPost {

    private Long id;
    private String title;
    private String createdAt;
    private String status;
    private String randomId;
    private String body;


    public SearchedPost() {
    }
    public SearchedPost(Long id, String title, String createdAt, String status, String randomId, String body) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.status = status;
        this.randomId = randomId;
        this.body = body;
    }

    @Override
    public String toString() {
        return "SearchedPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", status='" + status + '\'' +
                ", randomId='" + randomId + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRandomId() {
        return randomId;
    }

    public void setRandomId(String randomId) {
        this.randomId = randomId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
