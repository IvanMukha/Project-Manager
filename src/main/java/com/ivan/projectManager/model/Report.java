package com.ivan.projectManager.model;

import java.time.LocalDateTime;

public class Report {
    private int id;
    private String title;
    private String text;
    private LocalDateTime createdAt;
    private int userId;
    private int taskId;

    public int getId() {
        return id;
    }

    public Report setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Report setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public Report setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Report setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Report setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getTaskId() {
        return taskId;
    }

    public Report setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }
}
