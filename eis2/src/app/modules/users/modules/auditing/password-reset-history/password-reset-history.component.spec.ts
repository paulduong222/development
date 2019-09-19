import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordResetHistoryComponent } from './password-reset-history.component';

describe('PasswordResetHistoryComponent', () => {
  let component: PasswordResetHistoryComponent;
  let fixture: ComponentFixture<PasswordResetHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PasswordResetHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordResetHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
