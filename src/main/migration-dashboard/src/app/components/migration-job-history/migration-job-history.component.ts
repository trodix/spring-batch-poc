import { Component, OnInit } from '@angular/core';
import { JobNotification } from 'src/models/JobNotification';
import { JobService, WEBSOCKET_JOB_EXECUTION_BASE_URL } from 'src/services/job.service';
import { WebsocketService } from 'src/services/websocket.service';

@Component({
  selector: 'app-migration-job-history',
  templateUrl: './migration-job-history.component.html',
  styleUrls: ['./migration-job-history.component.scss']
})
export class MigrationJobHistoryComponent implements OnInit {

  subscribedUrl: string[] = [];

  constructor(private jobService: JobService, private websocketService: WebsocketService) { }

  ngOnInit(): void {
    this.initWebsocket();
  }

  get jobList() {
    return this.jobService.getExecutedJobs();
  }

  private initWebsocket() {
    this.websocketService.initWebSocket().then(() => {
      this.websocketService.subscribe(WEBSOCKET_JOB_EXECUTION_BASE_URL, (event) => {
        this.subscribedUrl.push(WEBSOCKET_JOB_EXECUTION_BASE_URL);
        const response: JobNotification = event.body;
        console.log(response);
        this.jobService.addJob(response);
        console.log(this.jobService.getExecutedJobs());
      });
    });
  }

}
