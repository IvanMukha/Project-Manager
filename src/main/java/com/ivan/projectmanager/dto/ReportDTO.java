package com.ivan.projectmanager.dto;

import java.time.LocalDateTime;

public class ReportDTO {
    private Long id;
    private String title;
    private String text;
    private LocalDateTime createdAt;
    private Long userId;
    private Long taskId;

    public Long getId() {
        return id;
    }

    public ReportDTO setId(Long id) {
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

    public Long getUserId() {
        return userId;
    }

    public ReportDTO setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getTaskId() {
        return taskId;
    }

    public ReportDTO setTaskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }
}
