import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourcesCostComponent } from './resources-cost.component';

describe('ResourcesCostComponent', () => {
  let component: ResourcesCostComponent;
  let fixture: ComponentFixture<ResourcesCostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourcesCostComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourcesCostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
