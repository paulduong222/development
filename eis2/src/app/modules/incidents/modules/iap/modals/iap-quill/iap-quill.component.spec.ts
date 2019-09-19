import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IapQuillComponent } from './iap-quill.component';

describe('IapQuillComponent', () => {
  let component: IapQuillComponent;
  let fixture: ComponentFixture<IapQuillComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IapQuillComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IapQuillComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
