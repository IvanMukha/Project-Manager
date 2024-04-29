package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.exeptions.HandleCustomIllegalArgumentException;
import com.ivan.projectmanager.exeptions.HandleCustomNotFoundException;
import com.ivan.projectmanager.exeptions.HandleCustomNullPointerException;
import com.ivan.projectmanager.model.Task;
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

    @Autowired
    public TaskServiceImpl(ModelMapper modelMapper, TaskRepository taskRepository) {
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    public List<TaskDTO> getAll() {
        return taskRepository.getAll().stream().map(this::mapTaskToDTO).collect(Collectors.toList());
    }

    @Transactional
    public TaskDTO save(TaskDTO taskDTO) {
        checkTask(taskDTO);
        return mapTaskToDTO(taskRepository.save(mapDTOToTask(taskDTO)));
    }

    public Optional<TaskDTO> getById(Long id) {
        checkId(id);
        Optional<Task> taskOptional = taskRepository.getById(id);
        if (taskOptional.isEmpty()) {
            throw new HandleCustomNotFoundException("Task with id " + id + " not found");
        }
        return taskOptional.map(this::mapTaskToDTO);
    }

    @Transactional
    public Optional<TaskDTO> update(Long id, TaskDTO updatedTaskDTO) {
        checkId(id);
        if (updatedTaskDTO.getTitle() == null) {
            throw new HandleCustomNullPointerException("Title cannot be null");
        }
        if (updatedTaskDTO.getTitle().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Title cannot be empty");
        }
        Optional<Task> taskOptional = taskRepository.update(id, mapDTOToTask(updatedTaskDTO));
        if (taskOptional.isEmpty()) {
            throw new HandleCustomNotFoundException("Task with id " + id + " not found");
        }
        return taskOptional.map(this::mapTaskToDTO);
    }

    @Transactional
    public void delete(Long id) {
        checkId(id);
        taskRepository.delete(id);
    }

    private void checkTask(TaskDTO taskDTO) {
        if (taskDTO == null) {
            throw new HandleCustomNullPointerException("TaskDTO cannot be null");
        }
        if (taskDTO.getTitle() == null) {
            throw new HandleCustomNullPointerException("Title cannot be null");
        }
        if (taskDTO.getTitle().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Title cannot be empty");
        }
        if (taskDTO.getReporterId() == null) {
            throw new HandleCustomNullPointerException("Reporter cannot be null");
        }
        if (taskDTO.getProjectId() == null) {
            throw new HandleCustomNullPointerException("ProjectId cannot be null");
        }
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new HandleCustomNullPointerException("Task id cannot be null");
        }
        if (id <= 0) {
            throw new HandleCustomIllegalArgumentException("Task id must be greater than 0");
        }
    }

    private Task mapDTOToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO mapTaskToDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
}
