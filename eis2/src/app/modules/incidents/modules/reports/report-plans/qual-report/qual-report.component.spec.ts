import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QualReportComponent } from './qual-report.component';

describe('QualReportComponent', () => {
  let component: QualReportComponent;
  let fixture: ComponentFixture<QualReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QualReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QualReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
