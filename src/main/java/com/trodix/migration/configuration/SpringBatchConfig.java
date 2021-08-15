package com.trodix.migration.configuration;

import com.trodix.migration.models.TodoBackendDto;
import com.trodix.migration.models.TodoDto;
import com.trodix.migration.readers.TodoItemRestReader;
import com.trodix.migration.services.JobService;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
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
    private ItemReader<TodoDto> todoItemReader;

    @Autowired
    private ItemProcessor<TodoDto, TodoBackendDto> todoItemProcessor;

    @Autowired
    private ItemWriter<TodoBackendDto> todoItemWriter;

    @Bean
    public Job todoJob() {
        final String jobName = "todos-migration-job";
        
        Step step = stepBuilderFactory.get("migrate-todo").<TodoDto, TodoBackendDto>chunk(30).reader(todoItemReader)
                .processor(todoItemProcessor).writer(todoItemWriter).build();

        Job todoJob = jobBuilderFactory.get(jobName).incrementer(new RunIdIncrementer()).start(step).build();

        this.jobService.registerJob(jobName);

        return todoJob;
    }

    @Bean
    public TodoItemRestReader todoItemReader() {
        return new TodoItemRestReader();
    }

}
