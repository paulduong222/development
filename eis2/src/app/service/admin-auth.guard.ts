import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AdminAuthGuard implements CanActivate, CanActivateChild {


  private isAdminUser: boolean = false;

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    return this.isAdminUser;
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.isAdminUser;;
  }

  setIsAdminUser(isAdmin: boolean) {
    this.isAdminUser = isAdmin;
  }

  reset() {
    this.isAdminUser = false;
  }

}
