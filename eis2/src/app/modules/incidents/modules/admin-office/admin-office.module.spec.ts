import { AdminOfficeModule } from './admin-office.module';

describe('AdminOfficeModule', () => {
  let adminOfficeModule: AdminOfficeModule;

  beforeEach(() => {
    adminOfficeModule = new AdminOfficeModule();
  });

  it('should create an instance', () => {
    expect(adminOfficeModule).toBeTruthy();
  });
});
