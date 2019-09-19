import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IncGroupInfoTabComponent } from './inc-group-info-tab.component';

describe('IncGroupInfoTabComponent', () => {
  let component: IncGroupInfoTabComponent;
  let fixture: ComponentFixture<IncGroupInfoTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IncGroupInfoTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IncGroupInfoTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
