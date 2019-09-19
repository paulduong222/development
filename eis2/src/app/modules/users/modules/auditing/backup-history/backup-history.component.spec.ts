import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BackupHistoryComponent } from './backup-history.component';

describe('BackupHistoryComponent', () => {
  let component: BackupHistoryComponent;
  let fixture: ComponentFixture<BackupHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BackupHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BackupHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
