package com.ivan.projectManager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectManager.dto.ProjectDTO;
import com.ivan.projectManager.dto.TaskDTO;
import com.ivan.projectManager.dto.TeamDTO;
import com.ivan.projectManager.dto.UserDTO;
import com.ivan.projectManager.service.EntityCreationService;
import com.ivan.projectManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final ObjectMapper objectMapper;
    private final EntityCreationService entityCreationService;

    @Autowired
    public TaskController(TaskService taskService, ObjectMapper objectMapper, EntityCreationService entityCreationService) {
        this.taskService = taskService;
        this.objectMapper = objectMapper;
        this.entityCreationService = entityCreationService;
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

    public String createTaskWithRelatedEntities(TaskDTO taskDTO, UserDTO userDTO, TeamDTO teamDTO, ProjectDTO projectDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(entityCreationService.createTaskWithRelatedEntities(taskDTO, userDTO, teamDTO, projectDTO));
    }
}
