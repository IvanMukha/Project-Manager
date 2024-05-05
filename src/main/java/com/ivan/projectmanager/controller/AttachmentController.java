package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.AttachmentDTO;
import com.ivan.projectmanager.service.AttachmentService;
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
@RequestMapping("projects/{projectId}/tasks/{taskId}/attachments")
@Validated
public class AttachmentController {
    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<AttachmentDTO>> getAll(@PathVariable("projectId") Long projectId,
                                                      @PathVariable("taskId") Long taskId) {
        List<AttachmentDTO> attachments = attachmentService.getAll(projectId, taskId);
        return ResponseEntity.ok().body(attachments);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<AttachmentDTO> save(@PathVariable("projectId") Long projectId,
                                              @PathVariable("taskId") Long taskId,
                                              @RequestBody AttachmentDTO attachmentDTO) {
        AttachmentDTO savedAttachment = attachmentService.save(projectId, taskId, attachmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAttachment);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("projectId") Long projectId,
                                     @PathVariable("taskId") Long taskId,
                                     @PathVariable("id") Long id) {
        Optional<AttachmentDTO> attachmentDTOOptional = attachmentService.getById(projectId, taskId, id);
        return attachmentDTOOptional
                .map(attachmentDTO -> ResponseEntity.ok().body(attachmentDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AttachmentDTO> update(@PathVariable("projectId") Long projectId,
                                                @PathVariable("taskId") Long taskId,
                                                @PathVariable("id") Long id,
                                                @RequestBody AttachmentDTO attachmentDTO) {
        Optional<AttachmentDTO> updatedAttachment = attachmentService.update(projectId, taskId, id, attachmentDTO);
        return updatedAttachment.map(attachmentDTO1 -> ResponseEntity.ok().body(attachmentDTO1))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("projectId") Long projectId,
                                       @PathVariable("taskId") Long taskId,
                                       @PathVariable("id") Long id) {
        attachmentService.delete(projectId, taskId, id);
        return ResponseEntity.noContent().build();
    }
}
