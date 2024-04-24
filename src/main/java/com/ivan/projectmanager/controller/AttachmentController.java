package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.AttachmentDTO;
import com.ivan.projectmanager.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("projects/{id}/tasks/{id}/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AttachmentController(AttachmentService attachmentService, ObjectMapper objectMapper) {
        this.attachmentService = attachmentService;
        this.objectMapper = objectMapper;
    }

    @GetMapping()

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(attachmentService.getAll());
    }

    @PostMapping("/new")
    public String save(AttachmentDTO attachmentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(attachmentService.save(attachmentDTO));
    }

    @GetMapping("/{id}")
    public String getById(Long id) throws JsonProcessingException {
        Optional<AttachmentDTO> attachmentDTOOptional = attachmentService.getById(id);
        return objectMapper.writeValueAsString(attachmentDTOOptional.orElse(null));
    }

    @PatchMapping("/{id}")
    public String update(Long id, AttachmentDTO attachmentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(attachmentService.update(id, attachmentDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        attachmentService.delete(id);
    }
}
