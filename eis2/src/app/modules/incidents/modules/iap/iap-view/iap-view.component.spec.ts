import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IapViewComponent } from './iap-view.component';

describe('IapViewComponent', () => {
  let component: IapViewComponent;
  let fixture: ComponentFixture<IapViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IapViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IapViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
