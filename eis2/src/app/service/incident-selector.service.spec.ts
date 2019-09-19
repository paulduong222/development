import { TestBed } from '@angular/core/testing';

import { IncidentSelectorService } from './incident-selector.service';

describe('IncidentSelectorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IncidentSelectorService = TestBed.get(IncidentSelectorService);
    expect(service).toBeTruthy();
  });
});
