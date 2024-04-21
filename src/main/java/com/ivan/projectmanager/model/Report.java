package com.ivan.projectmanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String title;
    @Column
    private String text;
    @Column
    private LocalDateTime createAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;


    public Long getId() {
        return id;
    }

    public Report setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Report setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public Report setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Report setCreateAt(LocalDateTime createdAt) {
        this.createAt = createdAt;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Report setUser(User user) {
        this.user = user;
        return this;
    }

    public Task getTask() {
        return task;
    }

    public Report setTask(Task task) {
        this.task = task;
        return this;
    }
}
