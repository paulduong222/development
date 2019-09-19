import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics202SiteSafetyPlanComponent } from './ics202-site-safety-plan.component';

describe('Ics202SiteSafetyPlanComponent', () => {
  let component: Ics202SiteSafetyPlanComponent;
  let fixture: ComponentFixture<Ics202SiteSafetyPlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics202SiteSafetyPlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics202SiteSafetyPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
