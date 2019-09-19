import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserSessionService {
  authenticated = false;
  databaseName = '';
  loginName = '';
  roles = [];

  constructor() { }
}
