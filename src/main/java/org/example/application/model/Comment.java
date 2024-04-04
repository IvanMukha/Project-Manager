package org.example.application.model;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private String text;
    private LocalDateTime addTime;
    private int userId;
    private int taskId;

    public int getId() {
        return id;
    }

    public Comment setId(int id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public Comment setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public Comment setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Comment setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getTaskId() {
        return taskId;
    }

    public Comment setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }
}
