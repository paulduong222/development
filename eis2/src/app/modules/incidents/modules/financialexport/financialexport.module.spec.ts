import { FinancialexportModule } from './financialexport.module';

describe('FinancialexportModule', () => {
  let financialexportModule: FinancialexportModule;

  beforeEach(() => {
    financialexportModule = new FinancialexportModule();
  });

  it('should create an instance', () => {
    expect(financialexportModule).toBeTruthy();
  });
});
