import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportPlansViewComponent } from './report-plans-view.component';

describe('ReportPlansViewComponent', () => {
  let component: ReportPlansViewComponent;
  let fixture: ComponentFixture<ReportPlansViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportPlansViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportPlansViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
