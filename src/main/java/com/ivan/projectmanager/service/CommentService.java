package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.CommentDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CommentService {
    Page<CommentDTO> getAll(Long projectId, Long taskId, Integer page, Integer size);

    CommentDTO save(Long projectId, Long taskId, CommentDTO commentDTO);

    Optional<CommentDTO> getById(Long projectId, Long taskId, Long id);

    Optional<CommentDTO> update(Long projectId, Long taskId, Long id, CommentDTO updatedCommentDTO);

    void delete(Long projectId, Long taskId, Long id);
}
