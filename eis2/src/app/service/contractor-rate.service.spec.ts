import { TestBed } from '@angular/core/testing';

import { ContractorRateService } from './contractor-rate.service';

describe('ContractorRateService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ContractorRateService = TestBed.get(ContractorRateService);
    expect(service).toBeTruthy();
  });
});
