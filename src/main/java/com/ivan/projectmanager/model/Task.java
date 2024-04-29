package com.ivan.projectmanager.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_seq_generator")
    @SequenceGenerator(name = "tasks_seq_generator", sequenceName = "tasks_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String status;
    private String priority;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User reporter;
    @ManyToOne(fetch = FetchType.LAZY)
    private User assignee;
    private String category;
    private String label;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    @OneToMany(mappedBy = "task")
    private List<Attachment> attachments;
    @OneToMany(mappedBy = "task")
    private List<Report> reports;

    public Long getId() {
        return id;
    }

    public Task setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Task setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public Task setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Task setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public Task setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public User getReporter() {
        return reporter;
    }

    public Task setReporter(User reporter) {
        this.reporter = reporter;
        return this;
    }

    public User getAssignee() {
        return assignee;
    }

    public Task setAssignee(User assignee) {
        this.assignee = assignee;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Task setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Task setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public Project getProject() {
        return project;
    }

    public Task setProject(Project project) {
        this.project = project;
        return this;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
