import { TestBed } from '@angular/core/testing';

import { CostAccrualsService } from './cost-accruals.service';

describe('CostAccrualsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CostAccrualsService = TestBed.get(CostAccrualsService);
    expect(service).toBeTruthy();
  });
});
