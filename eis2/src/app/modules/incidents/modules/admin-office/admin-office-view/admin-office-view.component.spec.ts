import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOfficeViewComponent } from './admin-office-view.component';

describe('AdminOfficeViewComponent', () => {
  let component: AdminOfficeViewComponent;
  let fixture: ComponentFixture<AdminOfficeViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminOfficeViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminOfficeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
