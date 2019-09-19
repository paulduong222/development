import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics220ReadyAlertComponent } from './ics220-ready-alert.component';

describe('Ics220ReadyAlertComponent', () => {
  let component: Ics220ReadyAlertComponent;
  let fixture: ComponentFixture<Ics220ReadyAlertComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics220ReadyAlertComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics220ReadyAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
