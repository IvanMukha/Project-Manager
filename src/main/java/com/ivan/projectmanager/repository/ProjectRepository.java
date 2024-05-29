package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Page<Project> getAll(Pageable pageable);

    List<Project> findByStatus(String status);

    List<Project> findByTitle(String title);

}
