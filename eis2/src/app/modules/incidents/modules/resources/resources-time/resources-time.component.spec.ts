import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourcesTimeComponent } from './resources-time.component';

describe('ResourcesTimeComponent', () => {
  let component: ResourcesTimeComponent;
  let fixture: ComponentFixture<ResourcesTimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourcesTimeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourcesTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
