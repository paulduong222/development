import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncRefDataTabComponent } from './inc-ref-data-tab.component';

describe('IncRefDataTabComponent', () => {
  let component: IncRefDataTabComponent;
  let fixture: ComponentFixture<IncRefDataTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncRefDataTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncRefDataTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
