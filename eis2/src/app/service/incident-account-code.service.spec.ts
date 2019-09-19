import { TestBed } from '@angular/core/testing';

import { IncidentAccountCodeService } from './incident-account-code.service';

describe('IncidentAccountCodeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IncidentAccountCodeService = TestBed.get(IncidentAccountCodeService);
    expect(service).toBeTruthy();
  });
});
