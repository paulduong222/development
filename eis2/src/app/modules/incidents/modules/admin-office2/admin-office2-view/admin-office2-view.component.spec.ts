import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOffice2ViewComponent } from './admin-office2-view.component';

describe('AdminOffice2ViewComponent', () => {
  let component: AdminOffice2ViewComponent;
  let fixture: ComponentFixture<AdminOffice2ViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminOffice2ViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminOffice2ViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
