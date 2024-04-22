package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Project;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findByStatusCriteria(String status);

    List<Project> findByTitleJpql(String title);

    List<Project> findAllCriteriaFetch();

    List<Project> findAllJpqlFetch();

    List<Project> findAllEntityGraphFetch();
}
