package com.trodix.migration.domain.todo.readers;

import java.util.List;
import javax.sql.DataSource;
import com.trodix.migration.core.readers.ItemMultipleSourceReader;
import com.trodix.migration.core.readers.ListItemReader;
import com.trodix.migration.domain.todo.mapper.TodoItemRowMapper;
import com.trodix.migration.domain.todo.models.TodoDto;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class TodoItemReader extends ListItemReader<TodoDto>
        implements ItemMultipleSourceReader<TodoDto> {

    private DataSource dataSource;

    public TodoItemReader(List<TodoDto> dataProvider) {
        super(dataProvider);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public TodoDto read() throws Exception, UnexpectedInputException, ParseException,
            NonTransientResourceException, DataAccessException {

        TodoDto nextItem = super.read();

        if (nextItem != null) {
            nextItem = handleNextSource(nextItem);
        }

        return nextItem;
    }

    public TodoDto handleNextSource(TodoDto item) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.dataSource);
        SqlParameterSource params =
                new MapSqlParameterSource().addValue("id", item.getId());
        String query =
                "SELECT todo_data.id, todo_data.title FROM todo_data WHERE todo_data.id = :id";

        try {
            return template.queryForObject(query, params, new TodoItemRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return item;
        }
    }

}
