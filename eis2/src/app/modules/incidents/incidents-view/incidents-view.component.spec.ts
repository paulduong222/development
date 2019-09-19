import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncidentsViewComponent } from './incidents-view.component';

describe('IncidentsViewComponent', () => {
  let component: IncidentsViewComponent;
  let fixture: ComponentFixture<IncidentsViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncidentsViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncidentsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
