import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportTimeViewComponent } from './report-time-view.component';

describe('ReportTimeViewComponent', () => {
  let component: ReportTimeViewComponent;
  let fixture: ComponentFixture<ReportTimeViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportTimeViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportTimeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
