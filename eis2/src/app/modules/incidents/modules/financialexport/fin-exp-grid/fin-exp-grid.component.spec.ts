import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinExpGridComponent } from './fin-exp-grid.component';

describe('FinExpGridComponent', () => {
  let component: FinExpGridComponent;
  let fixture: ComponentFixture<FinExpGridComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinExpGridComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinExpGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
