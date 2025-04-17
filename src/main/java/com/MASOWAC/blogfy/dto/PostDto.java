package com.MASOWAC.blogfy.dto;

import com.MASOWAC.blogfy.enums.PostStatus;
import com.MASOWAC.blogfy.models.Users;

import java.util.Date;

public class PostDto {

    private String title;
    private String content;

    private Date publishedAt;

    private PostStatus status;
    private Users author;

    public PostDto( String title, String content, Date publishedAt, PostStatus status, Users author) {
        this.title = title;
        this.content = content;
        this.publishedAt = publishedAt;
        this.status = status;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public PostStatus getPostStatus() {
        return status;
    }

    public void setPostStatus(PostStatus status) {
        this.status = status;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }


}
