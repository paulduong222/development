import { DatatransferModule } from './datatransfer.module';

describe('DatatransferModule', () => {
  let datatransferModule: DatatransferModule;

  beforeEach(() => {
    datatransferModule = new DatatransferModule();
  });

  it('should create an instance', () => {
    expect(datatransferModule).toBeTruthy();
  });
});
