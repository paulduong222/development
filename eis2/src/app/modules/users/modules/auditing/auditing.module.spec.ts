import { AuditingModule } from './auditing.module';

describe('AuditingModule', () => {
  let auditingModule: AuditingModule;

  beforeEach(() => {
    auditingModule = new AuditingModule();
  });

  it('should create an instance', () => {
    expect(auditingModule).toBeTruthy();
  });
});
