package com.ivan.projectmanager.dto;

public class AttachmentDTO {
    private Long id;
    private String title;
    private String path;
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
