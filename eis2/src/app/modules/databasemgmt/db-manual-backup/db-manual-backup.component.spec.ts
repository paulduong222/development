import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DbManualBackupComponent } from './db-manual-backup.component';

describe('DbManualBackupComponent', () => {
  let component: DbManualBackupComponent;
  let fixture: ComponentFixture<DbManualBackupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DbManualBackupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DbManualBackupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
