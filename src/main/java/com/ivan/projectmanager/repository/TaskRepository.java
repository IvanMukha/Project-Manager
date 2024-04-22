package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Task;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> getByStatusCriteria(String status);

    List<Task> getByCategoryJpql(String category);

    List<Task> getAllJpqlFetch();

    List<Task> getAllCriteriaFetch();

    List<Task> getAllEntityGraph();

}
