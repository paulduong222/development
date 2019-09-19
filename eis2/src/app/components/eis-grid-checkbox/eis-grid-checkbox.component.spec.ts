import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EisGridCheckboxComponent } from './eis-grid-checkbox.component';

describe('EisGridCheckboxComponent', () => {
  let component: EisGridCheckboxComponent;
  let fixture: ComponentFixture<EisGridCheckboxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EisGridCheckboxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EisGridCheckboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
