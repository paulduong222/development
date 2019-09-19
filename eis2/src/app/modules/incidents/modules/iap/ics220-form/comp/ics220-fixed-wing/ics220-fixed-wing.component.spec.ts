import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics220FixedWingComponent } from './ics220-fixed-wing.component';

describe('Ics220FixedWingComponent', () => {
  let component: Ics220FixedWingComponent;
  let fixture: ComponentFixture<Ics220FixedWingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics220FixedWingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics220FixedWingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
