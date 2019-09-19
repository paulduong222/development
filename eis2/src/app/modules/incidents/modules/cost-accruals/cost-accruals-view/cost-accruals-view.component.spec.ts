import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CostAccrualsViewComponent } from './cost-accruals-view.component';

describe('CostAccrualsViewComponent', () => {
  let component: CostAccrualsViewComponent;
  let fixture: ComponentFixture<CostAccrualsViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CostAccrualsViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CostAccrualsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
