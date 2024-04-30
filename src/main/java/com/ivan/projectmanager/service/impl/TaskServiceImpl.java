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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<TaskDTO> getAll(Long projectId) {
        return taskRepository.getAll(projectId).stream().map(this::mapTaskToDTO).collect(Collectors.toList());
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

    @Transactional
    public void delete(Long projectId, Long id) {
        taskRepository.delete(projectId, id);
    }

    private Task mapDTOToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO mapTaskToDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
}
