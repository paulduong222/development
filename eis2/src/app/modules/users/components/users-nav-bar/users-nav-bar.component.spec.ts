import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersNavBarComponent } from './users-nav-bar.component';

describe('UsersNavBarComponent', () => {
  let component: UsersNavBarComponent;
  let fixture: ComponentFixture<UsersNavBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsersNavBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersNavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
