package com.MASOWAC.blogfy.repositories;

import com.MASOWAC.blogfy.models.Comments;
import com.MASOWAC.blogfy.models.Likes;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository  extends JpaRepository<Likes, Long> {
    Optional<Likes>findByUserAndPost(Users user, Post post);
    Optional<Likes>findByUserAndComments(Users user, Comments comments);
    List<Likes>findByPost(Post post);
    List<Likes>findByComments(Comments comments);

}
