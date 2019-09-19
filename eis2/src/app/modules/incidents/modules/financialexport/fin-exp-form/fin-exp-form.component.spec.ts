import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinExpFormComponent } from './fin-exp-form.component';

describe('FinExpFormComponent', () => {
  let component: FinExpFormComponent;
  let fixture: ComponentFixture<FinExpFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinExpFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinExpFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
