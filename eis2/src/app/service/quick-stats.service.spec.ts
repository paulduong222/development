import { TestBed } from '@angular/core/testing';

import { QuickStatsService } from './quick-stats.service';

describe('QuickStatsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: QuickStatsService = TestBed.get(QuickStatsService);
    expect(service).toBeTruthy();
  });
});
