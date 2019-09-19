import { CheckindemobModule } from './checkindemob.module';

describe('CheckindemobModule', () => {
  let checkindemobModule: CheckindemobModule;

  beforeEach(() => {
    checkindemobModule = new CheckindemobModule();
  });

  it('should create an instance', () => {
    expect(checkindemobModule).toBeTruthy();
  });
});
