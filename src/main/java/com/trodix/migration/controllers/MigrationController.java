package com.trodix.migration.controllers;

import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

import com.trodix.migration.models.MigrationConfigDto;
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
@RequestMapping(value = "/api/migration")
public class MigrationController {

    public static final String TOPIC_JOB_EXECUTION_PREFIX = "/topic/job-execution";

    private Logger logger = LoggerFactory.getLogger(MigrationController.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MigrationConfigService migrationConfigService;

    @PostMapping("/run")
    public void run(@RequestBody MigrationConfigDto config) throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException {

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

        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        notifyClient(jobExecution);
    }

    private void notifyClient(JobExecution jexec) {
        logger.info("Job " + jexec.getJobId() + " lancé");
        messagingTemplate.convertAndSend(TOPIC_JOB_EXECUTION_PREFIX, jexec.getJobId());
        messagingTemplate.convertAndSend(TOPIC_JOB_EXECUTION_PREFIX + "/" + jexec.getJobId(),
                "Job " + jexec.getJobId() + " lancé");

        while (jexec.isRunning()) {
            logger.info("...");
            messagingTemplate.convertAndSend(TOPIC_JOB_EXECUTION_PREFIX + "/" + jexec.getJobId(),
                    "Job " + jexec.getJobId() + "...");
        }

        logger.info("Job " + jexec.getJobId() + " terminé en "
                + ChronoUnit.MILLIS.between(jexec.getStartTime().toInstant(), jexec.getEndTime().toInstant())
                + " ms. STATUS: " + jexec.getStatus().getBatchStatus());

        messagingTemplate.convertAndSend(TOPIC_JOB_EXECUTION_PREFIX + "/" + jexec.getJobId(),
                "Job " + jexec.getJobId() + " terminé en "
                        + ChronoUnit.MILLIS.between(jexec.getStartTime().toInstant(), jexec.getEndTime().toInstant())
                        + " ms. STATUS: " + jexec.getStatus().getBatchStatus());
    }

}
