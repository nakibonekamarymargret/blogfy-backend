package com.MASOWAC.blogfy.repositories;

import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.PostLikes;
import com.MASOWAC.blogfy.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

    List<PostLikes> findAllByPostId(Long id);


    Optional<PostLikes> findByPostAndLikedBy(Post post, Users likedBy);

    Optional<Object> findByPostIdAndLikedBy_Username(Long postId, String username);
}
