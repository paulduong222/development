import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourcesTimeadjComponent } from './resources-timeadj.component';

describe('ResourcesTimeadjComponent', () => {
  let component: ResourcesTimeadjComponent;
  let fixture: ComponentFixture<ResourcesTimeadjComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourcesTimeadjComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourcesTimeadjComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
