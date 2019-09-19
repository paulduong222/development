import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuickStatsWindowComponent } from './quick-stats-window.component';

describe('QuickStatsWindowComponent', () => {
  let component: QuickStatsWindowComponent;
  let fixture: ComponentFixture<QuickStatsWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuickStatsWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuickStatsWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
