import { TestBed } from '@angular/core/testing';

import { DataAuditTrackingService } from './data-audit-tracking.service';

describe('DataAuditTrackingService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DataAuditTrackingService = TestBed.get(DataAuditTrackingService);
    expect(service).toBeTruthy();
  });
});
