package com.trodix.migration.domain.todo.services;

import java.util.Arrays;
import java.util.List;
import com.trodix.migration.domain.todo.configuration.RestConfig;
import com.trodix.migration.domain.todo.models.TodoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private RestConfig restConfig;

    @Autowired
    private TodoMigrationConfigService migrationConfigService;

    public TodoService() {
        // no-op
    }

    public List<TodoDto> getTodos() {
        ResponseEntity<TodoDto[]> response = this.restConfig.getRestTemplate().getForEntity(migrationConfigService.getSourceApiEndpoint() + migrationConfigService.getSourceTodoEndpoint(),
                TodoDto[].class);
        TodoDto[] todosData = response.getBody();

        return Arrays.asList(todosData);
    }

}
