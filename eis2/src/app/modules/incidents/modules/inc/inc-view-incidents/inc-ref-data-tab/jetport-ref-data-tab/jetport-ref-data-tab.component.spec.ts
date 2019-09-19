import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JetportRefDataTabComponent } from './jetport-ref-data-tab.component';

describe('JetportRefDataTabComponent', () => {
  let component: JetportRefDataTabComponent;
  let fixture: ComponentFixture<JetportRefDataTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JetportRefDataTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JetportRefDataTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
