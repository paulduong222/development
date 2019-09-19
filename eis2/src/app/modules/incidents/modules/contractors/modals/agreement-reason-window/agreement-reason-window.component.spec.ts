import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgreementReasonWindowComponent } from './agreement-reason-window.component';

describe('AgreementReasonWindowComponent', () => {
  let component: AgreementReasonWindowComponent;
  let fixture: ComponentFixture<AgreementReasonWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgreementReasonWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgreementReasonWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
