package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Project_;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.ProjectRepository;
import jakarta.persistence.EntityManager;
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
public class ProjectRepositoryImpl extends AbstractRepository<Project, Long> implements ProjectRepository {

    public ProjectRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Project.class);
    }

    public Page<Project> getAll(Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        Root<Project> root = criteriaQuery.from(Project.class);

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Project.class)));
        Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

        criteriaQuery.select(root);
        TypedQuery<Project> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Project> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, totalRows);
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

    public List<Project> findByStatus(String status) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> query = cb.createQuery(Project.class);
        Root<Project> root = query.from(Project.class);
        Predicate predicate = cb.equal(root.get(Project_.STATUS), status);
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

    public List<Project> findByTitle(String title) {
        return entityManager.createQuery("SELECT p FROM Project p WHERE p.title = :title", Project.class)
                .setParameter("title", title)
                .getResultList();
    }
}
