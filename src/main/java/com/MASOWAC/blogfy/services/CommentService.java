package com.MASOWAC.blogfy.services;

import com.MASOWAC.blogfy.enums.CommentStatus;
import com.MASOWAC.blogfy.models.Comments;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.models.Users;
import com.MASOWAC.blogfy.repositories.CommentsRepository;
import com.MASOWAC.blogfy.repositories.PostRepository;
import com.MASOWAC.blogfy.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    private final CommentsRepository commentRepository;
    private final UsersRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentsRepository commentRepository, UsersRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<Comments> getCommentsByPost(Post post) {
        return commentRepository.findByPostAndStatus(post, CommentStatus.APPROVED);
    }
    public Comments addComment(Long postId, Comments comment, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        comment.setPost(post);
        comment.setUser(user);
        comment.setCreatedAt(new Date());

        return commentRepository.save(comment);
    }
    public Comments updateComment(Long id, Comments updatedComment, String username) {
        Comments comment = commentRepository.findById(id).orElseThrow();
        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to edit this comment");
        }
        comment.setContent(updatedComment.getContent());
        return commentRepository.save(comment);
    }


    public void deleteComment(Long id, String username) {
        Comments comment = commentRepository.findById(id).orElseThrow();
        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to delete this comment");
        }
        comment.setStatus(CommentStatus.DELETED);
        commentRepository.save(comment);
    }
}
