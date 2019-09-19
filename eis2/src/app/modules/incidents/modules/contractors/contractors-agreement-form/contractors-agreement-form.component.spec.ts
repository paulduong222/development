import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractorsAgreementFormComponent } from './contractors-agreement-form.component';

describe('ContractorsAgreementFormComponent', () => {
  let component: ContractorsAgreementFormComponent;
  let fixture: ComponentFixture<ContractorsAgreementFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContractorsAgreementFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContractorsAgreementFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
