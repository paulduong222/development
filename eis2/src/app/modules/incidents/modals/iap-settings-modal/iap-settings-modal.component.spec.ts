import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IapSettingsModalComponent } from './iap-settings-modal.component';

describe('IapSettingsModalComponent', () => {
  let component: IapSettingsModalComponent;
  let fixture: ComponentFixture<IapSettingsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IapSettingsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IapSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
