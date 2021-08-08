package com.trodix.migration.controllers;

import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

import com.trodix.migration.configuration.SpringBatchConfig;
import com.trodix.migration.models.JobExecutionNotification;
import com.trodix.migration.models.JobNotification;
import com.trodix.migration.models.MigrationConfigDto;
import com.trodix.migration.models.MigrationJobApiResponse;
import com.trodix.migration.services.MigrationConfigService;

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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/migration/run")
public class MigrationController {

    public static final String TOPIC_JOB_EXECUTION_PREFIX = "/topic/job-execution";

    private Logger logger = LoggerFactory.getLogger(MigrationController.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private SpringBatchConfig springBatchConfig;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MigrationConfigService migrationConfigService;

    @PostMapping("/todo-job")
    public MigrationJobApiResponse run(@RequestBody MigrationConfigDto config) throws JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {

        this.migrationConfigService.setMigrationConfig(config);

        JobParameter sourceEndpoint = new JobParameter(migrationConfigService.getSourceApiEndpoint());
        JobParameter sourceTodoEndpoint = new JobParameter(migrationConfigService.getSourceTodoEndpoint());
        JobParameter destinationEndpoint = new JobParameter(migrationConfigService.getBackendApiEndpoint());
        JobParameter destinationTodoEndpoint = new JobParameter(migrationConfigService.getBackendTodoEndpoint());

        Map<String, JobParameter> params = new LinkedHashMap<>();
        params.put("sourceEndpoint", sourceEndpoint);
        params.put("sourceTodoEndpoint", sourceTodoEndpoint);
        params.put("destinationEndpoint", destinationEndpoint);
        params.put("destinationTodoEndpoint", destinationTodoEndpoint);
        JobParameters jobParameters = new JobParameters(params);

        try {
            JobExecution jobExecution = jobLauncher.run(springBatchConfig.todoJob(), jobParameters);
            notifyClient(springBatchConfig.todoJob(), jobExecution);
        } catch (Exception e) {
            logger.error("Erreur pendant l'execution du job " + springBatchConfig.todoJob().getName(), e);
            messagingTemplate.convertAndSend(TOPIC_JOB_EXECUTION_PREFIX + "/" + springBatchConfig.todoJob().getName(),
            "Erreur pendant l'execution du job. " + e.getMessage());
        }

        return new MigrationJobApiResponse(springBatchConfig.todoJob().getName());
    }

    private void notifyClient(Job job, JobExecution jexec) {
        logger.info("Job " + job.getName() + " lancé avec l'id " + jexec.getJobId());
        messagingTemplate.convertAndSend(TOPIC_JOB_EXECUTION_PREFIX, new JobNotification(springBatchConfig.todoJob().getName(), "Job " + job.getName() + " lancé avec l'id " + jexec.getJobId()));
        messagingTemplate.convertAndSend(TOPIC_JOB_EXECUTION_PREFIX + "/" + job.getName(),
                new JobExecutionNotification(jexec.getJobId(), "Job " + job.getName() + " lancé"));

        while (jexec.isRunning()) {
            logger.info("...");
            messagingTemplate.convertAndSend(TOPIC_JOB_EXECUTION_PREFIX + "/" + job.getName(),
            new JobExecutionNotification(jexec.getJobId(), "..."));
        }

        logger.info("Job " + job.getName() + " terminé en "
                + ChronoUnit.MILLIS.between(jexec.getStartTime().toInstant(), jexec.getEndTime().toInstant())
                + " ms. STATUS: " + jexec.getStatus().getBatchStatus());

        messagingTemplate.convertAndSend(TOPIC_JOB_EXECUTION_PREFIX + "/" + job.getName(), new JobExecutionNotification(jexec.getJobId(), "Job " + job.getName() + " terminé en "
        + ChronoUnit.MILLIS.between(jexec.getStartTime().toInstant(), jexec.getEndTime().toInstant())
        + " ms. STATUS: " + jexec.getStatus().getBatchStatus()));
                
    }

}
