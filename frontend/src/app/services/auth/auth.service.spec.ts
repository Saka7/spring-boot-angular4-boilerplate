import { TestBed, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { AuthService } from './auth.service';

describe('AuthService', () => {
  let store = {};

  const user = JSON.stringify({
    id: 1,
    sub: "user",
    token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
      + ".eyJpZCI6IjEiLCJuYW1lIjoidXNlciJ9"
      + ".PUHflJtYA6kdUev8BwbC_a1GBi3SCCWxQstZQGBYY7g"
  });

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        MockBackend,
        BaseRequestOptions,
        {
          provide: Http,
          useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
            return new Http(backendInstance, defaultOptions);
          },
          deps: [MockBackend, BaseRequestOptions]
        },
        AuthService
      ]
    });
  });

  beforeEach(() => {
    store = {user: user};
    spyOn(sessionStorage, 'getItem').and.callFake(key => {
      return store[key];
    });
  });

  it('should return token if exists',
      inject([AuthService], (authService: AuthService) => {
    authService.refresh();
    const retrievedToken = authService.getToken();
    expect(retrievedToken).toBeDefined();
    expect(retrievedToken).not.toBeNull();
    expect(retrievedToken).toEqual(JSON.parse(user).token);
  }));

  it('should return null if token does not exists',
      inject([AuthService], (authService: AuthService) => {
    store = {};
    authService.refresh();
    const retrievedToken = authService.getToken();
    expect(retrievedToken).toBeNull();
  }));

  it('should return userId if exists',
      inject([AuthService], (authService: AuthService) => {
    authService.refresh();
    const userId = authService.getUserId();
    expect(userId).toBeDefined();
    expect(userId).not.toBeNull();
    expect(userId).toEqual(1);
  }));

  it('should return null if userId does not exists',
      inject([AuthService], (authService: AuthService) => {
    store = {};
    authService.refresh();
    const userId = authService.getUserId();
    expect(userId).toBeNull();
  }));

  it('should return username if exists',
      inject([AuthService], (authService: AuthService) => {
    authService.refresh();
    const username = authService.getUsername();
    expect(username).toBeDefined();
    expect(username).not.toBeNull();
    expect(username).toEqual("user");
  }));

  it('should return null if username does not exists',
      inject([AuthService], (authService: AuthService) => {
    store = {};
    authService.refresh();
    const username = authService.getUsername();
    expect(username).toBeNull();
  }));

  describe('isAuthorized method', () => {

    it('should return true if token exists',
        inject([AuthService], (authService: AuthService) => {
      authService.refresh();
      const isAuthorized = authService.isAuthorized();
      expect(isAuthorized).toBeTruthy();
    }));

    it('should return false if token does not exists',
        inject([AuthService], (authService: AuthService) => {
      store = {user: undefined};
      authService.refresh();
      const isAuthorized = authService.isAuthorized();
      expect(isAuthorized).toBeFalsy();
    }));

  });

});