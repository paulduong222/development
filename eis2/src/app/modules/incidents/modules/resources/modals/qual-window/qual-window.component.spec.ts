import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QualWindowComponent } from './qual-window.component';

describe('QualWindowComponent', () => {
  let component: QualWindowComponent;
  let fixture: ComponentFixture<QualWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QualWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QualWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
