import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignmentCloseoutModalComponent } from './assignment-closeout-modal.component';

describe('AssignmentCloseoutModalComponent', () => {
  let component: AssignmentCloseoutModalComponent;
  let fixture: ComponentFixture<AssignmentCloseoutModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignmentCloseoutModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentCloseoutModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
