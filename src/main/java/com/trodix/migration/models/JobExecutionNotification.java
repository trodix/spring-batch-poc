package com.trodix.migration.models;

public class JobExecutionNotification {

    private Long jobExecutionId;
    private String notification;

    public JobExecutionNotification() {
        // no-op
    }

    public JobExecutionNotification(Long jobExecutionId, String notification) {
        this.jobExecutionId = jobExecutionId;
        this.notification = notification;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

}
