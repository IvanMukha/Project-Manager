package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Report;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.ReportRepository;
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
public class ReportRepositoryImpl extends AbstractRepository<Report, Long> implements ReportRepository {


    public ReportRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Report.class);
    }

    public Page<Report> getAll(Long projectId, Long taskId, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Report> criteriaQuery = criteriaBuilder.createQuery(Report.class);
        Root<Report> root = criteriaQuery.from(Report.class);

        Predicate taskIdPredicate = criteriaBuilder.equal(root.get("task").get("id"), taskId);
        Predicate projectIdPredicate = criteriaBuilder.equal(root.get("task").get("project").get("id"), projectId);
        Predicate finalPredicate = criteriaBuilder.and(taskIdPredicate, projectIdPredicate);

        criteriaQuery.where(finalPredicate);

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Report.class)));
        Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

        TypedQuery<Report> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<Report> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, totalRows);
    }


    public Optional<Report> getById(Long projectId, Long taskId, Long id) {
        try {
            return Optional.ofNullable(entityManager.createQuery(
                            "SELECT r FROM Report r " +
                                    "JOIN FETCH r.task t " +
                                    "WHERE t.id = :taskId AND t.project.id = :projectId AND r.id = :id", Report.class)
                    .setParameter("projectId", projectId)
                    .setParameter("taskId", taskId)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Report> update(Long projectId, Long taskId, Long id, Report updatedEntity) {
        try {
            Report existingReport = entityManager.createQuery(
                            "SELECT r FROM Report r " +
                                    "JOIN FETCH r.task t " +
                                    "WHERE t.id = :taskId AND t.project.id = :projectId AND r.id = :id", Report.class)
                    .setParameter("projectId", projectId)
                    .setParameter("taskId", taskId)
                    .setParameter("id", id)
                    .getSingleResult();

            if (existingReport != null) {
                existingReport.setText(updatedEntity.getText());
                existingReport.setTitle(updatedEntity.getTitle());
                entityManager.merge(existingReport);
                return Optional.of(existingReport);
            } else {
                return Optional.empty();
            }
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void delete(Long projectId, Long taskId, Long id) {
        getById(projectId, taskId, id).ifPresent(entityManager::remove);
    }

    public List<Report> getReportsByUser(User user) {
        TypedQuery<Report> query = entityManager.createQuery(
                "SELECT r FROM Report r WHERE r.user = :user", Report.class);
        query.setParameter("user", user);
        return query.getResultList();
    }
}
