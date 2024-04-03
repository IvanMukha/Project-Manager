package org.example.application.dto;

public class AttachmentDTO {
    private int id;
    private String title;
    private int taskId;

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

    public int getId() {
        return id;
    }

    public AttachmentDTO setId(int id) {
        this.id = id;
        return this;
    }
}
