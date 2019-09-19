import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeUnitContactLabelsComponent } from './home-unit-contact-labels.component';

describe('HomeUnitContactLabelsComponent', () => {
  let component: HomeUnitContactLabelsComponent;
  let fixture: ComponentFixture<HomeUnitContactLabelsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeUnitContactLabelsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeUnitContactLabelsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
