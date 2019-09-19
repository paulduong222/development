import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics202GenSitAwarenessComponent } from './ics202-gen-sit-awareness.component';

describe('Ics202GenSitAwarenessComponent', () => {
  let component: Ics202GenSitAwarenessComponent;
  let fixture: ComponentFixture<Ics202GenSitAwarenessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics202GenSitAwarenessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics202GenSitAwarenessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
