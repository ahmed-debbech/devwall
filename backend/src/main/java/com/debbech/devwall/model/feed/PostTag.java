package com.debbech.devwall.model.feed;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class PostTag {


    @Id
    private String name;
    @ManyToMany(mappedBy = "tags")
    @JsonBackReference
    private List<Post> posts;


    @Override
    public String toString() {
        return "PostTag{" +
                ", posts=" + posts +
                ", name='" + name + '\'' +
                '}';
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
