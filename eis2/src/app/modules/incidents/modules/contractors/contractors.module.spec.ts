import { ContractorsModule } from './contractors.module';

describe('ContractorsModule', () => {
  let contractorsModule: ContractorsModule;

  beforeEach(() => {
    contractorsModule = new ContractorsModule();
  });

  it('should create an instance', () => {
    expect(contractorsModule).toBeTruthy();
  });
});
