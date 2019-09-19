import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditingFilterComponent } from './auditing-filter.component';

describe('AuditingFilterComponent', () => {
  let component: AuditingFilterComponent;
  let fixture: ComponentFixture<AuditingFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditingFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditingFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
