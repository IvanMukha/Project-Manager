package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Attachment;
import com.ivan.projectmanager.model.Attachment_;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.AttachmentRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AttachmentRepositoryImpl extends AbstractRepository<Attachment, Long> implements AttachmentRepository {

    public AttachmentRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Attachment.class);
    }

    @Override
    public List<Attachment> getAll() {
        return super.getAll();
    }

    @Override
    public Optional<Attachment> getById(Long id) {
        return super.getById(id);
    }

    @Override
    public Optional<Attachment> update(Long id, Attachment updatedEntity) {
        Attachment existingAttachment = entityManager.find(Attachment.class, id);
        if (existingAttachment != null) {
            existingAttachment.setTitle(updatedEntity.getTitle());
            existingAttachment.setPath(updatedEntity.getPath());
            existingAttachment.setTask(updatedEntity.getTask());
            entityManager.merge(existingAttachment);
            return Optional.of(existingAttachment);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    public List<Attachment> findByTitleCriteria(String title) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Attachment> query = cb.createQuery(Attachment.class);
        Root<Attachment> root = query.from(Attachment.class);
        query.select(root).where(cb.equal(root.get(Attachment_.TITLE), title));
        return entityManager.createQuery(query).getResultList();
    }

    public List<Attachment> findByTitleJpql(String title) {
        return entityManager.createQuery("SELECT a FROM Attachment a WHERE a.title = :title", Attachment.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Attachment> findByTitleCriteriaFetch(String title) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Attachment> query = cb.createQuery(Attachment.class);
        Root<Attachment> root = query.from(Attachment.class);
        root.fetch(Attachment_.TASK);
        query.select(root).where(cb.equal(root.get(Attachment_.TITLE), title));
        return entityManager.createQuery(query).getResultList();
    }

    public List<Attachment> findByTitleJpqlFetch(String title) {
        return entityManager.createQuery("SELECT a FROM Attachment a JOIN FETCH a.task WHERE a.title = :title", Attachment.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Attachment> findByTitleWithEntityGraphFetch(String title) {
        EntityGraph<Attachment> graph = entityManager.createEntityGraph(Attachment.class);
        graph.addAttributeNodes(Attachment_.TASK);

        return entityManager.createQuery("SELECT a FROM Attachment a WHERE a.title = :title", Attachment.class)
                .setParameter("title", title)
                .setHint("javax.persistence.fetchgraph", graph)
                .getResultList();
    }
}