import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailForReleaseReportComponent } from './avail-for-release-report.component';

describe('AvailForReleaseReportComponent', () => {
  let component: AvailForReleaseReportComponent;
  let fixture: ComponentFixture<AvailForReleaseReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AvailForReleaseReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvailForReleaseReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
