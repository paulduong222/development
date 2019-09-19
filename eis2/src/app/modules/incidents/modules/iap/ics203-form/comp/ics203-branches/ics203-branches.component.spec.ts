import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics203BranchesComponent } from './ics203-branches.component';

describe('Ics203BranchesComponent', () => {
  let component: Ics203BranchesComponent;
  let fixture: ComponentFixture<Ics203BranchesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics203BranchesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics203BranchesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
