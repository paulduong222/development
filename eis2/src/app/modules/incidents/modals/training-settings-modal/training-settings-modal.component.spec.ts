import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingSettingsModalComponent } from './training-settings-modal.component';

describe('TrainingSettingsModalComponent', () => {
  let component: TrainingSettingsModalComponent;
  let fixture: ComponentFixture<TrainingSettingsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingSettingsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
