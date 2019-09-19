import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAccountsHistoryComponent } from './user-accounts-history.component';

describe('UserAccountsHistoryComponent', () => {
  let component: UserAccountsHistoryComponent;
  let fixture: ComponentFixture<UserAccountsHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserAccountsHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserAccountsHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
