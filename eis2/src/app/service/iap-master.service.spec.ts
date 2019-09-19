import { TestBed } from '@angular/core/testing';

import { IapMasterService } from './iap-master.service';

describe('IapMasterService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IapMasterService = TestBed.get(IapMasterService);
    expect(service).toBeTruthy();
  });
});
