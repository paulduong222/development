import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAttachWindowComponent } from './add-attach-window.component';

describe('AddAttachWindowComponent', () => {
  let component: AddAttachWindowComponent;
  let fixture: ComponentFixture<AddAttachWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddAttachWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAttachWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
