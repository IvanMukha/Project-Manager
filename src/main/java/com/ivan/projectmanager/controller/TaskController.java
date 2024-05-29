package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
@Validated
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<TaskDTO>> getAll(
            @PathVariable("projectId") Long projectId,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "reporterId", required = false) Long reporterId,
            @RequestParam(name = "assigneeId", required = false) Long assigneeId,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "label", required = false) String label,
            @RequestParam(name = "startDateFrom", required = false) String startDateFrom,
            @RequestParam(name = "startDateTo", required = false) String startDateTo,
            @RequestParam(name = "dueDateFrom", required = false) String dueDateFrom,
            @RequestParam(name = "dueDateTo", required = false) String dueDateTo,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {

        Page<TaskDTO> tasks = taskService.getAll(
                status, priority, reporterId, assigneeId, category, label,
                startDateFrom, startDateTo, dueDateFrom, dueDateTo, projectId, page, size);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskDTO> save(@PathVariable("projectId") Long projectId,
                                        @RequestBody @Valid TaskDTO taskDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
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
                                          @PathVariable("id") Long id, @RequestBody @Valid TaskDTO taskDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
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
}