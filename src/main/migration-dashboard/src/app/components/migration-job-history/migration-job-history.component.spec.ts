import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MigrationJobHistoryComponent } from './migration-job-history.component';

describe('MigrationJobHistoryComponent', () => {
  let component: MigrationJobHistoryComponent;
  let fixture: ComponentFixture<MigrationJobHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MigrationJobHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MigrationJobHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
