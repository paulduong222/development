import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RestOvernightWindowComponent } from './rest-overnight-window.component';

describe('RestOvernightWindowComponent', () => {
  let component: RestOvernightWindowComponent;
  let fixture: ComponentFixture<RestOvernightWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RestOvernightWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RestOvernightWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
