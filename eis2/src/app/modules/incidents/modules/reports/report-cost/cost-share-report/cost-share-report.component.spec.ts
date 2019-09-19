import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CostShareReportComponent } from './cost-share-report.component';

describe('CostShareReportComponent', () => {
  let component: CostShareReportComponent;
  let fixture: ComponentFixture<CostShareReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CostShareReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CostShareReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
