import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeUnitContactComponent } from './home-unit-contact.component';

describe('HomeUnitContactComponent', () => {
  let component: HomeUnitContactComponent;
  let fixture: ComponentFixture<HomeUnitContactComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeUnitContactComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeUnitContactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
