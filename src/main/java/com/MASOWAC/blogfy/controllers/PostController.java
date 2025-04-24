package com.MASOWAC.blogfy.controllers;

import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            @RequestParam(value = "coverImageUrl", required = false) String coverImageUrl,
            Principal principal
    ) throws IOException {
        Post post = postService.createPost(title, content, coverImageUrl, principal);
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
    public ResponseEntity<List<Map<String, Object>>> getAllPosts() {
        List<Post> allPosts = postService.getAllPosts();
        List<Map<String, Object>> postResponse = new ArrayList<>();

        for (Post post : allPosts) {
            Map<String, Object> singlePost = new HashMap<>();
            singlePost.put("id", post.getId());
            singlePost.put("title", post.getTitle());
            singlePost.put("content", post.getContent());
            singlePost.put("coverImageUrl", post.getCoverImageUrl());
            singlePost.put("author", post.getAuthor().getUsername());
            singlePost.put("publishedAt", post.getPublishedAt());


            postResponse.add(singlePost);
        }

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
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
    @GetMapping("/authors/{authorId}/posts")
    public ResponseEntity<List<Post>> getPostsByAuthor(@PathVariable Long authorId) {
        List<Post> posts = postService.getPostsByAuthor(authorId);
        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(posts); // No posts found
        }
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<Post>> getPostsByTag(@PathVariable String tagName) {
        return ResponseEntity.ok(postService.getPostsByTag(tagName));
    }
    //    Likes
    @PostMapping("/likeunlike/{postId}")
    public ResponseEntity<?> addLikeUnlike(@PathVariable Long postId, Principal principal) {
        postService.likeUnlikePost(postId, principal.getName());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/likeunlike/remove/{id}")
        // Remove User Like On A Blog
    void removeLikeUnlike(@PathVariable Long id) {
        postService.removeLikeUnlikeService(id);
    }

    @GetMapping("/likes/{id}")
        // No Of Unlikes Received By A Blog
    Integer noOfLikesBlog(@PathVariable Long id) {
        return postService.noOfLikes(id);
    }

    @GetMapping("/unlikes/{id}")
        // No Of Likes Received By A plog
    Integer noOfUnlikesBlog(@PathVariable Long id) {
        return postService.noOfUnLikes(id);
    }
} 