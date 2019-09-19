import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RossImportViewComponent } from './ross-import-view.component';

describe('RossImportViewComponent', () => {
  let component: RossImportViewComponent;
  let fixture: ComponentFixture<RossImportViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RossImportViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RossImportViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
