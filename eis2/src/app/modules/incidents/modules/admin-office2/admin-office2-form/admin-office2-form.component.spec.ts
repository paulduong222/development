import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOffice2FormComponent } from './admin-office2-form.component';

describe('AdminOffice2FormComponent', () => {
  let component: AdminOffice2FormComponent;
  let fixture: ComponentFixture<AdminOffice2FormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminOffice2FormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminOffice2FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
