import { MessageboardModule } from './messageboard.module';

describe('MessageboardModule', () => {
  let messageboardModule: MessageboardModule;

  beforeEach(() => {
    messageboardModule = new MessageboardModule();
  });

  it('should create an instance', () => {
    expect(messageboardModule).toBeTruthy();
  });
});
