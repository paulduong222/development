import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DbAddEditComponent } from './db-add-edit.component';

describe('DbAddEditComponent', () => {
  let component: DbAddEditComponent;
  let fixture: ComponentFixture<DbAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DbAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DbAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
