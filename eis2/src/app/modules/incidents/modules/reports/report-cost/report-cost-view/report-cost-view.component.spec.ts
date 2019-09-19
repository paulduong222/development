import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportCostViewComponent } from './report-cost-view.component';

describe('ReportCostViewComponent', () => {
  let component: ReportCostViewComponent;
  let fixture: ComponentFixture<ReportCostViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportCostViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportCostViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
