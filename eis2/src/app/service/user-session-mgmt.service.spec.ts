import { TestBed } from '@angular/core/testing';

import { UserSessionMgmtService } from './user-session-mgmt.service';

describe('UserSessionMgmtService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserSessionMgmtService = TestBed.get(UserSessionMgmtService);
    expect(service).toBeTruthy();
  });
});
