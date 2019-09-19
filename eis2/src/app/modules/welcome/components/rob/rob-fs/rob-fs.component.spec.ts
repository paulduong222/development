import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RobFsComponent } from './rob-fs.component';

describe('RobFsComponent', () => {
  let component: RobFsComponent;
  let fixture: ComponentFixture<RobFsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RobFsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RobFsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
