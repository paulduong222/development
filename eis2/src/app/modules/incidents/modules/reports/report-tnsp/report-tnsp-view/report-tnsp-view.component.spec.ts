import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportTnspViewComponent } from './report-tnsp-view.component';

describe('ReportTnspViewComponent', () => {
  let component: ReportTnspViewComponent;
  let fixture: ComponentFixture<ReportTnspViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportTnspViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportTnspViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
