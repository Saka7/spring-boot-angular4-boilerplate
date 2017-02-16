import { browser, element, by } from 'protractor';

export class AppPage {
  navigateTo() {
    return browser.get('/');
  }

  getContainer() {
    return element(by.css('.container'));
  }

}
