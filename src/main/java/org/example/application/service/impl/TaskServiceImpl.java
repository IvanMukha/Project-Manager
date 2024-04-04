package org.example.application.service.impl;

import org.example.application.repository.TaskRepository;
import org.example.application.service.TaskService;
import org.example.application.dto.TaskDTO;
import org.example.application.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepositoryImpl;

    @Autowired
    public TaskServiceImpl(ModelMapper modelMapper, TaskRepository taskRepositoryImpl) {
        this.modelMapper = modelMapper;
        this.taskRepositoryImpl = taskRepositoryImpl;
    }

    public List<TaskDTO> getAll() {
        return taskRepositoryImpl.getAll().stream().map(this::mapTaskToDTO).collect(Collectors.toList());
    }

    public TaskDTO save(TaskDTO taskDTO) {
        return mapTaskToDTO(taskRepositoryImpl.save(mapDTOToTask(taskDTO)));
    }

    public Optional<TaskDTO> getById(int id) {
        Optional<Task> taskOptional = taskRepositoryImpl.getById(id);
        return taskOptional.map(this::mapTaskToDTO);
    }


    public Optional<TaskDTO> update(int id, TaskDTO updatedTaskDTO) {
        Optional<Task> taskOptional = taskRepositoryImpl.update(id, mapDTOToTask(updatedTaskDTO));
        return taskOptional.map(this::mapTaskToDTO);
    }

    public void delete(int id) {
        taskRepositoryImpl.delete(id);
    }

    private Task mapDTOToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO mapTaskToDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
}
