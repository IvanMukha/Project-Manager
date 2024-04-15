package com.ivan.projectmanager.dto;

public class AttachmentDTO {
    private int id;
    private String title;
    private int taskId;

    public int getId() {
        return id;
    }

    public AttachmentDTO setId(int id) {
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

    public int getTaskId() {
        return taskId;
    }

    public AttachmentDTO setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }
}
