import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RossImportGridComponent } from './ross-import-grid.component';

describe('RossImportGridComponent', () => {
  let component: RossImportGridComponent;
  let fixture: ComponentFixture<RossImportGridComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RossImportGridComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RossImportGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
