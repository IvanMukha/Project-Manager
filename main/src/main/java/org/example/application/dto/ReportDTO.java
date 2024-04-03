package org.example.application.dto;

import java.time.LocalDateTime;

public class ReportDTO {
    private int id;
    private String title;
    private String text;
    private LocalDateTime createdAt;
    private int userId;
    private int taskId;

    public int getId() {
        return id;
    }

    public ReportDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ReportDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public ReportDTO setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ReportDTO setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public ReportDTO setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getTaskId() {
        return taskId;
    }

    public ReportDTO setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }
}
