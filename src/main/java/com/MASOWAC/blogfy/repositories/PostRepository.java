package com.MASOWAC.blogfy.repositories;

import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> getPostByTitle(String title);

    List<Post> findByAuthor(Users author);

    List<Post> getPostByTitleAndAuthor(String title, Users author);
//    Page<Post> findAllByOrderByPublishedAtDesc(Pageable pageable);

}
