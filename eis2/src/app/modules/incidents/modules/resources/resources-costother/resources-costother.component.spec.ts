import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourcesCostotherComponent } from './resources-costother.component';

describe('ResourcesCostotherComponent', () => {
  let component: ResourcesCostotherComponent;
  let fixture: ComponentFixture<ResourcesCostotherComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourcesCostotherComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourcesCostotherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
