import { TestBed } from '@angular/core/testing';

import { DbMgmtService } from './db-mgmt.service';

describe('DbMgmtService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DbMgmtService = TestBed.get(DbMgmtService);
    expect(service).toBeTruthy();
  });
});
