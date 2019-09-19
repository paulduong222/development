import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GridIconBarComponent } from './grid-icon-bar.component';

describe('GridIconBarComponent', () => {
  let component: GridIconBarComponent;
  let fixture: ComponentFixture<GridIconBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GridIconBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GridIconBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
