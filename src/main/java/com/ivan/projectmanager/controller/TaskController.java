package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.service.EntityCreationService;
import com.ivan.projectmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
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

    @GetMapping()
    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(taskService.getAll());
    }

    @PostMapping("/new")
    public String save(TaskDTO taskDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(taskService.save(taskDTO));
    }

    @GetMapping("/{id}")
    public String getById(Long id) throws JsonProcessingException {
        Optional<TaskDTO> taskDTOOptional = taskService.getById(id);
        return objectMapper.writeValueAsString(taskDTOOptional.orElse(null));
    }

    @PatchMapping("/{id}")
    public String update(Long id, TaskDTO taskDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(taskService.update(id, taskDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        taskService.delete(id);
    }

    public String createTaskWithRelatedEntities(TaskDTO taskDTO, UserDTO userDTO, TeamDTO teamDTO, ProjectDTO projectDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(entityCreationService.createTaskWithRelatedEntities(taskDTO, userDTO, teamDTO, projectDTO));
    }
}
