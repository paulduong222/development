import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgencyRefDataTabComponent } from './agency-ref-data-tab.component';

describe('AgencyRefDataTabComponent', () => {
  let component: AgencyRefDataTabComponent;
  let fixture: ComponentFixture<AgencyRefDataTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgencyRefDataTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgencyRefDataTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
