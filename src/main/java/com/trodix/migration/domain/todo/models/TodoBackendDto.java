package com.trodix.migration.domain.todo.models;

import java.util.UUID;

public class TodoBackendDto {

    private UserBackendDto user;
    private UUID id;
    private String title;
    private Boolean done;

    public TodoBackendDto() {
        // no-op
    }

    public UserBackendDto getUser() {
        return user;
    }

    public void setUser(UserBackendDto user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

}
