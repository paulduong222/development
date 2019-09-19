import { ReportPlansModule } from './report-plans.module';

describe('ReportPlansModule', () => {
  let reportPlansModule: ReportPlansModule;

  beforeEach(() => {
    reportPlansModule = new ReportPlansModule();
  });

  it('should create an instance', () => {
    expect(reportPlansModule).toBeTruthy();
  });
});
