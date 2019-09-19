import { TestBed } from '@angular/core/testing';

import { IapPlanService } from './iap-plan.service';

describe('IapPlanService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IapPlanService = TestBed.get(IapPlanService);
    expect(service).toBeTruthy();
  });
});
