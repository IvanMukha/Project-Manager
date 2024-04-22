package com.ivan.projectmanager.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;


    public Long getId() {
        return id;
    }

    public Attachment setId(Long id) {
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
