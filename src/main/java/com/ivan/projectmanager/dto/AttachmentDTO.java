package com.ivan.projectmanager.dto;

import com.ivan.projectmanager.model.Task;

public class AttachmentDTO {
    private Long id;
    private String title;
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

    public Long getTaskId() {
        return taskId;
    }

    public AttachmentDTO setTaskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }
}
