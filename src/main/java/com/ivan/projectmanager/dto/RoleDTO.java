package com.ivan.projectmanager.dto;

import jakarta.validation.constraints.NotBlank;

public class RoleDTO {
    private Long id;
    @NotBlank(message = "Role name cannot be empty")
    private String name;

    public Long getId() {
        return id;
    }

    public RoleDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoleDTO setName(String name) {
        this.name = name;
        return this;
    }
}
