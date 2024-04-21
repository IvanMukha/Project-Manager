package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class TaskController {
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

    public String getById(Long id) throws JsonProcessingException {
        Optional<TaskDTO> taskDTOOptional = taskService.getById(id);
        return objectMapper.writeValueAsString(taskDTOOptional.orElse(null));
    }

    public String update(Long id, TaskDTO taskDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(taskService.update(id, taskDTO));
    }

    public void delete(Long id) {
        taskService.delete(id);
    }

}
