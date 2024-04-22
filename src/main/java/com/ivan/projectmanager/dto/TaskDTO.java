package com.ivan.projectmanager.dto;

import java.time.LocalDateTime;

public class TaskDTO {
    private Long id;
    private String title;
    private String status;
    private String priority;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private Long reporter;
    private Long assignee;
    private String category;
    private String label;
    private String description;
    private Long projectId;

    public Long getId() {
        return id;
    }

    public TaskDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TaskDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TaskDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public TaskDTO setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public TaskDTO setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public TaskDTO setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public Long getReporter() {
        return reporter;
    }

    public TaskDTO setReporter(Long reporter) {
        this.reporter = reporter;
        return this;
    }

    public Long getAssignee() {
        return assignee;
    }

    public TaskDTO setAssignee(Long assignee) {
        this.assignee = assignee;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public TaskDTO setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public TaskDTO setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getProjectId() {
        return projectId;
    }

    public TaskDTO setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }
}
