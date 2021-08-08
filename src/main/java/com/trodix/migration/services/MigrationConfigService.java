package com.trodix.migration.services;

import com.trodix.migration.models.MigrationConfigDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MigrationConfigService {

    private MigrationConfigDto migrationConfig;

    @Value("${backend.endpoint.api}")
    private String backendApiEndpoint;

    @Value("${backend.endpoint.auth.login}")
    private String signinEndpoint;

    @Value("${backend.endpoint.todo}")
    private String backendTodoEndpoint;

    @Value("${backend.username}")
    private String username;

    @Value("${backend.password}")
    private String password;

    @Value("${remote.endpoint.api}")
    private String sourceApiEndpoint;

    @Value("${remote.endpoint.todo}")
    private String sourceTodoEndpoint;

    public MigrationConfigService() {
        this.migrationConfig = new MigrationConfigDto();
    }

    public MigrationConfigDto getMigrationConfig() {
        return migrationConfig;
    }

    public void setMigrationConfig(MigrationConfigDto migrationConfig) {
        this.migrationConfig = migrationConfig;
    }

    public String getBackendApiEndpoint() {
        if (StringUtils.hasLength(this.getMigrationConfig().getDestinationEndpoint())) {
            return this.getMigrationConfig().getDestinationEndpoint();
        }
        return backendApiEndpoint;
    }

    public String getBackendTodoEndpoint() {
        if (StringUtils.hasLength(this.getMigrationConfig().getDestinationTodoEndpoint())) {
            return this.getMigrationConfig().getDestinationTodoEndpoint();
        }
        return backendTodoEndpoint;
    }

    public String getSigninEndpoint() {
        if (StringUtils.hasLength(this.getMigrationConfig().getDestinationAuthEndpoint())) {
            return this.getMigrationConfig().getDestinationAuthEndpoint();
        }
        return signinEndpoint;
    }

    public String getUsername() {
        if (StringUtils.hasLength(this.getMigrationConfig().getDestinationUsername())) {
            return this.getMigrationConfig().getDestinationUsername();
        }
        return username;
    }

    public String getPassword() {
        if (StringUtils.hasLength(this.getMigrationConfig().getDestinationPassword())) {
            return this.getMigrationConfig().getDestinationPassword();
        }
        return password;
    }

    public String getSourceApiEndpoint() {
        if (StringUtils.hasLength(this.getMigrationConfig().getSourceApiEndpoint())) {
            return this.getMigrationConfig().getSourceApiEndpoint();
        }
        return sourceApiEndpoint;
    }

    public String getSourceTodoEndpoint() {
        if (StringUtils.hasLength(this.getMigrationConfig().getSourceTodoEndpoint())) {
            return this.getMigrationConfig().getSourceTodoEndpoint();
        }
        return sourceTodoEndpoint;
    }

}
