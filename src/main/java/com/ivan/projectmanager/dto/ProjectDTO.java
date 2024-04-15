package com.ivan.projectmanager.dto;

import java.time.LocalDateTime;

public class ProjectDTO {
    private int id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private String status;
    private int teamId;
    private int managerId;

    public int getId() {
        return id;
    }

    public ProjectDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProjectDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProjectDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public ProjectDTO setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ProjectDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public int getTeamId() {
        return teamId;
    }

    public ProjectDTO setTeamId(int teamId) {
        this.teamId = teamId;
        return this;
    }

    public int getManagerId() {
        return managerId;
    }

    public ProjectDTO setManagerId(int managerId) {
        this.managerId = managerId;
        return this;
    }
}
