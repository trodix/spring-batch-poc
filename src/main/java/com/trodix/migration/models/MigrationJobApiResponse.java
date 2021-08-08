package com.trodix.migration.models;

public class MigrationJobApiResponse {

    private String jobName;

    public MigrationJobApiResponse(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

}
