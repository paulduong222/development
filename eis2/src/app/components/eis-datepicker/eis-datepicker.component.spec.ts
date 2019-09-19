import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EisDatepickerComponent } from './eis-datepicker.component';

describe('EisDatepickerComponent', () => {
  let component: EisDatepickerComponent;
  let fixture: ComponentFixture<EisDatepickerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EisDatepickerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EisDatepickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
