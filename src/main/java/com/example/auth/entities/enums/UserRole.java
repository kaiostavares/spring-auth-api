package com.example.auth.entities.enums;

public enum UserRole {
    USER("user"),
    PROFESSOR("professor");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
