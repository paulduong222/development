import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics220PersonnelComponent } from './ics220-personnel.component';

describe('Ics220PersonnelComponent', () => {
  let component: Ics220PersonnelComponent;
  let fixture: ComponentFixture<Ics220PersonnelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics220PersonnelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics220PersonnelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
