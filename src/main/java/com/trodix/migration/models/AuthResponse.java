package com.trodix.migration.models;

import java.util.List;

public class AuthResponse {

    private String id;
    private String accessToken;
    private String email;
    private String username;
    private List<String> roles;

    public AuthResponse() {
        // no-op
    }
    
    public AuthResponse(String id, String accessToken, String email, String username, List<String> roles) {
        this.id = id;
        this.accessToken = accessToken;
        this.email = email;
        this.username = username;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}