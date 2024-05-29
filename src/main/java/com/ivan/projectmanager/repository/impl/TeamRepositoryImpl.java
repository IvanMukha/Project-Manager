package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Team;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.TeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
public class TeamRepositoryImpl extends AbstractRepository<Team, Long> implements TeamRepository {

    public TeamRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Team.class);
    }

    public Page<Team> getAll(Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Team> criteriaQuery = criteriaBuilder.createQuery(Team.class);
        Root<Team> root = criteriaQuery.from(Team.class);
        criteriaQuery.select(root);

        TypedQuery<Team> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Team> resultList = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Team.class)));
        Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, totalRows);
    }

    public Optional<Team> update(Long id, Team updatedEntity) {
        return super.getById(id).map(team -> {
            team.setName(updatedEntity.getName());
            team.setUsers(updatedEntity.getUsers());
            entityManager.merge(team);
            return team;
        });
    }

}

