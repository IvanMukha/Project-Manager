package com.ivan.projectmanager.model;


import java.time.LocalDateTime;

public class Task {
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

    public Task setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Task setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public Task setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Task setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public Task setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public int getReporter() {
        return reporter;
    }

    public Task setReporter(int reporter) {
        this.reporter = reporter;
        return this;
    }

    public int getAssignee() {
        return assignee;
    }

    public Task setAssignee(int assignee) {
        this.assignee = assignee;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Task setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Task setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getProjectId() {
        return projectId;
    }

    public Task setProjectId(int projectId) {
        this.projectId = projectId;
        return this;
    }
}
