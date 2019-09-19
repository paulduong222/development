import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluatorModalComponent } from './evaluator-modal.component';

describe('EvaluatorModalComponent', () => {
  let component: EvaluatorModalComponent;
  let fixture: ComponentFixture<EvaluatorModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EvaluatorModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EvaluatorModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
