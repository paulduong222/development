import { DatabasemgmtModule } from './databasemgmt.module';

describe('DatabasemgmtModule', () => {
  let databasemgmtModule: DatabasemgmtModule;

  beforeEach(() => {
    databasemgmtModule = new DatabasemgmtModule();
  });

  it('should create an instance', () => {
    expect(databasemgmtModule).toBeTruthy();
  });
});
