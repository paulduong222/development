import { TestBed } from '@angular/core/testing';

import { IncidentResourceService } from './incident-resource.service';

describe('IncidentResourceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IncidentResourceService = TestBed.get(IncidentResourceService);
    expect(service).toBeTruthy();
  });
});
