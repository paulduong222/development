import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics220PrepByComponent } from './ics220-prep-by.component';

describe('Ics220PrepByComponent', () => {
  let component: Ics220PrepByComponent;
  let fixture: ComponentFixture<Ics220PrepByComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics220PrepByComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics220PrepByComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
