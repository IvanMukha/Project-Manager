package com.ivan.projectmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UserDetailsDTO {
    @NotNull(message = "Task id cannot be null")
    @Positive(message = "Task id must be greater than 0")
    private Long userId;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    private String surname;
    private String phone;
    private String workPhone;
    private String workAdress;
    private String department;

    public Long getUserId() {
        return userId;
    }

    public UserDetailsDTO setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDetailsDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserDetailsDTO setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserDetailsDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public UserDetailsDTO setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public String getWorkAdress() {
        return workAdress;
    }

    public UserDetailsDTO setWorkAdress(String workAdress) {
        this.workAdress = workAdress;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public UserDetailsDTO setDepartment(String department) {
        this.department = department;
        return this;
    }
}
