package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.Task_;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Transactional
public class TaskRepositoryImpl extends AbstractRepository<Task, Long> implements TaskRepository {
    public TaskRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Task.class);
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

    public List<Task> getByStatus(String status) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> query = builder.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        Predicate predicate = builder.equal(root.get(Task_.STATUS), status);
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Task> getByCategory(String category) {
        return entityManager.createQuery("SELECT t FROM Task t WHERE t.category = :category", Task.class)
                .setParameter("category", category)
                .getResultList();
    }

    public Page<Task> getAll(
            String status, String priority, Long reporterId, Long assigneeId,
            String category, String label, LocalDateTime startDateFrom,
            LocalDateTime startDateTo, LocalDateTime dueDateFrom,
            LocalDateTime dueDateTo, Long projectId, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        Root<Task> root = criteriaQuery.from(Task.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(buildEqualPredicate(criteriaBuilder, root.get("project").get("id"), projectId));
        predicates.add(buildEqualPredicate(criteriaBuilder, root.get("status"), status));
        predicates.add(buildEqualPredicate(criteriaBuilder, root.get("priority"), priority));
        predicates.add(buildEqualPredicate(criteriaBuilder, root.get("reporter").get("id"), reporterId));
        predicates.add(buildEqualPredicate(criteriaBuilder, root.get("assignee").get("id"), assigneeId));
        predicates.add(buildEqualPredicate(criteriaBuilder, root.get("category"), category));
        predicates.add(buildEqualPredicate(criteriaBuilder, root.get("label"), label));
        predicates.add(buildDateRangePredicate(criteriaBuilder, root.get("startDate"), startDateFrom, startDateTo));
        predicates.add(buildDateRangePredicate(criteriaBuilder, root.get("dueDate"), dueDateFrom, dueDateTo));

        Predicate[] nonNullPredicates = predicates.stream()
                .filter(Objects::nonNull)
                .toArray(Predicate[]::new);

        Predicate finalPredicate = criteriaBuilder.and(nonNullPredicates);

        criteriaQuery.where(finalPredicate);

        TypedQuery<Task> query = entityManager.createQuery(criteriaQuery);

        int totalRows = query.getResultList().size();
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Task> resultList = query.getResultList();

        return new PageImpl<>(resultList, pageable, totalRows);
    }


    private Predicate buildEqualPredicate(CriteriaBuilder criteriaBuilder, Path<String> path, String value) {
        return (value != null) ? criteriaBuilder.equal(path, value) : null;
    }

    private Predicate buildEqualPredicate(CriteriaBuilder criteriaBuilder, Path<Long> path, Long value) {
        return (value != null) ? criteriaBuilder.equal(path, value) : null;
    }

    private Predicate buildDateRangePredicate(CriteriaBuilder criteriaBuilder, Path<LocalDateTime> path, LocalDateTime from, LocalDateTime to) {
        if (from != null && to != null) {
            return criteriaBuilder.between(path, from, to);
        } else if (from != null) {
            return criteriaBuilder.greaterThanOrEqualTo(path, from);
        } else if (to != null) {
            return criteriaBuilder.lessThanOrEqualTo(path, to);
        }
        return null;
    }
}
