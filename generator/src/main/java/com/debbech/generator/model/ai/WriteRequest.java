package com.debbech.generator.model.ai;


import jakarta.persistence.*;

@Entity()
public class WriteRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 20000)
    private String body;

    private String title_hash;


    @Override
    public String toString() {
        return "WriteRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", title_hash='" + title_hash + '\'' +
                '}';
    }

    public String getTitle_hash() {
        return title_hash;
    }

    public void setTitle_hash(String title_hash) {
        this.title_hash = title_hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String desc) {
        this.body = desc;
    }
}
