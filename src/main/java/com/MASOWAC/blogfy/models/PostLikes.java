package com.MASOWAC.blogfy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "liked_by_id"})
)
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Post post;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Users likedBy;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Users unlikedBy;
    private Date likedAt;

    public PostLikes() {
    }

    public PostLikes(Post post, Users likedBy, Users unlikedBy, Date likedAt) {
        this.post = post;
        this.likedBy = likedBy;
        this.unlikedBy = unlikedBy;
        this.likedAt = likedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Users getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Users likedBy) {
        this.likedBy = likedBy;
    }

    public Users getUnlikedBy() {
        return unlikedBy;
    }

    public void setUnlikedBy(Users unlikedBy) {
        this.unlikedBy = unlikedBy;
    }

    public Date getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(Date likedAt) {
        this.likedAt = likedAt;
    }
}