import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics202FormComponent } from './ics202-form.component';

describe('Ics202FormComponent', () => {
  let component: Ics202FormComponent;
  let fixture: ComponentFixture<Ics202FormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics202FormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics202FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
