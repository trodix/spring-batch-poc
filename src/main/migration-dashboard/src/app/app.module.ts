import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MigrationJobHistoryComponent } from 'src/app/components/migration-job-history/migration-job-history.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MigrationConfigComponent } from './components/jobs-config/job-todo-config/job-todo-config.component';
import { JobsListComponent } from './components/jobs-list/jobs-list.component';


@NgModule({
  declarations: [
    AppComponent,
    MigrationConfigComponent,
    MigrationJobHistoryComponent,
    JobsListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatTabsModule,
    MatExpansionModule
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
