package com.MASOWAC.blogfy.controllers;

import com.MASOWAC.blogfy.models.Likes;
import com.MASOWAC.blogfy.repositories.CommentsRepository;
import com.MASOWAC.blogfy.repositories.LikesRepository;
import com.MASOWAC.blogfy.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikesRepository likeRepo;

    @Autowired
    private CommentsRepository commentRepo;

    @Autowired
    private UsersRepository userRepo;

    @PostMapping("/comment/{commentId}/user/{userId}")
    public ResponseEntity<?> likeComment(@PathVariable Long commentId, @PathVariable Long userId) {
        Optional<Likes> existing = likeRepo.findByCommentsIdAndUserId(commentId, userId);
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("Already liked");
        }

        Likes like = new Likes();
        like.setComments(commentRepo.findById(commentId).get());
        like.setUser(userRepo.findById(userId).get());
        like.setCreatedAt(new Date());

        return ResponseEntity.ok(likeRepo.save(like));
    }

    @GetMapping("/comment/{commentId}/count")
    public Long countLikes(@PathVariable Long commentId) {
        return likeRepo.countByCommentsId(commentId);

    }

    @DeleteMapping("/comment/{commentId}/user/{userId}")
    public ResponseEntity<?> unlikeComment(@PathVariable Long commentId, @PathVariable Long userId) {
        Optional<Likes> like = likeRepo.findByCommentsIdAndUserId(commentId, userId);
        if (like.isPresent()) {
            likeRepo.delete(like.get());
            return ResponseEntity.ok("Unliked");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Like not found");
    }

}

