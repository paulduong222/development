import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractorsFormComponent } from './contractors-form.component';

describe('ContractorsFormComponent', () => {
  let component: ContractorsFormComponent;
  let fixture: ComponentFixture<ContractorsFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContractorsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContractorsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
