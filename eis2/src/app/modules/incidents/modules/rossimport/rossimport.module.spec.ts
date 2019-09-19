import { RossimportModule } from './rossimport.module';

describe('RossimportModule', () => {
  let rossimportModule: RossimportModule;

  beforeEach(() => {
    rossimportModule = new RossimportModule();
  });

  it('should create an instance', () => {
    expect(rossimportModule).toBeTruthy();
  });
});
