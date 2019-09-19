import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroundSupportReportComponent } from './ground-support-report.component';

describe('GroundSupportReportComponent', () => {
  let component: GroundSupportReportComponent;
  let fixture: ComponentFixture<GroundSupportReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroundSupportReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroundSupportReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
