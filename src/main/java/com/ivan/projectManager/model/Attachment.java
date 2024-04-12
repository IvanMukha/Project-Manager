package com.ivan.projectManager.model;

public class Attachment {
    private int id;
    private String title;
    private String path;
    private int taskId;

    public int getId() {
        return id;
    }

    public Attachment setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Attachment setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Attachment setPath(String path) {
        this.path = path;
        return this;
    }

    public int getTaskId() {
        return taskId;
    }

    public Attachment setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }
}
