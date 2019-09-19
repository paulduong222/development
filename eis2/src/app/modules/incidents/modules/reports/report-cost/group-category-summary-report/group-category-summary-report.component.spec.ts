import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupCategorySummaryReportComponent } from './group-category-summary-report.component';

describe('GroupCategorySummaryReportComponent', () => {
  let component: GroupCategorySummaryReportComponent;
  let fixture: ComponentFixture<GroupCategorySummaryReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupCategorySummaryReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupCategorySummaryReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
