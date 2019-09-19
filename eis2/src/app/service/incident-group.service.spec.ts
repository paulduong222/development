import { TestBed } from '@angular/core/testing';

import { IncidentGroupService } from './incident-group.service';

describe('IncidentGroupService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IncidentGroupService = TestBed.get(IncidentGroupService);
    expect(service).toBeTruthy();
  });
});
