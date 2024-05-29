package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.AttachmentDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface AttachmentService {
    Page<AttachmentDTO> getAll(Long projectId, Long taskId, Integer page, Integer size);

    AttachmentDTO save(Long projectId, Long taskId, AttachmentDTO attachmentDTO);

    Optional<AttachmentDTO> getById(Long projectId, Long taskId, Long id);

    Optional<AttachmentDTO> update(Long projectId, Long taskId, Long id, AttachmentDTO updatedAttachmentDTO);

    void delete(Long projectId, Long taskId, Long id);
}
