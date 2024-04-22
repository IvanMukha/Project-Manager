package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.TaskDTO;
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
        return mapTaskToDTO(taskRepository.save(mapDTOToTask(taskDTO)));
    }

    public Optional<TaskDTO> getById(Long id) {
        Optional<Task> taskOptional = taskRepository.getById(id);
        return taskOptional.map(this::mapTaskToDTO);
    }

    @Transactional
    public Optional<TaskDTO> update(Long id, TaskDTO updatedTaskDTO) {
        Optional<Task> taskOptional = taskRepository.update(id, mapDTOToTask(updatedTaskDTO));
        return taskOptional.map(this::mapTaskToDTO);
    }

    @Transactional
    public void delete(Long id) {
        taskRepository.delete(id);
    }

    private Task mapDTOToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO mapTaskToDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
}
