package com.trodix.migration.domain.todo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.trodix.migration.domain.todo.models.TodoDto;
import org.springframework.jdbc.core.RowMapper;

public class TodoItemRowMapper implements RowMapper<TodoDto> {

    public static final String TITLE_COLUMN = "title";

    @Override
    public TodoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        TodoDto todoItem = new TodoDto();

        todoItem.setTitle(rs.getString(TITLE_COLUMN));

        return todoItem;
    }

}
