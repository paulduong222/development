import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics203AirOpBranchComponent } from './ics203-air-op-branch.component';

describe('Ics203AirOpBranchComponent', () => {
  let component: Ics203AirOpBranchComponent;
  let fixture: ComponentFixture<Ics203AirOpBranchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics203AirOpBranchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics203AirOpBranchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
