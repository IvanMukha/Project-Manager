package com.ivan.projectManager.dto;

import java.time.LocalDateTime;

public class TaskDTO {
    private int id;
    private String title;
    private String status;
    private String priority;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private int reporter;
    private int assignee;
    private String category;
    private String label;
    private String description;
    private int projectId;

    public int getId() {
        return id;
    }

    public TaskDTO setId(int id) {
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

    public int getReporter() {
        return reporter;
    }

    public TaskDTO setReporter(int reporter) {
        this.reporter = reporter;
        return this;
    }

    public int getAssignee() {
        return assignee;
    }

    public TaskDTO setAssignee(int assignee) {
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

    public int getProjectId() {
        return projectId;
    }

    public TaskDTO setProjectId(int projectId) {
        this.projectId = projectId;
        return this;
    }
}
