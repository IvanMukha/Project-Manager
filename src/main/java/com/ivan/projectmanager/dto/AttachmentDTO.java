package com.ivan.projectmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AttachmentDTO {
    private Long id;
    @NotBlank(message = "Attachment title cannot be empty")
    private String title;
    private String path;
    @NotNull(message = "Task id cannot be null")
    @Positive(message = "Task id must be greater than 0")
    private Long taskId;

    public Long getId() {
        return id;
    }

    public AttachmentDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AttachmentDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPath() {
        return path;
    }

    public AttachmentDTO setPath(String path) {
        this.path = path;
        return this;
    }

    public Long getTaskId() {
        return taskId;
    }

    public AttachmentDTO setTaskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }
}
