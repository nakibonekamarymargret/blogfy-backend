package com.MASOWAC.blogfy.controllers;


import com.MASOWAC.blogfy.dto.PostRequest;
import com.MASOWAC.blogfy.dto.PostResponse;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

// PostController.java
@RestController
@RequestMapping("/posts")

public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createPost(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(value = "coverImageUrl", required = false) String coverImageUrl, // Expect the URL
            @RequestParam(value = "coverImageFile", required = false) MultipartFile coverImageFile,
            Principal principal
    ) throws IOException {
        Post post = postService.createPost(title, content, coverImageUrl, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequest request, Principal principal) throws AccessDeniedException {
        PostResponse updated = postService.updatePost(id, request, principal.getName());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Principal principal) throws AccessDeniedException {
        postService.deletePost(id, principal.getName());
        return ResponseEntity.ok("Post deleted");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<PostResponse>> getPostsByTag(@PathVariable String tagName) {
        return ResponseEntity.ok(postService.getPostsByTag(tagName));
    }
}


