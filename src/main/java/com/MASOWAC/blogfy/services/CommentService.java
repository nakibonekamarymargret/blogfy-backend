package com.MASOWAC.blogfy.services;

import com.MASOWAC.blogfy.models.Comments;
import com.MASOWAC.blogfy.models.Post;
import com.MASOWAC.blogfy.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentsRepository commentRepository;

    public List<Comments> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public Comments addComment(Comments comment) {
        comment.setCreatedAt(new Date());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
