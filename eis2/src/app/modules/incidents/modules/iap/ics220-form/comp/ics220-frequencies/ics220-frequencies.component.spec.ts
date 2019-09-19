import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics220FrequenciesComponent } from './ics220-frequencies.component';

describe('Ics220FrequenciesComponent', () => {
  let component: Ics220FrequenciesComponent;
  let fixture: ComponentFixture<Ics220FrequenciesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics220FrequenciesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics220FrequenciesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
