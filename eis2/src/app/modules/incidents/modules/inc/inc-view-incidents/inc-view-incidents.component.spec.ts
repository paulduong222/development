import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncViewIncidentsComponent } from './inc-view-incidents.component';

describe('IncViewIncidentsComponent', () => {
  let component: IncViewIncidentsComponent;
  let fixture: ComponentFixture<IncViewIncidentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncViewIncidentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncViewIncidentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
