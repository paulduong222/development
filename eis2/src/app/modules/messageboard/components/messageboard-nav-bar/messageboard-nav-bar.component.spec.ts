import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageboardNavBarComponent } from './messageboard-nav-bar.component';

describe('MessageboardNavBarComponent', () => {
  let component: MessageboardNavBarComponent;
  let fixture: ComponentFixture<MessageboardNavBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MessageboardNavBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MessageboardNavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
