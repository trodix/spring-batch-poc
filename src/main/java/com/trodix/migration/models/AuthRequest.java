package com.trodix.migration.models;

public class AuthRequest {

    private String username;
    private String password;

    public AuthRequest() {
        // no-op
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passowrd) {
        this.password = passowrd;
    }

}