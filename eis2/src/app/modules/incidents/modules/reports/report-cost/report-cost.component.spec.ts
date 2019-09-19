import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportCostComponent } from './report-cost.component';

describe('ReportCostComponent', () => {
  let component: ReportCostComponent;
  let fixture: ComponentFixture<ReportCostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportCostComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportCostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
