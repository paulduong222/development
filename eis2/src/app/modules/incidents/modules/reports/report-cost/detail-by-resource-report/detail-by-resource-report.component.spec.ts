import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailByResourceReportComponent } from './detail-by-resource-report.component';

describe('DetailByResourceReportComponent', () => {
  let component: DetailByResourceReportComponent;
  let fixture: ComponentFixture<DetailByResourceReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailByResourceReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailByResourceReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
