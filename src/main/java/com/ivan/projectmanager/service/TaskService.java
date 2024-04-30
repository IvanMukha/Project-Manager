package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.TaskDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskDTO> getAll(Long projectId);

    TaskDTO save(Long projectId, TaskDTO taskDTO);

    Optional<TaskDTO> getById(Long projectId, Long id);

    Optional<TaskDTO> update(Long projectId, Long id, TaskDTO updatedTaskDTO);

    void delete(Long projectId, Long id);

}
