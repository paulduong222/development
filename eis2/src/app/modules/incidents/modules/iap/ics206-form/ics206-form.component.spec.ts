import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics206FormComponent } from './ics206-form.component';

describe('Ics206FormComponent', () => {
  let component: Ics206FormComponent;
  let fixture: ComponentFixture<Ics206FormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics206FormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics206FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
