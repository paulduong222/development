import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeContractorComponent } from './time-contractor.component';

describe('TimeContractorComponent', () => {
  let component: TimeContractorComponent;
  let fixture: ComponentFixture<TimeContractorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeContractorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeContractorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
