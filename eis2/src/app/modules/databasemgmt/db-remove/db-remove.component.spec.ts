import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DbRemoveComponent } from './db-remove.component';

describe('DbRemoveComponent', () => {
  let component: DbRemoveComponent;
  let fixture: ComponentFixture<DbRemoveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DbRemoveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DbRemoveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
