import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics206PrepByRevByComponent } from './ics206-prep-by-rev-by.component';

describe('Ics206PrepByRevByComponent', () => {
  let component: Ics206PrepByRevByComponent;
  let fixture: ComponentFixture<Ics206PrepByRevByComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics206PrepByRevByComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics206PrepByRevByComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
