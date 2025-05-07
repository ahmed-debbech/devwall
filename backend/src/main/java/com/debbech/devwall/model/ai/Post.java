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


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "write_request_id", referencedColumnName = "id")
    private WriteRequest writeRequest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "write_response_id", referencedColumnName = "id")
    private WriteResponse writeResponse;

}
