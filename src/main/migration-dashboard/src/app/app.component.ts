import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { Job } from 'src/models/Job';
import { SocketResponse } from 'src/models/SocketResponse';
import { JobExecutionListWebsocketService, JobExecutionProgressWebsocketService } from 'src/services/websocket/progress.websocket.service';
import { Validators } from '@angular/forms';
import { MigrationConfig } from 'src/models/MigrationConfig';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title: string = 'Application Migration Spring Batch';
  jobList: Observable<Job>[] = [];
  obsList: Observable<SocketResponse>[] = [];

  configForm = this.fb.group({
    migrationSourceUrl: ['', [ Validators.required, Validators.pattern(new RegExp("(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]+\.[^\s]{2,}|www\.[a-zA-Z0-9]+\.[^\s]{2,})")) ]],
    migrationDestinationUrl: ['', [ Validators.required, Validators.pattern(new RegExp("(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]+\.[^\s]{2,}|www\.[a-zA-Z0-9]+\.[^\s]{2,})")) ]],
    migrationDestinationAuthEndpoint: ['', Validators.required],
    migrationDestinationUsername: ['', Validators.required],
    migrationDestinationPassword: ['', Validators.required],
  });

  constructor(private fb: FormBuilder, private http: HttpClient, private jobExecutionListWebsocketService: JobExecutionListWebsocketService, private jobExecutionProgressWebsocketService: JobExecutionProgressWebsocketService) {
  }

  ngOnInit(): void {
  }

  runMigration(): void {
    const body: MigrationConfig = {
      sourceEndpoint: this.configForm.get('migrationSourceUrl').value,
      destinationEndpoint: this.configForm.get('migrationDestinationUrl').value,
      destinationAuthEndpoint: this.configForm.get('migrationDestinationAuthEndpoint').value,
      destinationUsername: this.configForm.get('migrationDestinationUsername').value,
      destinationPassword: this.configForm.get('migrationDestinationPassword').value,
    }

    this.http.post('http://localhost:8001/api/migration/run', body).subscribe();

    const obs = this.jobExecutionListWebsocketService.getObservable();
    this.obsList.push(obs);
    obs.subscribe({
      next: this.onNewJob,
      error: (err: Error) => { console.log(err); }
    });
  }

  onNewJob(data: SocketResponse) {
    console.log("a");
    // this.jobList = this.jobList.filter(i => i.subscribe(d => d.id != parseInt(data.message))
    console.log(data.message);
    const job: Job = {
      id: parseInt(data.message),
      outputConsole: ""
    }
    // this.jobList.push(job);

    // const obs = this.jobExecutionProgressWebsocketService.getObservable();
    // obs.subscribe({
    //   next: value => this.onNewJobData(value, job.id),
    //   error: (err: Error) => { console.log(err); }
    // });
  }

  // onNewJobData(data: SocketResponse, jobId: number) {
  //   console.log(jobId, data.message);
  // }

}
