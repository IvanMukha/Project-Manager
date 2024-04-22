package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Team;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.TeamRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class TeamRepositoryImpl extends AbstractRepository<Team, Long> implements TeamRepository {

    public TeamRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Team.class);
    }
}

