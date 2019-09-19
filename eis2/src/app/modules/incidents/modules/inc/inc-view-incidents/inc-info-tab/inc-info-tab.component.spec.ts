import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncInfoTabComponent } from './inc-info-tab.component';

describe('IncInfoTabComponent', () => {
  let component: IncInfoTabComponent;
  let fixture: ComponentFixture<IncInfoTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncInfoTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncInfoTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
