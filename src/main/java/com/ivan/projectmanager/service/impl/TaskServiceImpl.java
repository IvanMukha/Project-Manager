package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.exeptions.CustomNotFoundException;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.repository.ProjectRepository;
import com.ivan.projectmanager.repository.TaskRepository;
import com.ivan.projectmanager.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskServiceImpl(ModelMapper modelMapper, TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public TaskDTO save(Long projectId, TaskDTO taskDTO) {
        Project project = projectRepository.getById(projectId)
                .orElseThrow(() -> new CustomNotFoundException(projectId, Project.class));

        taskDTO.setProjectId(projectId);
        return mapTaskToDTO(taskRepository.save(mapDTOToTask(taskDTO)));
    }

    public Optional<TaskDTO> getById(Long projectId, Long id) {
        Optional<Task> taskOptional = taskRepository.getById(projectId, id);
        if (taskOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Task.class);
        }
        return taskOptional.map(this::mapTaskToDTO);
    }

    @Transactional
    public Optional<TaskDTO> update(Long projectId, Long id, TaskDTO updatedTaskDTO) {
        Optional<Task> taskOptional = taskRepository.update(projectId, id, mapDTOToTask(updatedTaskDTO));
        if (taskOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Task.class);
        }
        return taskOptional.map(this::mapTaskToDTO);
    }

    public Page<TaskDTO> getAll(
            String status, String priority, Long reporterId, Long assigneeId,
            String category, String label, String startDateFromStr,
            String startDateToStr, String dueDateFromStr,
            String dueDateToStr, Long projectId, Integer page, Integer size) {

        LocalDateTime startDateFrom = parseLocalDateTime(startDateFromStr);
        LocalDateTime startDateTo = parseLocalDateTime(startDateToStr);
        LocalDateTime dueDateFrom = parseLocalDateTime(dueDateFromStr);
        LocalDateTime dueDateTo = parseLocalDateTime(dueDateToStr);
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 10;
        }
        return taskRepository.getAll(
                        status, priority, reporterId, assigneeId, category, label,
                        startDateFrom, startDateTo, dueDateFrom, dueDateTo, projectId, PageRequest.of(page, size))
                .map(this::mapTaskToDTO);
    }

    @Transactional
    public void delete(Long projectId, Long id) {
        taskRepository.delete(projectId, id);
    }

    private LocalDateTime parseLocalDateTime(String dateTimeStr) {
        if (dateTimeStr != null && !dateTimeStr.isEmpty()) {
            dateTimeStr = dateTimeStr.trim();
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;
    }

    private Task mapDTOToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO mapTaskToDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
}
