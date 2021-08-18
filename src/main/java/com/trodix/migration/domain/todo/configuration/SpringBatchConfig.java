package com.trodix.migration.domain.todo.configuration;

import com.trodix.migration.core.configuration.DataSourceConfig;
import com.trodix.migration.core.services.JobService;
import com.trodix.migration.domain.todo.models.TodoBackendDto;
import com.trodix.migration.domain.todo.models.TodoDto;
import com.trodix.migration.domain.todo.processor.TodoItemProcessor;
import com.trodix.migration.domain.todo.readers.TodoItemReader;
import com.trodix.migration.domain.todo.services.TodoService;
import com.trodix.migration.domain.todo.writers.TodoItemRestWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private TodoItemProcessor todoItemProcessor;

    @Autowired
    private TodoItemRestWriter todoItemWriter;

    @Autowired
    private TodoService todoService;

    @Autowired
    private DataSourceConfig datasourceConfig;

    @Bean
    public Job todoJob() {
        final String jobName = "todos-migration-job";

        Step step1 = stepBuilderFactory.get("get-todo-items").<TodoDto, TodoBackendDto>chunk(30)
                .reader(todoItemReader())
                .processor(todoItemProcessor)
                .writer(todoItemWriter)
                .build();

        Job todoJob = jobBuilderFactory.get(jobName)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();

        this.jobService.registerJob(jobName);

        return todoJob;
    }

    @Bean
    public TodoItemReader todoItemReader() {
        TodoItemReader reader = new TodoItemReader(this.todoService.getTodos());
        reader.setDataSource(this.datasourceConfig.todoDataSource());
        return reader;
    }

}
