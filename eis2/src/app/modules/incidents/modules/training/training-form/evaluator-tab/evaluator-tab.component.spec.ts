import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluatorTabComponent } from './evaluator-tab.component';

describe('EvaluatorTabComponent', () => {
  let component: EvaluatorTabComponent;
  let fixture: ComponentFixture<EvaluatorTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EvaluatorTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EvaluatorTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
