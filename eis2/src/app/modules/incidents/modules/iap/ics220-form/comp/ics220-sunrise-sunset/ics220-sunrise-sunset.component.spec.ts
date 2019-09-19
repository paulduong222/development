import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics220SunriseSunsetComponent } from './ics220-sunrise-sunset.component';

describe('Ics220SunriseSunsetComponent', () => {
  let component: Ics220SunriseSunsetComponent;
  let fixture: ComponentFixture<Ics220SunriseSunsetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics220SunriseSunsetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics220SunriseSunsetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
