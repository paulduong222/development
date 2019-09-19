import { IncModule } from './inc.module';

describe('IncModule', () => {
  let incModule: IncModule;

  beforeEach(() => {
    incModule = new IncModule();
  });

  it('should create an instance', () => {
    expect(incModule).toBeTruthy();
  });
});
