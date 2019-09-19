import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics203CommandersComponent } from './ics203-commanders.component';

describe('Ics203CommandersComponent', () => {
  let component: Ics203CommandersComponent;
  let fixture: ComponentFixture<Ics203CommandersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics203CommandersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics203CommandersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
