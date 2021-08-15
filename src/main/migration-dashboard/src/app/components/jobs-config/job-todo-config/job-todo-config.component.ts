import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { JobApiResponse } from 'src/models/JobApiResponse';
import { MigrationConfig } from 'src/models/MigrationConfig';
import { SpringBatchService } from 'src/services/springbatch.service';

@Component({
  selector: 'app-job-todo-config',
  templateUrl: './job-todo-config.component.html',
  styleUrls: ['./job-todo-config.component.scss']
})
export class MigrationConfigComponent {

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

  constructor(private fb: FormBuilder, private http: HttpClient, private springBatchService: SpringBatchService) {
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
        this.springBatchService.refresh();
      });
    }
  }

  resetForm() {
    this.configForm.reset();
  }
}
