import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditPlanWindowComponent } from './add-edit-plan-window.component';

describe('AddEditPlanWindowComponent', () => {
  let component: AddEditPlanWindowComponent;
  let fixture: ComponentFixture<AddEditPlanWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditPlanWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditPlanWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
