package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Project_;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.ProjectRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class ProjectRepositoryImpl extends AbstractRepository<Project, Long> implements ProjectRepository {

    public ProjectRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Project.class);
    }

    public List<Project> getAll() {
        return super.getAll();
    }

    @Override
    public Optional<Project> getById(Long id) {
        return super.getById(id);
    }

    @Override
    public Optional<Project> update(Long id, Project updatedEntity) {
        Project existingProject = entityManager.find(Project.class, id);
        if (existingProject != null) {
            existingProject.setTitle(updatedEntity.getTitle());
            existingProject.setDescription(updatedEntity.getDescription());
            existingProject.setStatus(updatedEntity.getStatus());
            existingProject.setTeam(updatedEntity.getTeam());
            existingProject.setManager(updatedEntity.getManager());
            entityManager.merge(existingProject);
            return Optional.of(existingProject);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    public List<Project> findByStatusCriteria(String status) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> query = cb.createQuery(Project.class);
        Root<Project> root = query.from(Project.class);
        Predicate predicate = cb.equal(root.get(Project_.STATUS), status);
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Project> findByTitleJpql(String title) {
        return entityManager.createQuery("SELECT p FROM Project p WHERE p.title = :title", Project.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Project> findAllCriteriaFetch() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> query = cb.createQuery(Project.class);
        Root<Project> root = query.from(Project.class);
        root.fetch(Project_.TEAM, JoinType.LEFT);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Project> findAllJpqlFetch() {
        return entityManager.createQuery("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.team", Project.class)
                .getResultList();
    }

    public List<Project> findAllEntityGraphFetch() {
        EntityGraph<Project> graph = entityManager.createEntityGraph(Project.class);
        graph.addAttributeNodes("team");

        return entityManager.createQuery("SELECT p FROM Project p", Project.class)
                .setHint("javax.persistence.fetchgraph", graph)
                .getResultList();
    }
}
