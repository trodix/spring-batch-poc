import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { map, mergeMap } from 'rxjs/operators';
import { JobExecution } from "src/models/batch/JobExecution";
import { JobExecutionApiResponse } from "src/models/batch/JobExecutionApiResponse";
import { JobInstance } from "src/models/batch/JobInstance";

export const BACKEND_URL = 'http://localhost:8001';
export const MIGRATION_JOB_API_URL = 'api/migration/jobs';

@Injectable({
  providedIn: 'root'
})
export class SpringBatchService {

  public jobExecutions$: Subject<JobExecution[]> = new Subject();

  constructor(private http: HttpClient) { }

  public getJobList(): Observable<string[]> {
    return this.http.get<string[]>(`${BACKEND_URL}/${MIGRATION_JOB_API_URL}/job-list`);
  }

  public getJobInstances(jobName: string): Observable<JobInstance[]> {
    return this.http.get<JobInstance[]>(`${BACKEND_URL}/${MIGRATION_JOB_API_URL}/job-instances?jobName=${jobName}&start=0&count=999`);
  }

  public getJobExecutions(jobName: string, jobInstanceId: number): Observable<JobExecutionApiResponse[]> {
    return this.http.get<JobExecutionApiResponse[]>(`${BACKEND_URL}/${MIGRATION_JOB_API_URL}/job-executions?jobName=${jobName}&jobInstanceId=${jobInstanceId}`);
  }

  initJobData() {
    this.getJobList().subscribe(data => {
      data.forEach(job => {
        this.getJobInstances(job).subscribe(jobInstances => {
          jobInstances.forEach(jobInstance => {
            this.getJobExecutions(jobInstance.jobName, jobInstance.instanceId).subscribe(jobExecutionsResponse => {
              console.log(jobExecutionsResponse.map(i => i.jobExecution));
              this.jobExecutions$.next(jobExecutionsResponse.map(i => i.jobExecution));
            })
          })
        });
      })
    })
  }

  refresh() {
    this.initJobData();
  }

}