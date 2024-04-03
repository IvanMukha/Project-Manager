package org.example.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.dto.AttachmentDTO;
import org.example.application.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AttachmentController(AttachmentService attachmentService, ObjectMapper objectMapper) {
        this.attachmentService = attachmentService;
        this.objectMapper = objectMapper;
    }

    public void save(AttachmentDTO attachmentDTO) {
        attachmentService.save(attachmentDTO);
    }

    public String getById(int id) throws JsonProcessingException {
        Optional<AttachmentDTO> attachmentDTOOptional = attachmentService.getById(id);
        return objectMapper.writeValueAsString(attachmentDTOOptional.orElse(null));
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(attachmentService.getAll());
    }

    public void update(int id, AttachmentDTO attachmentDTO) {
        attachmentService.update(id, attachmentDTO);
    }

    public void delete(int id) {
        attachmentService.delete(id);
    }
}
