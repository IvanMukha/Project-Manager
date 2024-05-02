package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.Task_;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.TaskRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl extends AbstractRepository<Task, Long> implements TaskRepository {
    public TaskRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Task.class);
    }

    public List<Task> getAll(Long projectId) {
        return entityManager.createQuery("SELECT t FROM Task t JOIN FETCH t.project p WHERE  t.project.id = :projectId", Task.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public Optional<Task> getById(Long projectId, Long id) {
        try {
            return Optional.ofNullable(entityManager.createQuery(
                            "SELECT t FROM Task t " +
                                    "JOIN FETCH t.project p " +
                                    "WHERE p.id = :projectId AND t.id = :id", Task.class)
                    .setParameter("projectId", projectId)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Task> update(Long projectId, Long id, Task updatedEntity) {
        try {
            Task existingTask = entityManager.createQuery(
                            "SELECT t FROM Task t " +
                                    "JOIN FETCH t.project p " +
                                    "WHERE p.id = :projectId AND t.id = :id", Task.class)
                    .setParameter("projectId", projectId)
                    .setParameter("id", id)
                    .getSingleResult();

            if (existingTask != null) {
                existingTask.setTitle(updatedEntity.getTitle());
                existingTask.setStatus(updatedEntity.getStatus());
                existingTask.setPriority(updatedEntity.getPriority());
                existingTask.setDueDate(updatedEntity.getDueDate());
                existingTask.setCategory(updatedEntity.getCategory());
                existingTask.setLabel(updatedEntity.getLabel());
                existingTask.setDescription(updatedEntity.getDescription());
                entityManager.merge(existingTask);
                return Optional.of(existingTask);
            } else {
                return Optional.empty();
            }
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void delete(Long projectId, Long id) {
        getById(projectId, id).ifPresent(entityManager::remove);
    }

    public List<Task> getByStatusCriteria(String status) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> query = builder.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        Predicate predicate = builder.equal(root.get(Task_.STATUS), status);
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Task> getByCategoryJpql(String category) {
        return entityManager.createQuery("SELECT t FROM Task t WHERE t.category = :category", Task.class)
                .setParameter("category", category)
                .getResultList();
    }

    public List<Task> getAllJpqlFetch() {
        return entityManager.createQuery(
                        "SELECT DISTINCT t FROM Task t " +
                                "LEFT JOIN FETCH t.reporter " +
                                "LEFT JOIN FETCH t.assignee " +
                                "LEFT JOIN FETCH t.project " +
                                "WHERE t.id IS NOT NULL", Task.class)
                .getResultList();
    }

    public List<Task> getAllCriteriaFetch() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> query = cb.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        root.fetch(Task_.REPORTER, JoinType.LEFT);
        root.fetch(Task_.ASSIGNEE, JoinType.LEFT);
        root.fetch(Task_.PROJECT, JoinType.LEFT);
        query.select(root).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Task> getAllEntityGraph() {
        EntityGraph<Task> entityGraph = entityManager.createEntityGraph(Task.class);
        entityGraph.addAttributeNodes(Task_.REPORTER, Task_.ASSIGNEE, Task_.PROJECT);

        return entityManager.createQuery(
                        "SELECT t FROM Task t WHERE t.id IS NOT NULL", Task.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }
}
