import { ImportexportModule } from './importexport.module';

describe('ImportexportModule', () => {
  let importexportModule: ImportexportModule;

  beforeEach(() => {
    importexportModule = new ImportexportModule();
  });

  it('should create an instance', () => {
    expect(importexportModule).toBeTruthy();
  });
});
