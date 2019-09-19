import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics206AreaLocCapComponent } from './ics206-area-loc-cap.component';

describe('Ics206AreaLocCapComponent', () => {
  let component: Ics206AreaLocCapComponent;
  let fixture: ComponentFixture<Ics206AreaLocCapComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics206AreaLocCapComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics206AreaLocCapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
