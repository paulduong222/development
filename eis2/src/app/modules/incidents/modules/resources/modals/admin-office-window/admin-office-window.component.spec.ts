import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOfficeWindowComponent } from './admin-office-window.component';

describe('AdminOfficeWindowComponent', () => {
  let component: AdminOfficeWindowComponent;
  let fixture: ComponentFixture<AdminOfficeWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminOfficeWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminOfficeWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
