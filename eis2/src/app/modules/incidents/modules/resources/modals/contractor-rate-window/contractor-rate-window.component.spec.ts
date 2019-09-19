import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractorRateWindowComponent } from './contractor-rate-window.component';

describe('ContractorRateWindowComponent', () => {
  let component: ContractorRateWindowComponent;
  let fixture: ComponentFixture<ContractorRateWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContractorRateWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContractorRateWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
