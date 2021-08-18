package com.trodix.migration.core.controllers;

import java.util.List;
import java.util.stream.Collectors;
import com.trodix.migration.core.models.JobExecutionDTO;
import com.trodix.migration.core.services.JobService;
import org.springframework.batch.core.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/migration/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/job-list")
    public List<String> getJobList() {
        return jobService.getJobList();
    }

    @GetMapping("/job-instances")
    public List<JobInstance> getJobInstances(@RequestParam String jobName, @RequestParam int start, @RequestParam int count) {
        return jobService.getJobInstances(jobName, start, count);
    }

    @GetMapping("/job-executions")
    public List<JobExecutionDTO> getJobExecutions(@RequestParam Long jobInstanceId, @RequestParam String jobName) {
        JobInstance jobInstance = new JobInstance(jobInstanceId, jobName);
        return jobService.getJobExecutions(jobInstance).stream().map(i -> new JobExecutionDTO(i)).collect(Collectors.toList());
    }
    
}
