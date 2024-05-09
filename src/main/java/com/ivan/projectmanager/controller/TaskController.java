package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.service.EntityCreationService;
import com.ivan.projectmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
@Validated
public class TaskController {
    private final TaskService taskService;
    private final EntityCreationService entityCreationService;

    @Autowired
    public TaskController(TaskService taskService, EntityCreationService entityCreationService) {
        this.taskService = taskService;
        this.entityCreationService = entityCreationService;
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<TaskDTO>> getAll(@PathVariable("projectId") Long projectId) {
        List<TaskDTO> tasks = taskService.getAll(projectId);
        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskDTO> save(@PathVariable("projectId") Long projectId,
                                        @RequestBody TaskDTO taskDTO) {
        TaskDTO savedTask = taskService.save(projectId, taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<TaskDTO> getById(@PathVariable("projectId") Long projectId,
                                           @PathVariable("id") Long id) {
        Optional<TaskDTO> taskDTOOptional = taskService.getById(projectId, id);
        return taskDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskDTO> update(@PathVariable("projectId") Long projectId,
                                          @PathVariable("id") Long id, @RequestBody TaskDTO taskDTO) {
        Optional<TaskDTO> updatedTask = taskService.update(projectId, id, taskDTO);
        return updatedTask.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("projectId") Long projectId,
                                       @PathVariable("id") Long id) {
        taskService.delete(projectId, id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<String> createTaskWithRelatedEntities(@PathVariable("projectId") Long projectId, @RequestBody TaskDTO taskDTO, @RequestBody UserDTO userDTO, @RequestBody TeamDTO teamDTO, @RequestBody ProjectDTO projectDTO) {
        String result = String.valueOf(entityCreationService.createTaskWithRelatedEntities(taskDTO, userDTO, teamDTO, projectDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}