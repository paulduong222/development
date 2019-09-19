import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics202ObjectivesComponent } from './ics202-objectives.component';

describe('Ics202ObjectivesComponent', () => {
  let component: Ics202ObjectivesComponent;
  let fixture: ComponentFixture<Ics202ObjectivesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics202ObjectivesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics202ObjectivesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
