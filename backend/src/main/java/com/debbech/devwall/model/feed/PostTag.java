package com.debbech.devwall.model.feed;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class PostTag {


    @Id
    private String name;
    @ManyToMany(mappedBy = "tags")
    @JsonBackReference
    private Set<Post> posts;

    public PostTag() {
        this.posts = new HashSet<>();
    }

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

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
