package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.AttachmentDTO;
import com.ivan.projectmanager.service.AttachmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<AttachmentDTO>> getAll(
            @PathVariable("projectId") Long projectId,
            @PathVariable("taskId") Long taskId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<AttachmentDTO> attachments = attachmentService.getAll(projectId, taskId, page, size);
        return ResponseEntity.ok().body(attachments);
    }


    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AttachmentDTO> save(@PathVariable("projectId") Long projectId,
                                              @PathVariable("taskId") Long taskId,
                                              @RequestBody @Valid AttachmentDTO attachmentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        AttachmentDTO savedAttachment = attachmentService.save(projectId, taskId, attachmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAttachment);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("projectId") Long projectId,
                                     @PathVariable("taskId") Long taskId,
                                     @PathVariable("id") Long id) {
        Optional<AttachmentDTO> attachmentDTOOptional = attachmentService.getById(projectId, taskId, id);
        return attachmentDTOOptional
                .map(attachmentDTO -> ResponseEntity.ok().body(attachmentDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AttachmentDTO> update(@PathVariable("projectId") Long projectId,
                                                @PathVariable("taskId") Long taskId,
                                                @PathVariable("id") Long id,
                                                @RequestBody @Valid AttachmentDTO attachmentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Optional<AttachmentDTO> updatedAttachment = attachmentService.update(projectId, taskId, id, attachmentDTO);
        return updatedAttachment.map(attachmentDTO1 -> ResponseEntity.ok().body(attachmentDTO1))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("projectId") Long projectId,
                                       @PathVariable("taskId") Long taskId,
                                       @PathVariable("id") Long id) {
        attachmentService.delete(projectId, taskId, id);
        return ResponseEntity.noContent().build();
    }
}
