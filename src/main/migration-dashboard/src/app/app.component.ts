import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'migration-dashboard';

  constructor(private http: HttpClient) {}

  runMigration(): void {
    this.http.post('http://localhost:8001/api/migration/run', null).subscribe();
  }
}
