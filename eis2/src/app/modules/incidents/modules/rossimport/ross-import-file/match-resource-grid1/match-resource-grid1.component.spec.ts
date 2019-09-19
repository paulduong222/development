import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchResourceGrid1Component } from './match-resource-grid1.component';

describe('MatchResourceGrid1Component', () => {
  let component: MatchResourceGrid1Component;
  let fixture: ComponentFixture<MatchResourceGrid1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MatchResourceGrid1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MatchResourceGrid1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
