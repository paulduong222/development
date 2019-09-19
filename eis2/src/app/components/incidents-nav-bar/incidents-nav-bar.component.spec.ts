import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncidentsNavBarComponent } from './incidents-nav-bar.component';

describe('IncidentsNavBarComponent', () => {
  let component: IncidentsNavBarComponent;
  let fixture: ComponentFixture<IncidentsNavBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncidentsNavBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncidentsNavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
