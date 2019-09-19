import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics205PrepByComponent } from './ics205-prep-by.component';

describe('Ics205PrepByComponent', () => {
  let component: Ics205PrepByComponent;
  let fixture: ComponentFixture<Ics205PrepByComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics205PrepByComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics205PrepByComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
