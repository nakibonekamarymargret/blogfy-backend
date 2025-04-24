package com.MASOWAC.blogfy.models;

import com.MASOWAC.blogfy.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.util.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 7, message = " Title must be at least 7 characters long")
    @NotEmpty(message = "Please provide the title of your blog ")
    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String content;

    private String coverImageUrl;

    // Remove or change JsonFormat to default ISO format
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedAt;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "Africa/Kampala")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @ManyToOne // May posts are created by one user
    @JoinColumn(name = "user_id", nullable = false)
    private Users author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comments> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PostLikes> likesUnlikes;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImages> postImages = new ArrayList<>();

    //    Post and Tags : many posts can have many tags

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonIgnore
    private Set<Tag> tags = new HashSet<>();

    public Post() {}

    public Post(String title, String content, String coverImageUrl, Date publishedAt, Date updatedAt, PostStatus status) {
        this.title = title;
        this.content = content;
        this.coverImageUrl = coverImageUrl;
        this.publishedAt = publishedAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Length(min = 7, message = " Title must be at least 7 characters long") @NotEmpty(message = "Please provide the title of your blog ") String getTitle() {
        return title;
    }

    public void setTitle(@Length(min = 7, message = " Title must be at least 7 characters long") @NotEmpty(message = "Please provide the title of your blog ") String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public List<PostLikes> getLikesUnlikes() {
        return likesUnlikes;
    }

    public void setLikesUnlikes(List<PostLikes> likesUnlikes) {
        this.likesUnlikes = likesUnlikes;
    }

    public List<PostImages> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<PostImages> postImages) {
        this.postImages = postImages;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", coverImageUrl='" + coverImageUrl + '\'' +
                ", publishedAt=" + publishedAt +
                ", updatedAt=" + updatedAt +
                ", status=" + status +
                ", author=" + author +
                ", comments=" + comments +
                ", likesUnlikes=" + likesUnlikes +
                ", postImages=" + postImages +
                ", tags=" + tags +
                '}';
    }
}
