import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';

import { AuthServiceService } from '../_services/auth-service.service';

@Injectable({ providedIn: 'root' })
export class AuthGuardGuard implements CanActivate {
  constructor(
    private router: Router,
    private authService: AuthServiceService
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    /*
    if (localStorage.getItem('jwtExpiration') && 
    new Date() > new Date(localStorage.getItem('jwtExpiration'))) {
      return false;
    } */

    const currentUser = this.authService.currentUserValue;
    if (currentUser) {
      return true;
    }

    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }
  
}
