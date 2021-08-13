import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { JobApiResponse } from 'src/models/JobApiResponse';
import { JobExecutionNotification } from 'src/models/JobExecutionNotification';
import { MigrationConfig } from 'src/models/MigrationConfig';
import { JobService, WEBSOCKET_JOB_EXECUTION_BASE_URL } from 'src/services/job.service';
import { WebsocketService } from 'src/services/websocket.service';

@Component({
  selector: 'app-migration-config',
  templateUrl: './migration-config.component.html',
  styleUrls: ['./migration-config.component.scss']
})
export class MigrationConfigComponent implements OnDestroy {

  jobExecutions: any[] = [];
  subscribedUrl: string[] = [];

  configForm = this.fb.group({
    migrationSourceUrl: ['', [Validators.pattern(new RegExp("(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]+\.[^\s]{2,}|www\.[a-zA-Z0-9]+\.[^\s]{2,})"))]],
    migrationSourceTodoPath: [''],
    migrationDestinationUrl: ['', [Validators.pattern(new RegExp("(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]+\.[^\s]{2,}|www\.[a-zA-Z0-9]+\.[^\s]{2,})"))]],
    migrationDestinationTodoPath: [''],
    migrationDestinationAuthEndpoint: [''],
    migrationDestinationUsername: [''],
    migrationDestinationPassword: [''],
  });

  constructor(private fb: FormBuilder, private http: HttpClient, private jobService: JobService, private websocketService: WebsocketService) {
  }


  ngOnDestroy(): void {
    this.subscribedUrl.forEach(url => {
      this.websocketService.unsubscribeToWebSocketEvent(url);
    })
  }

  runMigration(): void {
    const body: MigrationConfig = {
      sourceApiEndpoint: this.configForm.get('migrationSourceUrl').value,
      sourceTodoEndpoint: this.configForm.get('migrationSourceTodoPath').value,
      destinationApiEndpoint: this.configForm.get('migrationDestinationUrl').value,
      destinationTodoEndpoint: this.configForm.get('migrationDestinationTodoPath').value,
      destinationAuthEndpoint: this.configForm.get('migrationDestinationAuthEndpoint').value,
      destinationUsername: this.configForm.get('migrationDestinationUsername').value,
      destinationPassword: this.configForm.get('migrationDestinationPassword').value,
    }

    if (this.configForm.valid) {
      this.http.post<JobApiResponse>('http://localhost:8001/api/migration/run/todo-job', body).subscribe(data => {
        this.initWebsocket(data.jobName);
      });
    }

  }

  private initWebsocket(jobName: string) {
    this.websocketService.initWebSocket().then(() => {
      this.websocketService.subscribe(`${WEBSOCKET_JOB_EXECUTION_BASE_URL}/${jobName}`, (event) => {
        this.subscribedUrl.push(`${WEBSOCKET_JOB_EXECUTION_BASE_URL}/${jobName}`);
        const response: JobExecutionNotification = event.body;
        console.log(response);
        this.jobService.addJobExecutionOutput(response);
      });
    });
  }


}
