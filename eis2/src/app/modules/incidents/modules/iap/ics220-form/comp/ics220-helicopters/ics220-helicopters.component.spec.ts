import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics220HelicoptersComponent } from './ics220-helicopters.component';

describe('Ics220HelicoptersComponent', () => {
  let component: Ics220HelicoptersComponent;
  let fixture: ComponentFixture<Ics220HelicoptersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics220HelicoptersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics220HelicoptersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
