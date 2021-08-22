import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TodoJobConfigComponent } from './job-todo-config.component';

describe('MigrationConfigComponent', () => {
  let component: TodoJobConfigComponent;
  let fixture: ComponentFixture<TodoJobConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TodoJobConfigComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TodoJobConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
