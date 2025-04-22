package com.MASOWAC.blogfy.controllers;

import com.MASOWAC.blogfy.models.Comments;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.repositories.PostRepository;
import com.MASOWAC.blogfy.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/post/{postId}")
    public List<Comments> getCommentsByPost(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return commentService.getCommentsByPost(post);
    }

    @PostMapping("/post/{postId}")
    public Comments addComment(@PathVariable Long postId, @RequestBody Comments comment) {
        Post post = postRepository.findById(postId).orElseThrow();
        comment.setPost(post);
        return commentService.addComment(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}

