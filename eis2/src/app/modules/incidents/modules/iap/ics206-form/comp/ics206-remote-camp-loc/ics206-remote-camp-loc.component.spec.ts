import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics206RemoteCampLocComponent } from './ics206-remote-camp-loc.component';

describe('Ics206RemoteCampLocComponent', () => {
  let component: Ics206RemoteCampLocComponent;
  let fixture: ComponentFixture<Ics206RemoteCampLocComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics206RemoteCampLocComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics206RemoteCampLocComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
