import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractorWindowComponent } from './contractor-window.component';

describe('ContractorWindowComponent', () => {
  let component: ContractorWindowComponent;
  let fixture: ComponentFixture<ContractorWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContractorWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContractorWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
