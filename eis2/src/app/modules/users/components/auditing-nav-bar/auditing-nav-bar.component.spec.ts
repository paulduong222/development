import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditingNavBarComponent } from './auditing-nav-bar.component';

describe('AuditingNavBarComponent', () => {
  let component: AuditingNavBarComponent;
  let fixture: ComponentFixture<AuditingNavBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditingNavBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditingNavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
