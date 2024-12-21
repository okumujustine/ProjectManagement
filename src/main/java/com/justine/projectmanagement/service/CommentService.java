package com.justine.projectmanagement.service;

import com.justine.projectmanagement.exceptions.ResourceNotFoundException;
import com.justine.projectmanagement.model.Comment;
import com.justine.projectmanagement.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));
    }


    public Comment updateComment(Long commentId, String newText) {
        Comment comment = getCommentById(commentId);
        comment.setText(newText);
        return commentRepository.save(comment);
    }
    
    public void deleteComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        commentRepository.delete(comment);
    }

}
