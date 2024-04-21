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
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;
    @Column()
    private String title;
    @Column()
    private String description;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column()
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