import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeOverheadComponent } from './time-overhead.component';

describe('TimeOverheadComponent', () => {
  let component: TimeOverheadComponent;
  let fixture: ComponentFixture<TimeOverheadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeOverheadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeOverheadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
