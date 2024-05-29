package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.TaskDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TaskService {
    Page<TaskDTO> getAll(
            String status, String priority, Long reporterId, Long assigneeId,
            String category, String label, String startDateFromStr,
            String startDateToStr, String dueDateFromStr,
            String dueDateToStr, Long projectId, Integer page, Integer size);

    TaskDTO save(Long projectId, TaskDTO taskDTO);

    Optional<TaskDTO> getById(Long projectId, Long id);

    Optional<TaskDTO> update(Long projectId, Long id, TaskDTO updatedTaskDTO);

    void delete(Long projectId, Long id);

}
