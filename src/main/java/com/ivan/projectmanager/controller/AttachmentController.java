package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.AttachmentDTO;
import com.ivan.projectmanager.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
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
@RequestMapping("projects/{projectId}/tasks/{taskId}/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping()
    public ResponseEntity<List<AttachmentDTO>> getAll() {
        List<AttachmentDTO> attachments = attachmentService.getAll();
        return ResponseEntity.ok().body(attachments);
    }
    @PostMapping("/new")
    public ResponseEntity<AttachmentDTO> save(@RequestBody AttachmentDTO attachmentDTO) {
        AttachmentDTO savedAttachment = attachmentService.save(attachmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAttachment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Optional<AttachmentDTO> attachmentDTOOptional = attachmentService.getById(id);
        return attachmentDTOOptional
                .map(attachmentDTO -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(attachmentDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AttachmentDTO> update(@PathVariable("id") Long id, @RequestBody AttachmentDTO attachmentDTO) {
        Optional<AttachmentDTO> updatedAttachment = attachmentService.update(id, attachmentDTO);
        return updatedAttachment.map(attachmentDTO1 -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(attachmentDTO1))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        attachmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
