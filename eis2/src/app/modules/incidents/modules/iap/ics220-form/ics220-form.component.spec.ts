import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics220FormComponent } from './ics220-form.component';

describe('Ics220FormComponent', () => {
  let component: Ics220FormComponent;
  let fixture: ComponentFixture<Ics220FormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics220FormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics220FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
