import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CostSettingsModalComponent } from './cost-settings-modal.component';

describe('CostSettingsModalComponent', () => {
  let component: CostSettingsModalComponent;
  let fixture: ComponentFixture<CostSettingsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CostSettingsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CostSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
