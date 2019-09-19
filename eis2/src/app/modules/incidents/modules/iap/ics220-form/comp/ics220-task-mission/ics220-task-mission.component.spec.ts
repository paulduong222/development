import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics220TaskMissionComponent } from './ics220-task-mission.component';

describe('Ics220TaskMissionComponent', () => {
  let component: Ics220TaskMissionComponent;
  let fixture: ComponentFixture<Ics220TaskMissionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics220TaskMissionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics220TaskMissionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
