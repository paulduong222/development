import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CopyFormWindowComponent } from './copy-form-window.component';

describe('CopyFormWindowComponent', () => {
  let component: CopyFormWindowComponent;
  let fixture: ComponentFixture<CopyFormWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CopyFormWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CopyFormWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
