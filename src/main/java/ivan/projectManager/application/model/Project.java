package ivan.projectManager.application.model;

import java.time.LocalDateTime;

public class Project {
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

    public Project setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Project setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Project setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Project setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Project setStatus(String status) {
        this.status = status;
        return this;
    }

    public int getTeamId() {
        return teamId;
    }

    public Project setTeamId(int teamId) {
        this.teamId = teamId;
        return this;
    }

    public int getManagerId() {
        return managerId;
    }

    public Project setManagerId(int managerId) {
        this.managerId = managerId;
        return this;
    }
}
