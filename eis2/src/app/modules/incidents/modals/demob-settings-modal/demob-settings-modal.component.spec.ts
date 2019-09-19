import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DemobSettingsModalComponent } from './demob-settings-modal.component';

describe('DemobSettingsModalComponent', () => {
  let component: DemobSettingsModalComponent;
  let fixture: ComponentFixture<DemobSettingsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DemobSettingsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DemobSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
