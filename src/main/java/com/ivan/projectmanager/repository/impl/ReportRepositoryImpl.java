package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Report;
import com.ivan.projectmanager.model.Report_;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.ReportRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReportRepositoryImpl extends AbstractRepository<Report, Long> implements ReportRepository {


    public ReportRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Report.class);
    }

    public List<Report> getAll(Long projectId, Long taskId) {
        return entityManager.createQuery("SELECT r FROM Report r JOIN FETCH r.task t WHERE t.id = :taskId AND t.project.id = :projectId", Report.class)
                .setParameter("taskId", taskId)
                .setParameter("projectId", projectId)
                .getResultList();
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

    public List<Report> getReportsByUserJpql(User user) {
        TypedQuery<Report> query = entityManager.createQuery(
                "SELECT r FROM Report r WHERE r.user = :user", Report.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<Report> getReportsByUserCriteria(User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Report> cq = cb.createQuery(Report.class);
        Root<Report> root = cq.from(Report.class);
        cq.select(root).where(cb.equal(root.get(Report_.USER), user));
        TypedQuery<Report> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    public List<Report> getReportsByUserJpqlFetch(User user) {
        return entityManager.createQuery(
                        "SELECT r FROM Report r JOIN FETCH r.user WHERE r.user = :user", Report.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Report> getReportsByUserCriteriaFetch(User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Report> cq = cb.createQuery(Report.class);
        Root<Report> root = cq.from(Report.class);
        root.fetch(Report_.USER, JoinType.LEFT);
        cq.select(root).where(cb.equal(root.get(Report_.USER), user));
        return entityManager.createQuery(cq).getResultList();
    }

    public List<Report> getReportsByUserEntityGraph(User user) {
        EntityGraph<Report> entityGraph = entityManager.createEntityGraph(Report.class);
        entityGraph.addAttributeNodes(Report_.USER);
        return entityManager.createQuery(
                        "SELECT r FROM Report r WHERE r.user = :user", Report.class)
                .setParameter("user", user)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }
}
