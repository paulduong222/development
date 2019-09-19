import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncAcctCodeTabComponent } from './inc-acct-code-tab.component';

describe('IncAcctCodeTabComponent', () => {
  let component: IncAcctCodeTabComponent;
  let fixture: ComponentFixture<IncAcctCodeTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncAcctCodeTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncAcctCodeTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
