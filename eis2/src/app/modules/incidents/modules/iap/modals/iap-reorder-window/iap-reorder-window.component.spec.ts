import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IapReorderWindowComponent } from './iap-reorder-window.component';

describe('IapReorderWindowComponent', () => {
  let component: IapReorderWindowComponent;
  let fixture: ComponentFixture<IapReorderWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IapReorderWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IapReorderWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
