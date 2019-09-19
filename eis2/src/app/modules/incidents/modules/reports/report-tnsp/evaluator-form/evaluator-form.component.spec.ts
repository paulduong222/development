import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluatorFormComponent } from './evaluator-form.component';

describe('EvaluatorFormComponent', () => {
  let component: EvaluatorFormComponent;
  let fixture: ComponentFixture<EvaluatorFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EvaluatorFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EvaluatorFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
