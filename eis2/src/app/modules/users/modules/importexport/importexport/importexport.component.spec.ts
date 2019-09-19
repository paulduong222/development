import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportexportComponent } from './importexport.component';

describe('ImportexportComponent', () => {
  let component: ImportexportComponent;
  let fixture: ComponentFixture<ImportexportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImportexportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImportexportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
