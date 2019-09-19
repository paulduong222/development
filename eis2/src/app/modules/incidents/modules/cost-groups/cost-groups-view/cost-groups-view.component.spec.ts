import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CostGroupsViewComponent } from './cost-groups-view.component';

describe('CostGroupsViewComponent', () => {
  let component: CostGroupsViewComponent;
  let fixture: ComponentFixture<CostGroupsViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CostGroupsViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CostGroupsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
