package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.CommentDTO;
import com.ivan.projectmanager.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}/comments")
@Validated
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<CommentDTO>> getAll(@PathVariable("projectId") Long projectId,
                                                   @PathVariable("taskId") Long taskId) {
        List<CommentDTO> comments = commentService.getAll(projectId, taskId);
        return ResponseEntity.ok().body(comments);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<CommentDTO> save(@PathVariable("projectId") Long projectId,
                                           @PathVariable("taskId") Long taskId,
                                           @RequestBody CommentDTO commentDTO) {
        CommentDTO savedComment = commentService.save(projectId, taskId, commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getById(@PathVariable("projectId") Long projectId,
                                              @PathVariable("taskId") Long taskId,
                                              @PathVariable("id") Long id) {
        Optional<CommentDTO> commentDTOOptional = commentService.getById(projectId, taskId, id);
        return commentDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable("projectId") Long projectId,
                                             @PathVariable("taskId") Long taskId,
                                             @PathVariable("id") Long id,
                                             @RequestBody CommentDTO commentDTO) {
        Optional<CommentDTO> updatedComment = commentService.update(projectId, taskId, id, commentDTO);
        return updatedComment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("projectId") Long projectId,
                                       @PathVariable("taskId") Long taskId,
                                       @PathVariable("id") Long id) {
        commentService.delete(projectId, taskId, id);
        return ResponseEntity.noContent().build();
    }
}