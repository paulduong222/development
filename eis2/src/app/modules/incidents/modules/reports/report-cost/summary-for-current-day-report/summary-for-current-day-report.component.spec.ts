import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SummaryForCurrentDayReportComponent } from './summary-for-current-day-report.component';

describe('SummaryForCurrentDayReportComponent', () => {
  let component: SummaryForCurrentDayReportComponent;
  let fixture: ComponentFixture<SummaryForCurrentDayReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SummaryForCurrentDayReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SummaryForCurrentDayReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
