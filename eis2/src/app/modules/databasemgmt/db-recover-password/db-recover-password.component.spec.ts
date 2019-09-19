import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DbRecoverPasswordComponent } from './db-recover-password.component';

describe('DbRecoverPasswordComponent', () => {
  let component: DbRecoverPasswordComponent;
  let fixture: ComponentFixture<DbRecoverPasswordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DbRecoverPasswordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DbRecoverPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
