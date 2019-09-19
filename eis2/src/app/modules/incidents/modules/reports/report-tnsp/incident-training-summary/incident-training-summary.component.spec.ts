import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncidentTrainingSummaryComponent } from './incident-training-summary.component';

describe('IncidentTrainingSummaryComponent', () => {
  let component: IncidentTrainingSummaryComponent;
  let fixture: ComponentFixture<IncidentTrainingSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncidentTrainingSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncidentTrainingSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
