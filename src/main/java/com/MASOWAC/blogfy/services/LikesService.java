package com.MASOWAC.blogfy.services;

import com.MASOWAC.blogfy.enums.LikesStatus;
import com.MASOWAC.blogfy.models.Likes;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.repositories.LikesRepository;
import com.MASOWAC.blogfy.repositories.PostRepository;
import com.MASOWAC.blogfy.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final UsersRepository userRepository;

    public LikesService(LikesRepository likesRepository, PostRepository postRepository, UsersRepository userRepository) {
        this.likesRepository = likesRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Likes> getLikesByPost(Post post) {
        return likesRepository.findLikesByPostAndStatus(post, LikesStatus.ACTIVE);
    }

    public Likes likePost(Long postId, Likes likes, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        likes.setPost(post);
        likes.setUser(user);
        likes.setCreatedAt(new Date());
        likes.setStatus(LikesStatus.ACTIVE);

        return likesRepository.save(likes);
    }

    public void unlikePost(Long id, String username) {
        Likes like = likesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        if (!like.getUser().getUsername().equals(username)) {
            throw new SecurityException("Not authorized to unlike this post");
        }
        likesRepository.deleteById(id);
    }
}
