import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics203PlanningComponent } from './ics203-planning.component';

describe('Ics203PlanningComponent', () => {
  let component: Ics203PlanningComponent;
  let fixture: ComponentFixture<Ics203PlanningComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics203PlanningComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics203PlanningComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
