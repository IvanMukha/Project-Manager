package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Long> {
    Page<Team> getAll(Pageable pageable);

    Optional<Team> update(Long id, Team updatedEntity);
}
