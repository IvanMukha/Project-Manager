package org.example.application.service;

import org.example.application.RepositoryInterfaces.TaskRepositoryInterface;
import org.example.application.ServiceInterfaces.TaskServiceInterface;
import org.example.application.dto.TaskDTO;
import org.example.application.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService implements TaskServiceInterface {
    private final ModelMapper modelMapper;
    private final TaskRepositoryInterface taskRepository;

    @Autowired
    public TaskService(ModelMapper modelMapper, TaskRepositoryInterface taskRepository) {
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    public List<TaskDTO> getAll() {
        return taskRepository.getAll().stream().map(this::mapTaskToDTO).collect(Collectors.toList());
    }

    public TaskDTO save(TaskDTO taskDTO) {
        return mapTaskToDTO(taskRepository.save(mapDTOToTask(taskDTO)));
    }

    public Optional<TaskDTO> getById(int id) {
        Optional<Task> taskOptional = taskRepository.getById(id);
        return taskOptional.map(this::mapTaskToDTO);
    }


    public Optional<TaskDTO> update(int id, TaskDTO updatedTaskDTO) {
        Optional<Task> taskOptional = taskRepository.update(id, mapDTOToTask(updatedTaskDTO));
        return taskOptional.map(this::mapTaskToDTO);
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
