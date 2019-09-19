import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics204FormComponent } from './ics204-form.component';

describe('Ics204FormComponent', () => {
  let component: Ics204FormComponent;
  let fixture: ComponentFixture<Ics204FormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics204FormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics204FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
