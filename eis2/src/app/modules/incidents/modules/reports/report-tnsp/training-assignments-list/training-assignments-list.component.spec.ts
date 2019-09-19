import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingAssignmentsListComponent } from './training-assignments-list.component';

describe('TrainingAssignmentsListComponent', () => {
  let component: TrainingAssignmentsListComponent;
  let fixture: ComponentFixture<TrainingAssignmentsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingAssignmentsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingAssignmentsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
