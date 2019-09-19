import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics205SpecInstrComponent } from './ics205-spec-instr.component';

describe('Ics205SpecInstrComponent', () => {
  let component: Ics205SpecInstrComponent;
  let fixture: ComponentFixture<Ics205SpecInstrComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics205SpecInstrComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics205SpecInstrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
