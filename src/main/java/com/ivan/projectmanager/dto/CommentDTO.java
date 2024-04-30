package com.ivan.projectmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CommentDTO {
    private Long id;
    @NotBlank(message = "Comment text cannot be empty")
    private String text;
    @NotNull(message = "Task id cannot be null")
    @Positive(message = "Task id must be greater than 0")
    private Long taskId;

    public Long getId() {
        return id;
    }

    public CommentDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public CommentDTO setText(String text) {
        this.text = text;
        return this;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
