import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportsEvaluationModalComponent } from './reports-evaluation-modal.component';

describe('ReportsEvaluationModalComponent', () => {
  let component: ReportsEvaluationModalComponent;
  let fixture: ComponentFixture<ReportsEvaluationModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportsEvaluationModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportsEvaluationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
