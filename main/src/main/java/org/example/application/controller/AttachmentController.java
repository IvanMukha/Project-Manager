package org.example.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.dto.AttachmentDTO;
import org.example.application.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class AttachmentController implements BaseController<AttachmentDTO> {
    private final AttachmentService attachmentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AttachmentController(AttachmentService attachmentService, ObjectMapper objectMapper) {
        this.attachmentService = attachmentService;
        this.objectMapper = objectMapper;
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(attachmentService.getAll());
    }

    public String save(AttachmentDTO attachmentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(attachmentService.save(attachmentDTO));
    }

    public String getById(int id) throws JsonProcessingException {
        Optional<AttachmentDTO> attachmentDTOOptional = attachmentService.getById(id);
        return objectMapper.writeValueAsString(attachmentDTOOptional.orElse(null));
    }

    public String update(int id, AttachmentDTO attachmentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(attachmentService.update(id, attachmentDTO));
    }

    public void delete(int id) {
        attachmentService.delete(id);
    }
}
