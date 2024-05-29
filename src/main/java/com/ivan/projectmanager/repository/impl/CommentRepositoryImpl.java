package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Comment;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CommentRepositoryImpl extends AbstractRepository<Comment, Long> implements CommentRepository {

    public CommentRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Comment.class);
    }

    public Page<Comment> getAll(Long projectId, Long taskId, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root = criteriaQuery.from(Comment.class);

        Predicate taskIdPredicate = criteriaBuilder.equal(root.get("task").get("id"), taskId);
        Predicate projectIdPredicate = criteriaBuilder.equal(root.get("task").get("project").get("id"), projectId);
        Predicate finalPredicate = criteriaBuilder.and(taskIdPredicate, projectIdPredicate);

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Comment> countRoot = countQuery.from(Comment.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

        criteriaQuery.where(finalPredicate);
        TypedQuery<Comment> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Comment> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, totalRows);
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

