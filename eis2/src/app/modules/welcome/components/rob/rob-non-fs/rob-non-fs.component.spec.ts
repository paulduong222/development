import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RobNonFsComponent } from './rob-non-fs.component';

describe('RobNonFsComponent', () => {
  let component: RobNonFsComponent;
  let fixture: ComponentFixture<RobNonFsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RobNonFsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RobNonFsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
