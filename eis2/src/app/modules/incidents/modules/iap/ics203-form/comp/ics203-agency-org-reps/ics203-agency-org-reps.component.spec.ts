import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics203AgencyOrgRepsComponent } from './ics203-agency-org-reps.component';

describe('Ics203AgencyOrgRepsComponent', () => {
  let component: Ics203AgencyOrgRepsComponent;
  let fixture: ComponentFixture<Ics203AgencyOrgRepsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics203AgencyOrgRepsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics203AgencyOrgRepsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
