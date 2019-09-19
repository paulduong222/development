import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AircraftDetailReportComponent } from './aircraft-detail-report.component';

describe('AircraftDetailReportComponent', () => {
  let component: AircraftDetailReportComponent;
  let fixture: ComponentFixture<AircraftDetailReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AircraftDetailReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AircraftDetailReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
