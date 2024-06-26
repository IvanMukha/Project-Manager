package com.ivan.projectmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_seq_generator")
    @SequenceGenerator(name = "projects_seq_generator", sequenceName = "projects_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    public Long getId() {
        return id;
    }

    public Project setId(Long id) {
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

    public Team getTeam() {
        return team;
    }

    public Project setTeam(Team team) {
        this.team = team;
        return this;
    }

    public User getManager() {
        return manager;
    }

    public Project setManager(User manager) {
        this.manager = manager;
        return this;
    }


}
