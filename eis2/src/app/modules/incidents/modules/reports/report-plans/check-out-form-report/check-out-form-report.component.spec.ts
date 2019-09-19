import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckOutFormReportComponent } from './check-out-form-report.component';

describe('CheckOutFormReportComponent', () => {
  let component: CheckOutFormReportComponent;
  let fixture: ComponentFixture<CheckOutFormReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckOutFormReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckOutFormReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
