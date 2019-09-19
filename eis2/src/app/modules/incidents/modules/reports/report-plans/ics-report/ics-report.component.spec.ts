import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IcsReportComponent } from './ics-report.component';

describe('IcsReportComponent', () => {
  let component: IcsReportComponent;
  let fixture: ComponentFixture<IcsReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IcsReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IcsReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
