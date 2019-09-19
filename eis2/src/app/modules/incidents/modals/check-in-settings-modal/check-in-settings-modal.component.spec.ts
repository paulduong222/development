import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckInSettingsModalComponent } from './check-in-settings-modal.component';

describe('CheckInSettingsModalComponent', () => {
  let component: CheckInSettingsModalComponent;
  let fixture: ComponentFixture<CheckInSettingsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckInSettingsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckInSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
