import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SummaryByResourceReportComponent } from './summary-by-resource-report.component';

describe('SummaryByResourceReportComponent', () => {
  let component: SummaryByResourceReportComponent;
  let fixture: ComponentFixture<SummaryByResourceReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SummaryByResourceReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SummaryByResourceReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
