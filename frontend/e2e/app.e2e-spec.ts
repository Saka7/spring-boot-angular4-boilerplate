import { AppPage } from './app.po';

describe('app-name App', function() {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should ...', () => {
    page.navigateTo();
    expect(page.getContainer()).toBeDefined();
  });
  
});
