import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgreementWindowComponent } from './agreement-window.component';

describe('AgreementWindowComponent', () => {
  let component: AgreementWindowComponent;
  let fixture: ComponentFixture<AgreementWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgreementWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgreementWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
