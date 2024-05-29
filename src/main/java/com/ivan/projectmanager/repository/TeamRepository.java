package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamRepository extends CrudRepository<Team, Long> {
    Page<Team> getAll(Pageable pageable);
}
