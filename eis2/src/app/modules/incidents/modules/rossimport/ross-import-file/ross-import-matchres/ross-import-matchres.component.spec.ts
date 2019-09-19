import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RossImportMatchresComponent } from './ross-import-matchres.component';

describe('RossImportMatchresComponent', () => {
  let component: RossImportMatchresComponent;
  let fixture: ComponentFixture<RossImportMatchresComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RossImportMatchresComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RossImportMatchresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
