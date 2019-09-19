import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics202IncActionPlanComponent } from './ics202-inc-action-plan.component';

describe('Ics202IncActionPlanComponent', () => {
  let component: Ics202IncActionPlanComponent;
  let fixture: ComponentFixture<Ics202IncActionPlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics202IncActionPlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics202IncActionPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
