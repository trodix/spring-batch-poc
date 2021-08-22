import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TodoJobConfigComponent } from 'src/app/components/jobs-config/job-todo-config/job-todo-config.component';
import { JobsListComponent } from 'src/app/components/jobs-list/jobs-list.component';
import { MigrationJobHistoryComponent } from 'src/app/components/migration-job-history/migration-job-history.component';

const routes: Routes = [
  { path: 'jobs-list', component: JobsListComponent },
  { path: 'history', component: MigrationJobHistoryComponent },
  { path: 'job/todo-job', component: TodoJobConfigComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
