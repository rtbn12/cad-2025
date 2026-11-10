package com.example.demo.dto;

import com.example.demo.entity.UserRole;

import java.time.LocalDateTime;

public class CabinetView {

    private final String login;
    private final UserRole role;
    private final LocalDateTime createdAt;
    private final String firstName;
    private final String middleName;
    private final String lastName;

    public CabinetView(String login, UserRole role, LocalDateTime createdAt,
                       String firstName, String middleName, String lastName) {
        this.login = login;
        this.role = role;
        this.createdAt = createdAt;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }
    public UserRole getRole() {
        return role;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public String getLastName() {
        return lastName;
    }
}


