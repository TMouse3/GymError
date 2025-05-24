package com.QLGym.GymError.dto;

public class LoginResponse {
    private String token;
    private String role;
    private String username;

    public LoginResponse(String token, String role, String username) {
        this.token = token;
        this.role = role;
        this.username = username;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }
} 