package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.CommentDTO;
import com.ivan.projectmanager.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/projects/{id}/tasks/{id}/comments")
public class CommentController {
    private final CommentService commentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CommentController(CommentService commentService, ObjectMapper objectMapper) {
        this.commentService = commentService;
        this.objectMapper = objectMapper;
    }

    @GetMapping()
    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentService.getAll());
    }

    @PostMapping("/new")
    public String save(CommentDTO commentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentService.save(commentDTO));
    }

    @GetMapping("/{id}")
    public String getById(Long id) throws JsonProcessingException {
        Optional<CommentDTO> commentDTOOptional = commentService.getById(id);
        return objectMapper.writeValueAsString(commentDTOOptional.orElse(null));
    }

    @PatchMapping("/{id}")
    public String update(Long id, CommentDTO commentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentService.update(id, commentDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        commentService.delete(id);
    }
}

