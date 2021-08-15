import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { JobExecution } from 'src/models/batch/JobExecution';
import { SpringBatchService } from 'src/services/springbatch.service';

@Component({
  selector: 'app-migration-job-history',
  templateUrl: './migration-job-history.component.html',
  styleUrls: ['./migration-job-history.component.scss']
})
export class MigrationJobHistoryComponent implements OnInit {

  public jobExecutions$: Subject<JobExecution[]> = new Subject();

  constructor(private springBatchService: SpringBatchService) { }

  ngOnInit(): void {
    this.springBatchService.initJobData();
    this.jobExecutions$ = this.springBatchService.jobExecutions$;
  }

  refresh() {
    this.springBatchService.refresh();
  }

}
