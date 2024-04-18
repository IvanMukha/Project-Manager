package com.ivan.projectmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "path")
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;


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

    public Task getTask() {
        return task;
    }

    public Attachment setTask(Task task) {
        this.task = task;
        return this;
    }
}
