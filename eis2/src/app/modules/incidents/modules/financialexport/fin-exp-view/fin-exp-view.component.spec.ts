import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinExpViewComponent } from './fin-exp-view.component';

describe('FinExpViewComponent', () => {
  let component: FinExpViewComponent;
  let fixture: ComponentFixture<FinExpViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinExpViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinExpViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
