package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Comment;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl extends AbstractRepository<Comment, Long> implements CommentRepository {

    public CommentRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Comment.class);
    }

    public List<Comment> getAll(Long projectId, Long taskId) {
        return entityManager.createQuery("SELECT c FROM Comment c JOIN FETCH c.task t WHERE t.id = :taskId AND t.project.id = :projectId", Comment.class)
                .setParameter("taskId", taskId)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public Optional<Comment> getById(Long projectId, Long taskId, Long id) {
        try {
            return Optional.ofNullable(entityManager.createQuery(
                            "SELECT c FROM Comment c " +
                                    "JOIN FETCH c.task t " +
                                    "WHERE t.id = :taskId AND t.project.id = :projectId AND c.id = :id", Comment.class)
                    .setParameter("projectId", projectId)
                    .setParameter("taskId", taskId)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void delete(Long projectId, Long taskId, Long id) {
        getById(projectId, taskId, id).ifPresent(entityManager::remove);
    }

    public Optional<Comment> update(Long projectId, Long taskId, Long id, Comment updatedEntity) {
        try {
            Comment existingComment = entityManager.createQuery(
                            "SELECT c FROM Comment c " +
                                    "JOIN FETCH c.task t " +
                                    "WHERE t.id = :taskId AND t.project.id = :projectId AND c.id = :id", Comment.class)
                    .setParameter("projectId", projectId)
                    .setParameter("taskId", taskId)
                    .setParameter("id", id)
                    .getSingleResult();

            if (existingComment != null) {
                existingComment.setText(updatedEntity.getText());
                entityManager.merge(existingComment);
                return Optional.of(existingComment);
            } else {
                return Optional.empty();
            }
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}

