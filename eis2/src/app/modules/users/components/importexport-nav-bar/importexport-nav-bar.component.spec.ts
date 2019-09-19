import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportexportNavBarComponent } from './importexport-nav-bar.component';

describe('ImportexportNavBarComponent', () => {
  let component: ImportexportNavBarComponent;
  let fixture: ComponentFixture<ImportexportNavBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImportexportNavBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImportexportNavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
