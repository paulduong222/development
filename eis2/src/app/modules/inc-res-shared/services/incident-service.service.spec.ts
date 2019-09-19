import { TestBed, inject } from '@angular/core/testing';

import { IncidentServiceService } from './incident-service.service';

describe('IncidentServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IncidentServiceService]
    });
  });

  it('should be created', inject([IncidentServiceService], (service: IncidentServiceService) => {
    expect(service).toBeTruthy();
  }));
});
