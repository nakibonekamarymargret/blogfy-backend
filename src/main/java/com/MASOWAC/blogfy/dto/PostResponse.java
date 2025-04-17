package com.MASOWAC.blogfy.dto;

import com.MASOWAC.blogfy.enums.PostStatus;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.PostImages;
import com.MASOWAC.blogfy.models.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String coverImageUrl;
    private PostStatus status;
    private List<PostImages> postImages;
    private List<String> tagNames;
    private String authorUsername;
    private Date publishedAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.coverImageUrl = post.getCoverImageUrl();
        this.status = post.getStatus();
        this.postImages = post.getPostImages();
        this.tagNames = new ArrayList<>();
        for (Tag tag : post.getTags()) {
            this.tagNames.add(tag.getName());
        }
        this.authorUsername = post.getAuthor().getUsername();
        this.publishedAt = post.getPublishedAt();
    }

    public PostResponse(Long id, String title, String content, String coverImageUrl, PostStatus status, String authorUsername, Date publishedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.coverImageUrl = coverImageUrl;
        this.status = status;
        this.authorUsername = authorUsername;
        this.publishedAt = publishedAt;
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

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public List<PostImages> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<PostImages> postImages) {
        this.postImages = postImages;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public Date getCreatedAt() {
        return publishedAt;
    }

    public void setCreatedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
}
