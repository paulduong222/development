import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TentativePosterReportComponent } from './tentative-poster-report.component';

describe('TentativePosterReportComponent', () => {
  let component: TentativePosterReportComponent;
  let fixture: ComponentFixture<TentativePosterReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TentativePosterReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TentativePosterReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
