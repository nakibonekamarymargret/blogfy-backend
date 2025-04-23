package com.MASOWAC.blogfy.services;

import com.MASOWAC.blogfy.enums.LikesStatus;
import com.MASOWAC.blogfy.models.Likes;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.repositories.LikesRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LikesService {

    private final LikesRepository likesRepository;

    public LikesService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    public Likes toggleLikePost(Users user, Post post) {
        Optional<Likes> existingLike = likesRepository.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            Likes like = existingLike.get();
            like.setStatus(like.getStatus() == LikesStatus.ACTIVE ? LikesStatus.REMOVED : LikesStatus.ACTIVE);
            like.setUpdatedAt(new Date());
            return likesRepository.save(like);
        } else {
            Likes newLike = new Likes(new Date(), new Date(), LikesStatus.ACTIVE);
            newLike.setUser(user);
            newLike.setPost(post);
            return likesRepository.save(newLike);
        }
    }

    public boolean isPostLikedByUser(Users user, Post post) {
        return likesRepository.findByUserAndPost(user, post)
                .map(like -> like.getStatus() == LikesStatus.ACTIVE)
                .orElse(false);
    }

    public Long countLikesByPost(Post post) {
        return likesRepository.findByPost(post)
                .stream()
                .filter(like -> like.getStatus() == LikesStatus.ACTIVE)
                .count();
    }
}
