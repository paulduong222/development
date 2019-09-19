import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportCustomViewComponent } from './report-custom-view.component';

describe('ReportCustomViewComponent', () => {
  let component: ReportCustomViewComponent;
  let fixture: ComponentFixture<ReportCustomViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportCustomViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportCustomViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
