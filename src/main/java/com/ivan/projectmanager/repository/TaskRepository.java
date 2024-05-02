package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> getAll(Long projectId);

    Optional<Task> getById(Long projectId, Long id);

    Task save(Task task);

    Optional<Task> update(Long projectId, Long id, Task updatedEntity);

    void delete(Long projectId, Long id);

    List<Task> getByStatusCriteria(String status);

    List<Task> getByCategoryJpql(String category);

    List<Task> getAllJpqlFetch();

    List<Task> getAllCriteriaFetch();

    List<Task> getAllEntityGraph();

}
