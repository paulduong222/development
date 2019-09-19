import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageBoardViewComponent } from './message-board-view.component';

describe('MessageBoardViewComponent', () => {
  let component: MessageBoardViewComponent;
  let fixture: ComponentFixture<MessageBoardViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MessageBoardViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MessageBoardViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
