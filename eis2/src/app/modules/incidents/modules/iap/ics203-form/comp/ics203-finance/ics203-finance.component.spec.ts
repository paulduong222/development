import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics203FinanceComponent } from './ics203-finance.component';

describe('Ics203FinanceComponent', () => {
  let component: Ics203FinanceComponent;
  let fixture: ComponentFixture<Ics203FinanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics203FinanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics203FinanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
