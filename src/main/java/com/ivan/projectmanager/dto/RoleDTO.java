package com.ivan.projectmanager.dto;

public class RoleDTO {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public RoleDTO setId(int id) {
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
