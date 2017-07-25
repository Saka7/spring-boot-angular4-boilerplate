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
    store = {};
    spyOn(sessionStorage, 'getItem').and.callFake(key => {
      return store[key];
    });
  });

  it('should return token if exists',
      inject([AuthService], (authService: AuthService) => {
    store = {user: user};
    authService.refreshUserData();
    const retrievedToken = authService.getToken();
    expect(retrievedToken).toBeDefined();
    expect(retrievedToken).not.toBeNull();
    expect(retrievedToken).toEqual(JSON.parse(user).token);
  }));

  it('should return undefined if token does not exists',
      inject([AuthService], (authService: AuthService) => {
    authService.refreshUserData();
    const retrievedToken = authService.getToken();
    expect(retrievedToken).toBeUndefined();
  }));

  it('should return userId if exists',
      inject([AuthService], (authService: AuthService) => {
    store = {user: user};
    authService.refreshUserData();
    const userId = authService.getUserId();
    expect(userId).toBeDefined();
    expect(userId).not.toBeNull();
    expect(userId).toEqual(1);
  }));

  it('should return undefined if userId does not exists',
      inject([AuthService], (authService: AuthService) => {
    authService.refreshUserData();
    const userId = authService.getUserId();
    expect(userId).toBeUndefined();
  }));

  it('should return username if exists',
      inject([AuthService], (authService: AuthService) => {
    store = {user: user};
    authService.refreshUserData();
    const username = authService.getUsername();
    expect(username).toBeDefined();
    expect(username).not.toBeNull();
    expect(username).toEqual("user");
  }));

  it('should return undefined if username does not exists',
      inject([AuthService], (authService: AuthService) => {
    authService.refreshUserData();
    const username = authService.getUsername();
    expect(username).toBeUndefined();
  }));

  describe('isAuthorized method', () => {

    it('should return true if token exists',
        inject([AuthService], (authService: AuthService) => {
      store = {user: user};
      authService.refreshUserData();
      const isAuthorized = authService.isAuthorized();
      expect(isAuthorized).toBeTruthy();
    }));

    it('should return false if token does not exists',
        inject([AuthService], (authService: AuthService) => {
      authService.refreshUserData();
      const isAuthorized = authService.isAuthorized();
      expect(isAuthorized).toBeFalsy();
    }));

  });

});