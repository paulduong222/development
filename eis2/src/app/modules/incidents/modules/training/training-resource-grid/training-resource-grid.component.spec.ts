import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingResourceGridComponent } from './training-resource-grid.component';

describe('TrainingResourceGridComponent', () => {
  let component: TrainingResourceGridComponent;
  let fixture: ComponentFixture<TrainingResourceGridComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingResourceGridComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingResourceGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
