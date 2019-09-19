import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterFrequencyWindowComponent } from './master-frequency-window.component';

describe('MasterFrequencyWindowComponent', () => {
  let component: MasterFrequencyWindowComponent;
  let fixture: ComponentFixture<MasterFrequencyWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MasterFrequencyWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterFrequencyWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
