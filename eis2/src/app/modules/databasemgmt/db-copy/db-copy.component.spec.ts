import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DbCopyComponent } from './db-copy.component';

describe('DbCopyComponent', () => {
  let component: DbCopyComponent;
  let fixture: ComponentFixture<DbCopyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DbCopyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DbCopyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
