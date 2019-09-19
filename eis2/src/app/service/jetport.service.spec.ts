import { TestBed } from '@angular/core/testing';

import { JetportService } from './jetport.service';

describe('JetportService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: JetportService = TestBed.get(JetportService);
    expect(service).toBeTruthy();
  });
});
