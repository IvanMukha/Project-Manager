package com.ivan.projectmanager.dto;

import java.time.LocalDateTime;

public class ProjectDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private String status;
    private Long teamId;
    private Long managerId;

    public Long getId() {
        return id;
    }

    public ProjectDTO setId(Long id) {
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

    public Long getTeamId() {
        return teamId;
    }

    public ProjectDTO setTeamId(Long teamId) {
        this.teamId = teamId;
        return this;
    }

    public Long getManagerId() {
        return managerId;
    }

    public ProjectDTO setManagerId(Long managerId) {
        this.managerId = managerId;
        return this;
    }
}
