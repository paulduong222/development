import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOfficeFormComponent } from './admin-office-form.component';

describe('AdminOfficeFormComponent', () => {
  let component: AdminOfficeFormComponent;
  let fixture: ComponentFixture<AdminOfficeFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminOfficeFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminOfficeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
