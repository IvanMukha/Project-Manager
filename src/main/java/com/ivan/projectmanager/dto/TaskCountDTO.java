package com.ivan.projectmanager.dto;

import java.time.LocalDate;

public class TaskCountDTO {
    private LocalDate date;
    private Long count;

    public TaskCountDTO(LocalDate date, Long count) {
        this.date = date;
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}