package com.justine.projectmanagement.controller;


import com.justine.projectmanagement.dto.AddcommentDto;
import com.justine.projectmanagement.model.Comment;
import com.justine.projectmanagement.service.CommentService;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}/get")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PutMapping("/{id}/update")
    public Comment updateComment(@PathVariable Long id,
                                                    @RequestBody @Valid AddcommentDto commentRequest) {
        return commentService.updateComment(id, commentRequest.getText());
    }

    @DeleteMapping("/{id}/delete")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return "Comment with id " + id + " deleted";
    }
}
