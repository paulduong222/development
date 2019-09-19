import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchResourceGrid2Component } from './match-resource-grid2.component';

describe('MatchResourceGrid2Component', () => {
  let component: MatchResourceGrid2Component;
  let fixture: ComponentFixture<MatchResourceGrid2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MatchResourceGrid2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MatchResourceGrid2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
