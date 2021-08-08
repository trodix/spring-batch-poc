import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { RxStompService  } from '@stomp/ng2-stompjs';
import { AppComponent } from './app.component';
import { JobExecutionListWebsocketService, JobExecutionProgressWebsocketService } from 'src/services/websocket/progress.websocket.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    RxStompService,
    JobExecutionListWebsocketService,
    JobExecutionProgressWebsocketService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
