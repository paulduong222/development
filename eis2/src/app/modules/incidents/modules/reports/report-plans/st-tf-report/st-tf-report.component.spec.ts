import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StTfReportComponent } from './st-tf-report.component';

describe('StTfReportComponent', () => {
  let component: StTfReportComponent;
  let fixture: ComponentFixture<StTfReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StTfReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StTfReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
