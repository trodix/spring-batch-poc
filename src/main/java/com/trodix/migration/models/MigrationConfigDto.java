package com.trodix.migration.models;

public class MigrationConfigDto {

    private String sourceApiEndpoint;
    private String sourceTodoEndpoint;
    private String destinationApiEndpoint;
    private String destinationTodoEndpoint;
    private String destinationAuthEndpoint;
    private String destinationUsername;
    private String destinationPassword;

    public MigrationConfigDto() {
        // no-op
    }

    public String getSourceApiEndpoint() {
        return sourceApiEndpoint;
    }

    public void setSourceApiEndpoint(String sourceApiEndpoint) {
        this.sourceApiEndpoint = sourceApiEndpoint;
    }

    public String getDestinationApiEndpoint() {
        return destinationApiEndpoint;
    }

    public void setDestinationApiEndpoint(String destinationApiEndpoint) {
        this.destinationApiEndpoint = destinationApiEndpoint;
    }

    public String getDestinationAuthEndpoint() {
        return destinationAuthEndpoint;
    }

    public void setDestinationAuthEndpoint(String destinationAuthEndpoint) {
        this.destinationAuthEndpoint = destinationAuthEndpoint;
    }

    public String getDestinationUsername() {
        return destinationUsername;
    }

    public void setDestinationUsername(String destinationUsername) {
        this.destinationUsername = destinationUsername;
    }

    public String getDestinationPassword() {
        return destinationPassword;
    }

    public void setDestinationPassword(String destinationPassword) {
        this.destinationPassword = destinationPassword;
    }

    public String getSourceTodoEndpoint() {
        return sourceTodoEndpoint;
    }

    public void setSourceTodoEndpoint(String sourceTodoEndpoint) {
        this.sourceTodoEndpoint = sourceTodoEndpoint;
    }

    public String getDestinationTodoEndpoint() {
        return destinationTodoEndpoint;
    }

    public void setDestinationTodoEndpoint(String destinationTodoEndpoint) {
        this.destinationTodoEndpoint = destinationTodoEndpoint;
    }

    
}
