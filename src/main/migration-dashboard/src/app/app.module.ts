import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MigrationJobHistoryComponent } from 'src/app/components/migration-job-history/migration-job-history.component';
import { WebsocketService } from 'src/services/websocket.service';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MigrationConfigComponent } from './components/migration-config/migration-config.component';

@NgModule({
  declarations: [
    AppComponent,
    MigrationConfigComponent,
    MigrationJobHistoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    WebsocketService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
