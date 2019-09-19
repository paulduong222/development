import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemCodeRefDataTabComponent } from './item-code-ref-data-tab.component';

describe('ItemCodeRefDataTabComponent', () => {
  let component: ItemCodeRefDataTabComponent;
  let fixture: ComponentFixture<ItemCodeRefDataTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItemCodeRefDataTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemCodeRefDataTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
