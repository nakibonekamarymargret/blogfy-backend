package com.MASOWAC.blogfy.controllers;

import com.MASOWAC.blogfy.models.Comments;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.repositories.PostRepository;
import com.MASOWAC.blogfy.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    private final PostRepository postRepository;

    public CommentController(CommentService commentService, PostRepository postRepository) {
        this.commentService = commentService;
        this.postRepository = postRepository;
    }

    @GetMapping("/post/{postId}")
    public List<Comments> getCommentsByPost(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return commentService.getCommentsByPost(post);
    }

    @PostMapping(value = "/post/{postId}", consumes = "application/json")
    public Comments addComment(@PathVariable Long postId, @RequestBody Comments comment, Principal principal) {
        return commentService.addComment(postId, comment, principal.getName());
    }
    @PutMapping("/{id}")
    public Comments updateComment(@PathVariable Long id, @RequestBody Comments updatedComment, Principal principal) {
        return commentService.updateComment(id, updatedComment, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, Principal principal) {
        commentService.deleteComment(id, principal.getName());
    }
}

