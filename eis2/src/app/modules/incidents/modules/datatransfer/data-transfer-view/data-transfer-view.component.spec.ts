import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DataTransferViewComponent } from './data-transfer-view.component';

describe('DataTransferViewComponent', () => {
  let component: DataTransferViewComponent;
  let fixture: ComponentFixture<DataTransferViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DataTransferViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DataTransferViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
