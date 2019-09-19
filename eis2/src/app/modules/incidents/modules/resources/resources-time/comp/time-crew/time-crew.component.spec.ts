import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeCrewComponent } from './time-crew.component';

describe('TimeCrewComponent', () => {
  let component: TimeCrewComponent;
  let fixture: ComponentFixture<TimeCrewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeCrewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeCrewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
