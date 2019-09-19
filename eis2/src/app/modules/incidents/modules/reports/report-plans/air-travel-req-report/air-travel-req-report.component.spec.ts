import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AirTravelReqReportComponent } from './air-travel-req-report.component';

describe('AirTravelReqReportComponent', () => {
  let component: AirTravelReqReportComponent;
  let fixture: ComponentFixture<AirTravelReqReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AirTravelReqReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AirTravelReqReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
