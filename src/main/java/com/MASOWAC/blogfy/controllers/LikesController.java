package com.MASOWAC.blogfy.controllers;

import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.services.LikesService;
import com.MASOWAC.blogfy.services.PostService;
import com.MASOWAC.blogfy.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikesController {

    private final LikesService likesService;
    private final UserService userService;
    private final PostService postService;

    public LikesController(LikesService likesService, UserService userService, PostService postService) {
        this.likesService = likesService;
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/toggle/{postId}")
    public ResponseEntity<?> toggleLike(@PathVariable Long postId, @RequestParam Long id) {
        Optional<Users> userOptional = userService.getUserById(id);
        Post post = postService.getPostById(postId);

        if (userOptional.isEmpty() || post == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(likesService.toggleLikePost(userOptional.get(), post));
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<?> countLikes(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(likesService.countLikesByPost(post));
    }

    @GetMapping("/isLiked/{postId}")
    public ResponseEntity<?> isLiked(@PathVariable Long postId, @RequestParam Long userId) {
        Optional<Users> userOptional = userService.getUserById(userId);
        Post post = postService.getPostById(postId);

        if (userOptional.isEmpty() || post == null) {
            return ResponseEntity.notFound().build();
        }

        boolean isLiked = likesService.isPostLikedByUser(userOptional.get(), post);
        return ResponseEntity.ok(isLiked);
    }
}
