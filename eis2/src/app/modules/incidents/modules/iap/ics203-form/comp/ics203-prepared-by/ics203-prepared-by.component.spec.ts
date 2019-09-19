import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics203PreparedByComponent } from './ics203-prepared-by.component';

describe('Ics203PreparedByComponent', () => {
  let component: Ics203PreparedByComponent;
  let fixture: ComponentFixture<Ics203PreparedByComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics203PreparedByComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics203PreparedByComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
