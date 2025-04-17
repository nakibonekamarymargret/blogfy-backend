package com.MASOWAC.blogfy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class PostImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    private Date createdAt;
    private Date updatedAt;
    @Lob
    @JsonIgnore
    private byte[] imageData;
    private String downloadUrl;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post  post;


}
