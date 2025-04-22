package com.MASOWAC.blogfy.controllers;

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

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(value = "coverImageFile", required = false) MultipartFile coverImageFile,
            Principal principal
    ) throws IOException {
        Post post = postService.createPost(title, content, coverImageFile, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post updatedPost, Principal principal) throws AccessDeniedException {
        Post updated = postService.updatePost(id, updatedPost, principal.getName());
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
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
//    @GetMapping
//    public ResponseEntity<Map<String, Object>> getAllPosts(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size
//    ) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        Page<Post> postsPage = postService.getAllPosts(pageable);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("posts", postsPage.getContent());
//        response.put("currentPage", postsPage.getNumber());
//        response.put("totalItems", postsPage.getTotalElements());
//        response.put("totalPages", postsPage.getTotalPages());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<Post>> getPostsByTag(@PathVariable String tagName) {
        return ResponseEntity.ok(postService.getPostsByTag(tagName));
    }
}
