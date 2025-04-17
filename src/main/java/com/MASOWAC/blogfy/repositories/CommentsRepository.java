package com.MASOWAC.blogfy.repositories;

import com.MASOWAC.blogfy.models.Comments;
import com.MASOWAC.blogfy.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository  extends JpaRepository<Comments, Long> {
    List<Comments>findByPost(Post post);
}
