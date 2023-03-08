import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

import { Logger } from './logging.service';
import { environment } from 'src/environments/environment';
import { JwtResponse } from '../_models/jwtResponse';
import { Employee } from '../_models/employee';

@Injectable({ providedIn: 'root' })
export class RestService {

  constructor(private http: HttpClient, private logger: Logger) { }

  getToken(email: String, password: String): Observable<JwtResponse> {
    this.logger.log('Inside getToken()');
    return this.http
    .post<JwtResponse>(`${environment.loginServiceUrl}/api/signin`, 
    { username: email, password: password })
    .pipe(retry(1), catchError(this.handleError));
  }

  getEmployees() {
    this.logger.log('Inside getEmployees()');

    return this.http
    .get<Employee[]>(`${environment.employeeServiceUrl}/employee/findAll`)
    .pipe(retry(1), catchError(this.handleError));
    
  }

  getEmployee(id: number) {
    this.logger.log('Inside getEmployee()');

    return this.http
    .get<Employee>(`${environment.employeeServiceUrl}/employee/find/${id}`)
    .pipe(retry(1), catchError(this.handleError));
  }

  createEmployee(employee: Employee) {
    this.logger.log('Inside createEmployee()');

    return this.http
    .post<Employee>(`${environment.employeeServiceUrl}/employee/create`, employee)
    .pipe(retry(1), catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred.
      console.error('An error occurred: ', error.error);
    } else {
      //The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was `, error.error
      );
    }
    //Return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.'
    );
  }

}
