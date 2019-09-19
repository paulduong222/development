import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CopyPlanWindowComponent } from './copy-plan-window.component';

describe('CopyPlanWindowComponent', () => {
  let component: CopyPlanWindowComponent;
  let fixture: ComponentFixture<CopyPlanWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CopyPlanWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CopyPlanWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
