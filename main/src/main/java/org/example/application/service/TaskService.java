package org.example.application.service;

import org.example.application.dto.TaskDTO;
import org.example.application.model.Task;
import org.example.application.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(ModelMapper modelMapper, TaskRepository taskRepository) {
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    public List<TaskDTO> getAll() {
        return taskRepository.getAll().stream().map(this::mapTaskToDTO).collect(Collectors.toList());
    }

    public void save(TaskDTO taskDTO) {
        taskRepository.save(mapDTOToTask(taskDTO));
    }

    public Optional<TaskDTO> getById(int id) {
        Optional<Task> taskOptional = taskRepository.getById(id);
        return taskOptional.map(this::mapTaskToDTO);
    }

    public void update(int id, TaskDTO updatedTaskDTO) {
        taskRepository.update(id, mapDTOToTask(updatedTaskDTO));
    }

    public void delete(int id) {
        taskRepository.delete(id);
    }

    private Task mapDTOToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO mapTaskToDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
}
