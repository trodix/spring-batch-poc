package com.trodix.migration.readers;

import java.util.List;

import com.trodix.migration.models.TodoDto;
import com.trodix.migration.services.TodoService;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

public class TodoItemRestReader implements ItemReader<TodoDto> {

    @Autowired
    private TodoService todoService;

    private Integer nextItemIndex;
    private List<TodoDto> todosData;

    public TodoItemRestReader() {
        nextItemIndex = 0;
    }

    @Override
    public TodoDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (todosData == null) {
            todosData = fetchTodoItemsDataFomApi();
        }

        TodoDto nextItem = null;

        if (nextItemIndex < todosData.size()) {
            nextItem = todosData.get(nextItemIndex);
            nextItemIndex++;
        } else {
            nextItemIndex = 0;
            nextItem = null;
        }

        return nextItem;
    }

    private List<TodoDto> fetchTodoItemsDataFomApi() {
        return this.todoService.getTodos();
    }

}
