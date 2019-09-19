import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics205BasRadChannelUseComponent } from './ics205-bas-rad-channel-use.component';

describe('Ics205BasRadChannelUseComponent', () => {
  let component: Ics205BasRadChannelUseComponent;
  let fixture: ComponentFixture<Ics205BasRadChannelUseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics205BasRadChannelUseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics205BasRadChannelUseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
