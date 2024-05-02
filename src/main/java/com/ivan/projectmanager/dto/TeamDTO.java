package com.ivan.projectmanager.dto;

import jakarta.validation.constraints.NotBlank;

public class TeamDTO {
    private Long id;
    @NotBlank(message = "Team name cannot be empty")
    private String name;

    public Long getId() {
        return id;
    }

    public TeamDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TeamDTO setName(String name) {
        this.name = name;
        return this;
    }
}
