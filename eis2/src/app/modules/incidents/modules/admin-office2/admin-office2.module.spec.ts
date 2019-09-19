import { AdminOffice2Module } from './admin-office2.module';

describe('AdminOffice2Module', () => {
  let adminOffice2Module: AdminOffice2Module;

  beforeEach(() => {
    adminOffice2Module = new AdminOffice2Module();
  });

  it('should create an instance', () => {
    expect(adminOffice2Module).toBeTruthy();
  });
});
