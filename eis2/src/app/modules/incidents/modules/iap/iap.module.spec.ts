import { IapModule } from './iap.module';

describe('IapModule', () => {
  let iapModule: IapModule;

  beforeEach(() => {
    iapModule = new IapModule();
  });

  it('should create an instance', () => {
    expect(iapModule).toBeTruthy();
  });
});
