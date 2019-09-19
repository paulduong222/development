import { IncResSharedModule } from './inc-res-shared.module';

describe('IncResSharedModule', () => {
  let incResSharedModule: IncResSharedModule;

  beforeEach(() => {
    incResSharedModule = new IncResSharedModule();
  });

  it('should create an instance', () => {
    expect(incResSharedModule).toBeTruthy();
  });
});
