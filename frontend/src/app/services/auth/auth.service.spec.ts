/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import {HttpModule} from '@angular/http';
import { Http, ConnectionBackend } from '@angular/http';
import { AuthService } from './auth.service';

describe('AuthService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule], 
      providers: [ConnectionBackend, Http, AuthService]
    });
  });

  it('should ...', inject([AuthService], (service: AuthService) => {
    
  }));
  
});
