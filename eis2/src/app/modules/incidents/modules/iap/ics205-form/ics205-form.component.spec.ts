import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics205FormComponent } from './ics205-form.component';

describe('Ics205FormComponent', () => {
  let component: Ics205FormComponent;
  let fixture: ComponentFixture<Ics205FormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics205FormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics205FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
