package com.MASOWAC.blogfy.repositories;

import com.MASOWAC.blogfy.enums.LikesStatus;
import com.MASOWAC.blogfy.models.Comments;
import com.MASOWAC.blogfy.models.Likes;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByUserAndPost(Users user, Post post);

    Optional<Likes> findByUserAndComments(Users user, Comments comments);

    List<Likes> findByPost(Post post);

    List<Likes> findByComments(Comments comments);

    Optional<Likes> findByCommentsIdAndUserId(Long commentId, Long userId);

    Long countByCommentsId(Long commentId);

    List<Likes> findLikesByPostAndStatus(Post post, LikesStatus likesStatus);

    @Modifying
    @Transactional
    @Query("DELETE FROM Likes l WHERE l.post.id = :postId AND l.user.username = :username")
    void unlikePost(Long postId, String username);
}
