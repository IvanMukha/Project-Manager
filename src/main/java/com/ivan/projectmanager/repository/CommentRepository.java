package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment> getAll(Long projectId, Long taskId);

    Optional<Comment> getById(Long projectId, Long taskId, Long id);

    Comment save(Comment comment);

    void delete(Long projectId, Long taskId, Long id);

    Optional<Comment> update(Long projectId, Long taskId, Long id, Comment updatedEntity);
}