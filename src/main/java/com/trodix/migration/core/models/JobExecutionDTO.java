package com.trodix.migration.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.batch.core.JobExecution;

public class JobExecutionDTO {

    @JsonIgnoreProperties(value = { "stepExecutions" })
    private JobExecution jobExecution;

    public JobExecutionDTO(JobExecution jobExecution) {
        this.jobExecution = jobExecution;
    }

    public JobExecution getJobExecution() {
        return jobExecution;
    }

    public void setJobExecution(JobExecution jobExecution) {
        this.jobExecution = jobExecution;
    }

}
