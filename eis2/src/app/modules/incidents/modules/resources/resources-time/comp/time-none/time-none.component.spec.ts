import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeNoneComponent } from './time-none.component';

describe('TimeNoneComponent', () => {
  let component: TimeNoneComponent;
  let fixture: ComponentFixture<TimeNoneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeNoneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeNoneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
