import { TravelBestPage } from './app.po';

describe('travel-best App', function() {
  let page: TravelBestPage;

  beforeEach(() => {
    page = new TravelBestPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
