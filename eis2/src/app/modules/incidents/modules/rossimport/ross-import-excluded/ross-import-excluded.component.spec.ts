import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RossImportExcludedComponent } from './ross-import-excluded.component';

describe('RossImportExcludedComponent', () => {
  let component: RossImportExcludedComponent;
  let fixture: ComponentFixture<RossImportExcludedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RossImportExcludedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RossImportExcludedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
