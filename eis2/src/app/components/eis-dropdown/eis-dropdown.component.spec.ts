import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EisDropdownComponent } from './eis-dropdown.component';

describe('EisDropdownComponent', () => {
  let component: EisDropdownComponent;
  let fixture: ComponentFixture<EisDropdownComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EisDropdownComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EisDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
