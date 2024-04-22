package com.ivan.projectmanager.dto;

public class RoleDTO {
    private Long id;
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
