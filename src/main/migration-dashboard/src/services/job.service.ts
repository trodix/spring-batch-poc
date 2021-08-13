import { Injectable } from '@angular/core';
import { Job } from 'src/models/Job';
import { JobExecution } from 'src/models/JobExecution';
import { JobExecutionNotification } from 'src/models/JobExecutionNotification';
import { JobNotification } from 'src/models/JobNotification';

export const WEBSOCKET_JOB_EXECUTION_BASE_URL = 'topic/job-execution';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  private executedJobs: Job[] = [];

  constructor() { }

  addExecutedJob(job: Job) {
    this.executedJobs.push(job);
  }

  getExecutedJobs() {
    return this.executedJobs;
  }

  addJob(notification: JobNotification) {
    const found = this.executedJobs.find(j => j.name === notification.jobName);
    if (found) {
      found.jobNotifications.push(notification.notification);
    } else {
      const job: Job = {
        name: notification.jobName,
        jobExecutions: [],
        jobNotifications: [
          notification.notification
        ]
      }
      this.executedJobs.push(job);
    }
  }

  addJobExecution(jobName: string, jobExecution: JobExecution) {
    this.executedJobs = this.getExecutedJobs().map(job => {
      if (job.name === jobName) {
        return {
          ...job,
          jobExecutions: [
            ...job.jobExecutions,
            jobExecution
          ]
        }
      }
      return job;
    })
  }

  addJobExecutionOutput(notification: JobExecutionNotification) {
    this.getExecutedJobs().forEach(job => {
      if (job.jobExecutions.length > 0) {
        job.jobExecutions.forEach(jobExecution => {
          if (jobExecution.jobExecutionId === notification.jobId) {
            jobExecution.output.push(notification);
          }
        })
      } else {
        this.addJobExecution(job.name, {
          jobExecutionId: notification.jobId, output: [notification]
        });
      }
    });
  }
}
