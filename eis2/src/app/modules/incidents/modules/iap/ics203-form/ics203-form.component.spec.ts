import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics203FormComponent } from './ics203-form.component';

describe('Ics203FormComponent', () => {
  let component: Ics203FormComponent;
  let fixture: ComponentFixture<Ics203FormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics203FormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics203FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
