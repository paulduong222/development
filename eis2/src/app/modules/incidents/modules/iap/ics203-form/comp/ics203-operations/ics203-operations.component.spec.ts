import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics203OperationsComponent } from './ics203-operations.component';

describe('Ics203OperationsComponent', () => {
  let component: Ics203OperationsComponent;
  let fixture: ComponentFixture<Ics203OperationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics203OperationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics203OperationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
