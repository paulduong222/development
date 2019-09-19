import { CostAccrualsModule } from './cost-accruals.module';

describe('CostAccrualsModule', () => {
  let costAccrualsModule: CostAccrualsModule;

  beforeEach(() => {
    costAccrualsModule = new CostAccrualsModule();
  });

  it('should create an instance', () => {
    expect(costAccrualsModule).toBeTruthy();
  });
});
