package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Page<Comment> getAll(Long projectId, Long taskId, Pageable pageable);

    Optional<Comment> getById(Long projectId, Long taskId, Long id);

    Comment save(Comment comment);

    void delete(Long projectId, Long taskId, Long id);

    Optional<Comment> update(Long projectId, Long taskId, Long id, Comment updatedEntity);
}