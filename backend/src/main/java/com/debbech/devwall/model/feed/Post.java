package com.debbech.devwall.model.feed;


import com.debbech.devwall.model.ai.WriteRequest;
import com.debbech.devwall.model.ai.WriteResponse;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String createdAt;
    private String status;

    @Column(length = 4096)
    private String body;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_name")
    )
    @JsonManagedReference
    private Set<PostTag> tags;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "write_request_id", referencedColumnName = "id")
    private WriteRequest writeRequest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "write_response_id", referencedColumnName = "id")
    private WriteResponse writeResponse;


    public Post() {
        this.tags = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", status='" + status + '\'' +
                ", body='" + body + '\'' +
                ", writeRequest=" + writeRequest +
                ", writeResponse=" + writeResponse +
                '}';
    }

    public Set<PostTag> getTags() {
        return tags;
    }

    public void setTags(Set<PostTag> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
