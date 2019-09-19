import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllResourcesReportComponent } from './all-resources-report.component';

describe('AllResourcesReportComponent', () => {
  let component: AllResourcesReportComponent;
  let fixture: ComponentFixture<AllResourcesReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllResourcesReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllResourcesReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
