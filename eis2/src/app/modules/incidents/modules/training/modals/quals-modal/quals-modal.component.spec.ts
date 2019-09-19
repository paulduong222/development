import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QualsModalComponent } from './quals-modal.component';

describe('QualsModalComponent', () => {
  let component: QualsModalComponent;
  let fixture: ComponentFixture<QualsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QualsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QualsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
