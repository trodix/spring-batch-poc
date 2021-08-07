package com.trodix.migration.controllers;

import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/migration")
public class MigrationController {

    Logger logger = LoggerFactory.getLogger(MigrationController.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @PostMapping(value = "/run")
    public void run() throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        JobParameters jobParameters = new JobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        logger.info("Job " + jobExecution.getJobId() + " lancé");

        while (jobExecution.isRunning()) {
            logger.info("...");
        }
        logger.info("Job " + jobExecution.getJobId() + " terminé en "
                + ChronoUnit.MILLIS.between(jobExecution.getStartTime().toInstant(),
                        jobExecution.getEndTime().toInstant())
                + " ms. STATUS: " + jobExecution.getStatus().getBatchStatus());
    }

}
