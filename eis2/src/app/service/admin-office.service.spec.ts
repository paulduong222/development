import { TestBed } from '@angular/core/testing';

import { AdminOfficeService } from './admin-office.service';

describe('AdminOfficeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AdminOfficeService = TestBed.get(AdminOfficeService);
    expect(service).toBeTruthy();
  });
});
