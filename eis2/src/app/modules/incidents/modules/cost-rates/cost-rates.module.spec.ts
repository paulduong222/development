import { CostRatesModule } from './cost-rates.module';

describe('CostRatesModule', () => {
  let costRatesModule: CostRatesModule;

  beforeEach(() => {
    costRatesModule = new CostRatesModule();
  });

  it('should create an instance', () => {
    expect(costRatesModule).toBeTruthy();
  });
});
