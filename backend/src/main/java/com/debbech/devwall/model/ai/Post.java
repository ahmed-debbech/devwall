package com.debbech.devwall.model.ai;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String createdAt;
    private String body;

    @OneToOne
    private WriteRequest writeRequest;
    @OneToOne
    private WriteResponse writeResponse;

}
