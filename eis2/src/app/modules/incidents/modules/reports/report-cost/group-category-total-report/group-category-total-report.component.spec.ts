import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupCategoryTotalReportComponent } from './group-category-total-report.component';

describe('GroupCategoryTotalReportComponent', () => {
  let component: GroupCategoryTotalReportComponent;
  let fixture: ComponentFixture<GroupCategoryTotalReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupCategoryTotalReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupCategoryTotalReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
