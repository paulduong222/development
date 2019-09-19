import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DbRestoreComponent } from './db-restore.component';

describe('DbRestoreComponent', () => {
  let component: DbRestoreComponent;
  let fixture: ComponentFixture<DbRestoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DbRestoreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DbRestoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
