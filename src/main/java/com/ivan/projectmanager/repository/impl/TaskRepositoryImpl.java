package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.TaskRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl extends AbstractRepository<Task,Integer> implements TaskRepository {
    public TaskRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Task.class);
    }
    @Override
    public List<Task> getAll() {
        return super.getAll();
    }
    @Override
    public Optional<Task> getById(Integer id) {
        return super.getById(id);
    }
    @Override
    public Optional<Task> update(Integer id, Task updatedEntity) {
        Task task = entityManager.find(Task.class, id);
        if (task != null) {
            task.setTitle(updatedEntity.getTitle());
            task.setStatus(updatedEntity.getStatus());
            task.setPriority(updatedEntity.getPriority());
            task.setDueDate(updatedEntity.getDueDate());
            task.setCategory(updatedEntity.getCategory());
            task.setLabel(updatedEntity.getLabel());
            task.setDescription(updatedEntity.getDescription());
            entityManager.merge(task);
            return Optional.of(task);
        } else {
            return Optional.empty();
        }
    }
    @Override
    public void delete(Integer id) {
        Task task = entityManager.find(Task.class, id);
        if (task != null) {
            entityManager.remove(task);
        }
    }
    public List<Task> getByStatusCriteria(String status) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> query = builder.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        Predicate predicate = builder.equal(root.get("status"), status);
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
        root.fetch("reporter", JoinType.LEFT);
        root.fetch("assignee", JoinType.LEFT);
        root.fetch("project", JoinType.LEFT);
        query.select(root).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Task> getAllEntityGraph() {
        EntityGraph<Task> entityGraph = entityManager.createEntityGraph(Task.class);
        entityGraph.addAttributeNodes("reporter", "assignee", "project");

        return entityManager.createQuery(
                        "SELECT t FROM Task t WHERE t.id IS NOT NULL", Task.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }
}
