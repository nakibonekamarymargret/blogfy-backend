package com.MASOWAC.blogfy.controllers;

import com.MASOWAC.blogfy.models.Likes;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.repositories.PostRepository;
import com.MASOWAC.blogfy.services.LikesService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/like")
@CrossOrigin(origins = "*") // Allow all origins for testing; consider restricting in production
public class LikesController {

    private final LikesService likesService;
    private final PostRepository postRepository;

    public LikesController(LikesService likesService, PostRepository postRepository) {
        this.likesService = likesService;
        this.postRepository = postRepository;
    }

    @GetMapping("/post/{postId}")
    public List<Likes> getLikesByPost(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return likesService.getLikesByPost(post);
    }

    @PostMapping("/post/{postId}")
    public Likes likePost(@PathVariable Long postId, @RequestBody Likes likes, Principal principal) {
        return likesService.likePost(postId, likes, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void unlikePost(@PathVariable Long id, Principal principal) {
        likesService.unlikePost(id, principal.getName());
    }
}