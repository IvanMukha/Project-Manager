package com.ivan.projectmanager.dto;

public class UserDetailsDTO {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String workPhone;
    private String workAddress;
    private String department;

    public Long getId() {
        return id;
    }

    public UserDetailsDTO setId(Long id) {
        this.id = id;
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

    public String getWorkAddress() {
        return workAddress;
    }

    public UserDetailsDTO setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
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
