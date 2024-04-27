package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.CommentDTO;
import com.ivan.projectmanager.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping()
    public ResponseEntity<List<CommentDTO>> getAll() {
        List<CommentDTO> comments = commentService.getAll();
        return ResponseEntity.ok().body(comments);
    }

    @PostMapping("/new")
    public ResponseEntity<CommentDTO> save(@RequestBody CommentDTO commentDTO) {
        CommentDTO savedComment = commentService.save(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getById(@PathVariable("id") Long id) {
        Optional<CommentDTO> commentDTOOptional = commentService.getById(id);
        return commentDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable("id") Long id, @RequestBody CommentDTO commentDTO) {
        Optional<CommentDTO> updatedComment = commentService.update(id, commentDTO);
        return updatedComment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}