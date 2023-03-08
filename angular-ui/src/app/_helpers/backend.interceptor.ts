import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpResponse,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS
} from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { delay, mergeMap, materialize, dematerialize } from 'rxjs/operators';

import { Logger } from '../_services/logging.service';
import { RestService } from '../_services/rest.service';

@Injectable()
export class BackendInterceptor implements HttpInterceptor {

  constructor(private logger: Logger, private restService: RestService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const { url, method, headers, body} = request;

    if (url.endsWith('/users/authenticate') && method === 'POST') { 
      const { email, password } = body;
      this.logger.log('Email is ' + email + ', Password is ' + password);
      
      this.restService.getToken(email, password)
      .subscribe(data => {
        localStorage.setItem('jwtToken', data.jwtToken);
        //localStorage.setItem('jwtExpiration', data.jwtExpiration);
      });
    }
    
    return of(null)
      .pipe(mergeMap(handleRoute))
      .pipe(materialize())
      .pipe(delay(500))
      .pipe(dematerialize());
    
    function handleRoute() {
      switch (true) {
        case url.endsWith('/users/authenticate') && method === 'POST':
          return authenticate();
        //case url.endsWith('/users') && method === 'GET':
        //  return getUsers();
        default:
          return next.handle(request);
      }
    }

    function authenticate() {
      const { email, password} = body;
      //const user = users.find(x => x.email === email && x.password === password);
      const token = localStorage.getItem('jwtToken');
      if (!token) {
        return error('Username or password is incorrect');
      }  
      
      return ok({
        email: email,
        token: token
      })
    }

    //function getUsers() {
    //  if (!isLoggedIn()) return unauthorized();
    //  return ok(users);
    //}

    function ok(body?) {
      return of(new HttpResponse({ status: 200, body}));
    }

    function error(message) {
      return throwError({ error: { message } });
    }

    function unauthorized() {
      return throwError({ status: 401, error: { message: 'Unauthorized' } });
    }

    function isLoggedIn() {
      return headers.get('Authorization') === 'Bearer ' + localStorage.getItem('loginServiceToken');
    }

  }
}

export let backendProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: BackendInterceptor,
  multi: true
}
