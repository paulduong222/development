import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExitInterviewComponent } from './exit-interview.component';

describe('ExitInterviewComponent', () => {
  let component: ExitInterviewComponent;
  let fixture: ComponentFixture<ExitInterviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExitInterviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExitInterviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
