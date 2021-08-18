package com.trodix.migration.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private List<String> jobList;

    @Autowired
    private JobExplorer jobExplorer;

    public JobService() {
        jobList = new ArrayList<>();
        init();
    }

    protected void init() {
        // no-op
    }

    /**
     * Ajoute un nouveau job à la liste
     * 
     * @param jobName Le nom du job à ajouter
     */
    public void registerJob(String jobName) {
        this.jobList.add(jobName);
    }

    /**
     * Récupère la liste des Jobs configurés
     * 
     * @return La liste de jobs
     */
    public List<String> getJobList() {
        return jobList;
    }

    public List<JobInstance> findJobInstancesByJobName(String jobName, int start, int count) {
        return this.jobExplorer.findJobInstancesByJobName(jobName, start, count);
    }

    public Set<JobExecution> findRunningJobExecutions(String jobName) {
        return this.jobExplorer.findRunningJobExecutions(jobName);
    }

    public JobExecution getJobExecution(Long jobExecutionId) {
        return this.jobExplorer.getJobExecution(jobExecutionId);
    }

    public List<JobExecution> getJobExecutions(JobInstance jobInstance) {
        return this.jobExplorer.getJobExecutions(jobInstance);
    }

    public List<JobInstance> getJobInstances(String jobName, int start, int count) {
        return this.jobExplorer.getJobInstances(jobName, start, count);
    }

    public StepExecution getStepExecution(Long jobExecutionId, Long stepExecutionId) {
        return this.jobExplorer.getStepExecution(jobExecutionId, stepExecutionId);
    }

    public JobInstance getJobInstance(Long instanceId) {
        return this.jobExplorer.getJobInstance(instanceId);
    }

    public List<String> getJobNames() {
        return this.jobExplorer.getJobNames();
    }

    public int getJobInstanceCount(String jobName) throws NoSuchJobException {
        return this.jobExplorer.getJobInstanceCount(jobName);
    }

}
