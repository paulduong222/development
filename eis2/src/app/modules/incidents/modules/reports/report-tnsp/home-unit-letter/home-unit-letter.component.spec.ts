import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeUnitLetterComponent } from './home-unit-letter.component';

describe('HomeUnitLetterComponent', () => {
  let component: HomeUnitLetterComponent;
  let fixture: ComponentFixture<HomeUnitLetterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeUnitLetterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeUnitLetterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
