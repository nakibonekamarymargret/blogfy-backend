package com.MASOWAC.blogfy.repositories;

import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> getPostByTitle(String title);


    List<Post> getPostByTitleAndAuthor(String title, Users author);

    //    Page<Post> findAllByOrderByPublishedAtDesc(Pageable pageable);
    @Query("SELECT p FROM Post p JOIN FETCH p.author WHERE p.id = :postId")
    Optional<Post> findByIdWithAuthor(@Param("postId") Long postId);

    List<Post> findByAuthorId(Long authorId);
}
