import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualDemobReportComponent } from './actual-demob-report.component';

describe('ActualDemobReportComponent', () => {
  let component: ActualDemobReportComponent;
  let fixture: ComponentFixture<ActualDemobReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActualDemobReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActualDemobReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
