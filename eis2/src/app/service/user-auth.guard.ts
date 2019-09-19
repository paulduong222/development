import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserAuthGuard implements CanActivate, CanActivateChild {

  private isUser: boolean = true;

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    return this.isUser;
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.isUser;
  }

  setIsUser(isUser: boolean) {
    this.isUser = isUser;
  }

  reset() {
    this.isUser = true;
  }
}
