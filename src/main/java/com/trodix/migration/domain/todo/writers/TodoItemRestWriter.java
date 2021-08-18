package com.trodix.migration.domain.todo.writers;

import java.io.IOException;
import java.util.List;
import com.trodix.migration.domain.todo.configuration.RestConfig;
import com.trodix.migration.domain.todo.models.AuthRequest;
import com.trodix.migration.domain.todo.models.AuthResponse;
import com.trodix.migration.domain.todo.models.TodoBackendDto;
import com.trodix.migration.domain.todo.services.TodoMigrationConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TodoItemRestWriter implements ItemWriter<TodoBackendDto> {

    Logger logger = LoggerFactory.getLogger(TodoItemRestWriter.class);

    @Autowired
    private TodoMigrationConfigService migrationConfigService;

    @Autowired
    RestConfig restConfig;

    @Override
    public void write(final List<? extends TodoBackendDto> items) throws Exception {
        String token = getAuthToken();
        final RestTemplate restTemplate = restConfig.getRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        for (TodoBackendDto item : items) {
            HttpEntity<TodoBackendDto> entity = new HttpEntity<>(item, headers);
            final ResponseEntity<TodoBackendDto> response = restTemplate
                    .postForEntity(
                            migrationConfigService.getBackendApiEndpoint()
                                    + migrationConfigService.getBackendTodoEndpoint(),
                            entity, TodoBackendDto.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Migration réussie de l'item todo: " + item.getId().toString());
            } else if (response.getStatusCode().isError()) {
                throw new IOException(
                        "Erreur lors de l'enregistrement du l'item id: " + item.getId());
            }
        }

        logger.info("Nombre d'item envoyés: " + items.size());

    }

    private String getAuthToken() throws IOException {

        final RestTemplate restTemplate = restConfig.getRestTemplate();
        final AuthRequest requestBody = new AuthRequest();
        requestBody.setUsername(migrationConfigService.getUsername());
        requestBody.setPassword(migrationConfigService.getPassword());

        final ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
                migrationConfigService.getBackendApiEndpoint()
                        + migrationConfigService.getSigninEndpoint(),
                requestBody, AuthResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            final String token = response.getBody().getAccessToken();

            return token;
        }

        throw new IOException(
                "Impossible de récupérer un token d'authentification auprès du backend.");
    }

}
