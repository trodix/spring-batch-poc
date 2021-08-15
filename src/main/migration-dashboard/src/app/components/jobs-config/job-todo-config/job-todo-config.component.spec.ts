import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MigrationConfigComponent } from './job-todo-config.component';

describe('MigrationConfigComponent', () => {
  let component: MigrationConfigComponent;
  let fixture: ComponentFixture<MigrationConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MigrationConfigComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MigrationConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
