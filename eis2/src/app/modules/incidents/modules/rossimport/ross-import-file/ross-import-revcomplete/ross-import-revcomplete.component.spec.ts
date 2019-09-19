import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RossImportRevcompleteComponent } from './ross-import-revcomplete.component';

describe('RossImportRevcompleteComponent', () => {
  let component: RossImportRevcompleteComponent;
  let fixture: ComponentFixture<RossImportRevcompleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RossImportRevcompleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RossImportRevcompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
