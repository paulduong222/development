import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EisGrid2Component } from './eis-grid2.component';

describe('EisGrid2Component', () => {
  let component: EisGrid2Component;
  let fixture: ComponentFixture<EisGrid2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EisGrid2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EisGrid2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
