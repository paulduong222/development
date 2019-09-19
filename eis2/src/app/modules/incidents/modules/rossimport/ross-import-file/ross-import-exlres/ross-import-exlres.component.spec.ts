import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RossImportExlresComponent } from './ross-import-exlres.component';

describe('RossImportExlresComponent', () => {
  let component: RossImportExlresComponent;
  let fixture: ComponentFixture<RossImportExlresComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RossImportExlresComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RossImportExlresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
