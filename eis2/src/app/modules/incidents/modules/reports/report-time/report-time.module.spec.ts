import { ReportTimeModule } from './report-time.module';

describe('ReportTimeModule', () => {
  let reportTimeModule: ReportTimeModule;

  beforeEach(() => {
    reportTimeModule = new ReportTimeModule();
  });

  it('should create an instance', () => {
    expect(reportTimeModule).toBeTruthy();
  });
});
