package com.trodix.migration.domain.todo.controllers;

import java.text.MessageFormat;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import com.trodix.migration.core.models.MigrationJobApiResponse;
import com.trodix.migration.domain.todo.configuration.SpringBatchConfig;
import com.trodix.migration.domain.todo.models.TodoMigrationConfigDto;
import com.trodix.migration.domain.todo.services.TodoMigrationConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/migration/run")
public class TodoMigrationController {

    private Logger logger = LoggerFactory.getLogger(TodoMigrationController.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private SpringBatchConfig springBatchConfig;

    @Autowired
    private TodoMigrationConfigService migrationConfigService;

    @PostMapping("/todo-job")
    public MigrationJobApiResponse run(@RequestBody TodoMigrationConfigDto config)
            throws JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException,
            JobParametersInvalidException {

        this.migrationConfigService.setMigrationConfig(config);

        JobParameter sourceEndpoint =
                new JobParameter(migrationConfigService.getSourceApiEndpoint());
        JobParameter sourceTodoEndpoint =
                new JobParameter(migrationConfigService.getSourceTodoEndpoint());
        JobParameter destinationEndpoint =
                new JobParameter(migrationConfigService.getBackendApiEndpoint());
        JobParameter destinationTodoEndpoint =
                new JobParameter(migrationConfigService.getBackendTodoEndpoint());

        Map<String, JobParameter> params = new LinkedHashMap<>();
        params.put("sourceEndpoint", sourceEndpoint);
        params.put("sourceTodoEndpoint", sourceTodoEndpoint);
        params.put("destinationEndpoint", destinationEndpoint);
        params.put("destinationTodoEndpoint", destinationTodoEndpoint);
        JobParameters jobParameters = new JobParameters(params);
        Job job = springBatchConfig.todoJob();

        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            Long jobExecutionDuration = ChronoUnit.MILLIS.between(
                    jobExecution.getStartTime().toInstant(),
                    jobExecution.getEndTime().toInstant());
            logger.info(MessageFormat.format("Job {0} terminé en {1} ms. STATUT: {2}",
                    job.getName(),
                    jobExecutionDuration,
                    jobExecution.getStatus().getBatchStatus()));
        } catch (Exception e) {
            logger.error(
                    "Erreur pendant l'execution du job " + springBatchConfig.todoJob().getName(),
                    e);
        }

        return new MigrationJobApiResponse(job.getName());
    }

}
