import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncViewComponent } from './inc-view.component';

describe('IncViewComponent', () => {
  let component: IncViewComponent;
  let fixture: ComponentFixture<IncViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
