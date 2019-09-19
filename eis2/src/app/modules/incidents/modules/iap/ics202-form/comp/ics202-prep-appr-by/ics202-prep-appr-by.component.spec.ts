import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics202PrepApprByComponent } from './ics202-prep-appr-by.component';

describe('Ics202PrepApprByComponent', () => {
  let component: Ics202PrepApprByComponent;
  let fixture: ComponentFixture<Ics202PrepApprByComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics202PrepApprByComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics202PrepApprByComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
