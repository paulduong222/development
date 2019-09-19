import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RossImportStartComponent } from './ross-import-start.component';

describe('RossImportStartComponent', () => {
  let component: RossImportStartComponent;
  let fixture: ComponentFixture<RossImportStartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RossImportStartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RossImportStartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
