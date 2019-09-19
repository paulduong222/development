import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LastWorkDayReportComponent } from './last-work-day-report.component';

describe('LastWorkDayReportComponent', () => {
  let component: LastWorkDayReportComponent;
  let fixture: ComponentFixture<LastWorkDayReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LastWorkDayReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LastWorkDayReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
