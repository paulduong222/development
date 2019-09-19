import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TraineeTabComponent } from './trainee-tab.component';

describe('TraineeTabComponent', () => {
  let component: TraineeTabComponent;
  let fixture: ComponentFixture<TraineeTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TraineeTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TraineeTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
