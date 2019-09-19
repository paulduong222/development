import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RossImportFileComponent } from './ross-import-file.component';

describe('RossImportFileComponent', () => {
  let component: RossImportFileComponent;
  let fixture: ComponentFixture<RossImportFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RossImportFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RossImportFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
