package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.dto.TaskCountDTO;
import com.ivan.projectmanager.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> getById(Long projectId, Long id);

    Task save(Task task);

    Optional<Task> update(Long projectId, Long id, Task updatedEntity);

    void delete(Long projectId, Long id);

    List<Task> getByStatus(String status);

    List<Task> getByCategory(String category);

    Page<Task> getAll(
            String status, String priority, Long reporterId, Long assigneeId,
            String category, String label, LocalDateTime startDateFrom,
            LocalDateTime startDateTo, LocalDateTime dueDateFrom,
            LocalDateTime dueDateTo, Long projectId, Pageable pageable);

    List<TaskCountDTO> countTasksByStatusAndDateRange(String status, LocalDateTime dateFrom,
                                                      LocalDateTime dateTo, Long projectId);

    List<TaskCountDTO> countTasksByStatusAndDateRangeForUser(String status, LocalDateTime dateFrom,
                                                             LocalDateTime dateTo, Long userId, Long projectId);
}
