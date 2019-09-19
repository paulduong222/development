import { ReportTnspModule } from './report-tnsp.module';

describe('ReportTnspModule', () => {
  let reportTnspModule: ReportTnspModule;

  beforeEach(() => {
    reportTnspModule = new ReportTnspModule();
  });

  it('should create an instance', () => {
    expect(reportTnspModule).toBeTruthy();
  });
});
