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


    @Override
    public String toString() {
        return "WriteRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                '}';
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
