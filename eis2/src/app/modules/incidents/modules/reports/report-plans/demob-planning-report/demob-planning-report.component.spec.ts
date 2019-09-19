import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DemobPlanningReportComponent } from './demob-planning-report.component';

describe('DemobPlanningReportComponent', () => {
  let component: DemobPlanningReportComponent;
  let fixture: ComponentFixture<DemobPlanningReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DemobPlanningReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DemobPlanningReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
