import { CostModule } from './cost.module';

describe('CostModule', () => {
  let costModule: CostModule;

  beforeEach(() => {
    costModule = new CostModule();
  });

  it('should create an instance', () => {
    expect(costModule).toBeTruthy();
  });
});
