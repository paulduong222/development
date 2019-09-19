import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Ics202OpPerCmdEmphasisComponent } from './ics202-op-per-cmd-emphasis.component';

describe('Ics202OpPerCmdEmphasisComponent', () => {
  let component: Ics202OpPerCmdEmphasisComponent;
  let fixture: ComponentFixture<Ics202OpPerCmdEmphasisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Ics202OpPerCmdEmphasisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Ics202OpPerCmdEmphasisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
