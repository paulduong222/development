import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EisGridComponent } from './eis-grid.component';

describe('EisGridComponent', () => {
  let component: EisGridComponent;
  let fixture: ComponentFixture<EisGridComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EisGridComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EisGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
