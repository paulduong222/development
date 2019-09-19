import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics206AmbServicesComponent } from './ics206-amb-services.component';

describe('Ics206AmbServicesComponent', () => {
  let component: Ics206AmbServicesComponent;
  let fixture: ComponentFixture<Ics206AmbServicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics206AmbServicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics206AmbServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
