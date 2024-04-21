package com.ivan.projectmanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String phone;
    @Column
    private String workPhone;
    @Column
    private String workAddress;
    @Column
    private String department;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public UserDetails setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserDetails setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserDetails setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public UserDetails setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public UserDetails setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public UserDetails setDepartment(String department) {
        this.department = department;
        return this;
    }
}
