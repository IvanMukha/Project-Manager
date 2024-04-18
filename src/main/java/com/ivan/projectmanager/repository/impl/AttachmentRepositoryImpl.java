package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Attachment;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.AttachmentRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AttachmentRepositoryImpl extends AbstractRepository<Attachment,Integer> implements AttachmentRepository {
    private static final Logger log = LoggerFactory.getLogger(AttachmentRepositoryImpl.class);

    public AttachmentRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Attachment.class);
    }

    @Override
    public List<Attachment> getAll() {
       return super.getAll();
    }

    @Override
    public Optional<Attachment> getById(Integer id) {
        return super.getById(id);
    }

    @Override
    public Optional<Attachment> update(Integer id, Attachment updatedEntity) {
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
    public void delete(Integer id) {
        super.delete(id);
    }

    public List<Attachment> findByTitleCriteria(String title) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Attachment> query = cb.createQuery(Attachment.class);
        Root<Attachment> root = query.from(Attachment.class);
        query.select(root).where(cb.equal(root.get("title"), title));
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
        root.fetch("task");
        query.select(root).where(cb.equal(root.get("title"), title));
        return entityManager.createQuery(query).getResultList();
    }

    public List<Attachment> findByTitleJpqlFetch(String title) {
        return entityManager.createQuery("SELECT a FROM Attachment a JOIN FETCH a.task WHERE a.title = :title", Attachment.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Attachment> findByTitleWithEntityGraphFetch(String title) {
        EntityGraph<Attachment> graph = entityManager.createEntityGraph(Attachment.class);
        graph.addAttributeNodes("task");

        return entityManager.createQuery("SELECT a FROM Attachment a WHERE a.title = :title", Attachment.class)
                .setParameter("title", title)
                .setHint("javax.persistence.fetchgraph", graph)
                .getResultList();
    }
}