
import { Injectable } from '@angular/core';
import { InjectableRxStompConfig, RxStompService } from '@stomp/ng2-stompjs';
import { WebSocketOptions } from '../../models/WebSocketOptions';
import { WebSocketService } from '../websocket.service';
import { environment } from '../../environments/environment';

export const progressStompConfig: InjectableRxStompConfig = {
  webSocketFactory: () => {
    const hostname: string = window.location.hostname;
    const port: string = window.location.port;
    if (!environment.production) {
      return new WebSocket(`ws://${hostname}:8001/stomp`);
    }
    return new WebSocket(`ws://${hostname}:${port}/stomp`);
  }
};

@Injectable()
export class JobExecutionListWebsocketService extends WebSocketService {
  constructor(stompService: RxStompService) {
    super(stompService, progressStompConfig, new WebSocketOptions('/topic/job-execution'));
  }
}

@Injectable()
export class JobExecutionProgressWebsocketService extends WebSocketService {
  constructor(stompService: RxStompService) {
    const jobId = 1; // TODO passer au constructeur
    super(stompService, progressStompConfig, new WebSocketOptions(`/topic/job-execution/${jobId}`));
  }
}