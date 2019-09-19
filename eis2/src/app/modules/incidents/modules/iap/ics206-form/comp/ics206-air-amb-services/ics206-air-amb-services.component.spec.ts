import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics206AirAmbServicesComponent } from './ics206-air-amb-services.component';

describe('Ics206AirAmbServicesComponent', () => {
  let component: Ics206AirAmbServicesComponent;
  let fixture: ComponentFixture<Ics206AirAmbServicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics206AirAmbServicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics206AirAmbServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
