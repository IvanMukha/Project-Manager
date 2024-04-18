package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Team;
import com.ivan.projectmanager.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

    @Override
    public List<Team> getAll() {
        return List.of();
    }

    @Override
    public Team save(Team entity) {
        return null;
    }

    @Override
    public Optional<Team> getById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<Team> update(Integer integer, Team updatedEntity) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer integer) {

    }
}
