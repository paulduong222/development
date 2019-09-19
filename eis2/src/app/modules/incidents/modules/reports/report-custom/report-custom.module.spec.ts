import { ReportCustomModule } from './report-custom.module';

describe('ReportCustomModule', () => {
  let reportCustomModule: ReportCustomModule;

  beforeEach(() => {
    reportCustomModule = new ReportCustomModule();
  });

  it('should create an instance', () => {
    expect(reportCustomModule).toBeTruthy();
  });
});
