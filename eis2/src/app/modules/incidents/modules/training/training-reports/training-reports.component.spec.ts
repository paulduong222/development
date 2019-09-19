import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingReportsComponent } from './training-reports.component';

describe('TrainingReportsComponent', () => {
  let component: TrainingReportsComponent;
  let fixture: ComponentFixture<TrainingReportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingReportsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
