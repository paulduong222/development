import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics206HospitalsComponent } from './ics206-hospitals.component';

describe('Ics206HospitalsComponent', () => {
  let component: Ics206HospitalsComponent;
  let fixture: ComponentFixture<Ics206HospitalsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics206HospitalsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics206HospitalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
