package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.TaskCountDTO;
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

import java.util.List;
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

    @GetMapping("/count-by-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskCountDTO>> countTasksByStatusAndDateRange(
            @PathVariable("projectId") Long projectId,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "dateFrom", required = false) String dateFrom,
            @RequestParam(name = "dateTo", required = false) String dateTo) {

        List<TaskCountDTO> taskCounts = taskService.countTasksByStatusAndDateRange(status, dateFrom, dateTo, projectId);
        return ResponseEntity.ok(taskCounts);
    }

    @GetMapping("/count-by-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskCountDTO>> countTasksByStatusAndDateRangeForUser(
            @PathVariable("projectId") Long projectId,
            @RequestParam(name = "status") String status,
            @RequestParam(name = "dateFrom") String dateFromStr,
            @RequestParam(name = "dateTo") String dateToStr,
            @RequestParam(name = "userId") Long userId) {

        List<TaskCountDTO> taskCounts = taskService.countTasksByStatusAndDateRangeForUser(
                status, dateFromStr, dateToStr, userId, projectId);

        return ResponseEntity.ok(taskCounts);
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