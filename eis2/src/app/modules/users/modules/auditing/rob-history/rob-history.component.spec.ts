import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RobHistoryComponent } from './rob-history.component';

describe('RobHistoryComponent', () => {
  let component: RobHistoryComponent;
  let fixture: ComponentFixture<RobHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RobHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RobHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
