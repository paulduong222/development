import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RobAdminComponent } from './rob-admin.component';

describe('RobAdminComponent', () => {
  let component: RobAdminComponent;
  let fixture: ComponentFixture<RobAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RobAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RobAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
