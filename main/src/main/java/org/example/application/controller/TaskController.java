package org.example.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.dto.TaskDTO;
import org.example.application.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class TaskController implements BaseController<TaskDTO> {
    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TaskController(TaskService taskService, ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.objectMapper = objectMapper;
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(taskService.getAll());
    }

    public String save(TaskDTO taskDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(taskService.save(taskDTO));
    }

    public String getById(int id) throws JsonProcessingException {
        Optional<TaskDTO> taskDTOOptional = taskService.getById(id);
        return objectMapper.writeValueAsString(taskDTOOptional.orElse(null));
    }

    public String update(int id, TaskDTO taskDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(taskService.update(id, taskDTO));
    }

    public void delete(int id) {
        taskService.delete(id);
    }
}
