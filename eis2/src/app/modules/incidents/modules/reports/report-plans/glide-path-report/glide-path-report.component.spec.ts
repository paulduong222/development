import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlidePathReportComponent } from './glide-path-report.component';

describe('GlidePathReportComponent', () => {
  let component: GlidePathReportComponent;
  let fixture: ComponentFixture<GlidePathReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlidePathReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlidePathReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
