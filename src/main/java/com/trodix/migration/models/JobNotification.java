package com.trodix.migration.models;

public class JobNotification {

    private String jobName;
    private String notification;

    public JobNotification() {
        // no-op
    }

    public JobNotification(String jobName, String notification) {
        this.jobName = jobName;
        this.notification = notification;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

}
