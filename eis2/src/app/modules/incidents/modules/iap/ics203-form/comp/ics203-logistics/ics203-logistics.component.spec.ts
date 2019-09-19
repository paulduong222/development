import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics203LogisticsComponent } from './ics203-logistics.component';

describe('Ics203LogisticsComponent', () => {
  let component: Ics203LogisticsComponent;
  let fixture: ComponentFixture<Ics203LogisticsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics203LogisticsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics203LogisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
