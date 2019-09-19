import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitRefDataTabComponent } from './unit-ref-data-tab.component';

describe('UnitRefDataTabComponent', () => {
  let component: UnitRefDataTabComponent;
  let fixture: ComponentFixture<UnitRefDataTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnitRefDataTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnitRefDataTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
