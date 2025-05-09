package com.debbech.devwall.model.feed;


import com.debbech.devwall.model.ai.WriteRequest;
import com.debbech.devwall.model.ai.WriteResponse;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String createdAt;

    @Column(length = 4096)
    private String body;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "write_request_id", referencedColumnName = "id")
    private WriteRequest writeRequest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "write_response_id", referencedColumnName = "id")
    private WriteResponse writeResponse;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", body='" + body + '\'' +
                ", writeRequest=" + writeRequest +
                ", writeResponse=" + writeResponse +
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public WriteRequest getWriteRequest() {
        return writeRequest;
    }

    public void setWriteRequest(WriteRequest writeRequest) {
        this.writeRequest = writeRequest;
    }

    public WriteResponse getWriteResponse() {
        return writeResponse;
    }

    public void setWriteResponse(WriteResponse writeResponse) {
        this.writeResponse = writeResponse;
    }
}
