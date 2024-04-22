package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.CommentDTO;
import com.ivan.projectmanager.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CommentController(CommentService commentService, ObjectMapper objectMapper) {
        this.commentService = commentService;
        this.objectMapper = objectMapper;
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentService.getAll());
    }

    public String save(CommentDTO commentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentService.save(commentDTO));
    }

    public String getById(Long id) throws JsonProcessingException {
        Optional<CommentDTO> commentDTOOptional = commentService.getById(id);
        return objectMapper.writeValueAsString(commentDTOOptional.orElse(null));
    }

    public String update(Long id, CommentDTO commentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentService.update(id, commentDTO));
    }

    public void delete(Long id) {
        commentService.delete(id);
    }
}

