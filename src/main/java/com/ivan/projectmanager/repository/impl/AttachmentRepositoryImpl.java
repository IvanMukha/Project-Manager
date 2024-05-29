package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Attachment;
import com.ivan.projectmanager.model.Attachment_;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.AttachmentRepository;
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
public class AttachmentRepositoryImpl extends AbstractRepository<Attachment, Long> implements AttachmentRepository {

    public AttachmentRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Attachment.class);
    }

    public Page<Attachment> getAll(Long projectId, Long taskId, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Attachment> criteriaQuery = criteriaBuilder.createQuery(Attachment.class);
        Root<Attachment> root = criteriaQuery.from(Attachment.class);

        Predicate taskIdPredicate = criteriaBuilder.equal(root.get("task").get("id"), taskId);
        Predicate projectIdPredicate = criteriaBuilder.equal(root.get("task").get("project").get("id"), projectId);
        Predicate finalPredicate = criteriaBuilder.and(taskIdPredicate, projectIdPredicate);

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Attachment> rootCount = countQuery.from(Attachment.class);
        countQuery.select(criteriaBuilder.count(rootCount));
        Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

        criteriaQuery.where(finalPredicate);
        TypedQuery<Attachment> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Attachment> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, totalRows);
    }


    public Optional<Attachment> getById(Long projectId, Long taskId, Long id) {
        try {
            return Optional.ofNullable(entityManager.createQuery(
                            "SELECT a FROM Attachment a " +
                                    "JOIN FETCH a.task t " +
                                    "WHERE t.id = :taskId AND t.project.id = :projectId AND a.id = :id", Attachment.class)
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

    public Optional<Attachment> update(Long projectId, Long taskId, Long id, Attachment updatedEntity) {
        try {
            Attachment existingAttachment = entityManager.createQuery(
                            "SELECT a FROM Attachment a " +
                                    "JOIN FETCH a.task t " +
                                    "WHERE t.id = :taskId AND t.project.id = :projectId AND a.id = :id", Attachment.class)
                    .setParameter("projectId", projectId)
                    .setParameter("taskId", taskId)
                    .setParameter("id", id)
                    .getSingleResult();

            if (existingAttachment != null) {
                existingAttachment.setTitle(updatedEntity.getTitle());
                existingAttachment.setPath(updatedEntity.getPath());
                entityManager.merge(existingAttachment);
                return Optional.of(existingAttachment);
            } else {
                return Optional.empty();
            }
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    public List<Attachment> findByTitle(String title) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Attachment> query = cb.createQuery(Attachment.class);
        Root<Attachment> root = query.from(Attachment.class);
        query.select(root).where(cb.equal(root.get(Attachment_.TITLE), title));
        return entityManager.createQuery(query).getResultList();
    }
}