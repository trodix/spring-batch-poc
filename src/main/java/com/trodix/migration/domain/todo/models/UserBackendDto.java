package com.trodix.migration.domain.todo.models;

import java.util.UUID;

public class UserBackendDto {

    private UUID id;
    private String username;

    public UserBackendDto() {
        // no-op
    }

    public UserBackendDto(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
