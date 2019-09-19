import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncViewIncidentGroupComponent } from './inc-view-incident-group.component';

describe('IncViewIncidentGroupComponent', () => {
  let component: IncViewIncidentGroupComponent;
  let fixture: ComponentFixture<IncViewIncidentGroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncViewIncidentGroupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncViewIncidentGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
