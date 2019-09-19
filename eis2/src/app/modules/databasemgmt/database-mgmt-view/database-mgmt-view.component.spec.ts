import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DatabaseMgmtViewComponent } from './database-mgmt-view.component';

describe('DatabaseMgmtViewComponent', () => {
  let component: DatabaseMgmtViewComponent;
  let fixture: ComponentFixture<DatabaseMgmtViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DatabaseMgmtViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DatabaseMgmtViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
