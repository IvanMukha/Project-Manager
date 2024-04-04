package org.example.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.ServiceInterfaces.CommentServiceInterface;
import org.example.application.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class CommentController {
    private final CommentServiceInterface commentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CommentController(CommentServiceInterface commentService, ObjectMapper objectMapper) {
        this.commentService = commentService;
        this.objectMapper = objectMapper;
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentService.getAll());
    }

    public String save(CommentDTO commentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentService.save(commentDTO));
    }

    public String getById(int id) throws JsonProcessingException {
        Optional<CommentDTO> commentDTOOptional = commentService.getById(id);
        return objectMapper.writeValueAsString(commentDTOOptional.orElse(null));
    }

    public String update(int id, CommentDTO commentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentService.update(id, commentDTO));
    }

    public void delete(int id) {
        commentService.delete(id);
    }
}
