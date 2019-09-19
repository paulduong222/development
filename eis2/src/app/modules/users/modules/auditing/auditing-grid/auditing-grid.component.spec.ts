import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditingGridComponent } from './auditing-grid.component';

describe('AuditingGridComponent', () => {
  let component: AuditingGridComponent;
  let fixture: ComponentFixture<AuditingGridComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditingGridComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditingGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
