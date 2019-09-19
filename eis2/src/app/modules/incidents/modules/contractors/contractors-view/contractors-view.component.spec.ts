import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractorsViewComponent } from './contractors-view.component';

describe('ContractorsViewComponent', () => {
  let component: ContractorsViewComponent;
  let fixture: ComponentFixture<ContractorsViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContractorsViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContractorsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
