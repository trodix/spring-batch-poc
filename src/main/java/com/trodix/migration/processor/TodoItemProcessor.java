package com.trodix.migration.processor;

import java.util.UUID;

import com.trodix.migration.models.TodoBackendDto;
import com.trodix.migration.models.TodoDto;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TodoItemProcessor implements ItemProcessor<TodoDto, TodoBackendDto> {

    @Override
    public TodoBackendDto process(TodoDto item) throws Exception {

        TodoBackendDto todo = new TodoBackendDto();
        todo.setId(UUID.randomUUID());
        todo.setTitle(item.getTitle());
        todo.setDone(item.getCompleted());
        todo.setUser(null);

        return todo;
    }

}
