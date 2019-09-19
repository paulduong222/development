import { ReportCostModule } from './report-cost.module';

describe('ReportCostModule', () => {
  let reportCostModule: ReportCostModule;

  beforeEach(() => {
    reportCostModule = new ReportCostModule();
  });

  it('should create an instance', () => {
    expect(reportCostModule).toBeTruthy();
  });
});
