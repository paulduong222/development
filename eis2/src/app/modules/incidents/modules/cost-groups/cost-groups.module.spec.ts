import { CostGroupsModule } from './cost-groups.module';

describe('CostGroupsModule', () => {
  let costGroupsModule: CostGroupsModule;

  beforeEach(() => {
    costGroupsModule = new CostGroupsModule();
  });

  it('should create an instance', () => {
    expect(costGroupsModule).toBeTruthy();
  });
});
