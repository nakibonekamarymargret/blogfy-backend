package com.MASOWAC.blogfy.dto;

import com.MASOWAC.blogfy.enums.PostStatus;
import com.MASOWAC.blogfy.models.PostImages;

import java.util.List;

public class PostRequest {
    private String title;
    private String content;
    private PostStatus status;
    private List<PostImages> contentImageUrls;
    private List<String> tagNames;
private  String action;//submit,save to draft
    public PostRequest(String title, String content, PostStatus status, String action) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.action = action;
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



    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public List<PostImages> getContentImageUrls() {
        return contentImageUrls;
    }

    public void setContentImageUrls(List<PostImages> contentImageUrls) {
        this.contentImageUrls = contentImageUrls;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
