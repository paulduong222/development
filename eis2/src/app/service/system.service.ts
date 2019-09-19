import { Injectable, Output, EventEmitter } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse, HttpErrorResponse, HttpBackend } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { BaseUrlService } from '../service/base-url.service';
import { BehaviorSubject } from 'rxjs';
import { SystemRoleData } from '../data/system-role-data';
// import { EventEmitter } from 'protractor';

export class DbAvailVo {
  databaseName?: string;
  datasource?: string;
}
@Injectable()
export class SystemService {
  private localHttp: HttpClient;
  private dbnameSource = new BehaviorSubject('');
  private usernameSource = new BehaviorSubject('');
  currentdbname = this.dbnameSource.asObservable();
  currentUsername = this.usernameSource.asObservable();
  userSessionVo = {};
  serverDate;
  serverVersion;
  tempDbName;
  changePasswordDesc;
  tempPwd;

  constructor(private http: HttpClient, private baseUrlService: BaseUrlService, handler:HttpBackend) {
    this.localHttp = new HttpClient(handler);
  }

  setdbname(dbname: string){
    this.dbnameSource.next(dbname);
  }
  setUsername(username: string){
    this.usernameSource.next(username);
  }

  reset() {
    this.userSessionVo = {};
  }
  isAdmin(): Observable<boolean> {
    let r = this.userSessionVo['userRoleVos'].find(role => role['roleName'] === 'ROLE_ACCOUNT_MANAGER')['roleName'];
    console.log('isAdmin() ' + r);
    if ( r === 'ROLE_ACCOUNT_MANAGER' ) {
      return Observable.of(true);
    } else {
      return Observable.of(false);
    }
  }

  hasAnyRole(roles: any): boolean {

    for (const i of Object.keys(roles)) {
      // console.log('role: ' + roles[i]);
      const r = this.userSessionVo['userRoleVos'].find( role => role['roleName'] === roles[i]);
      if ( r ) {
        return true;
      }
    }
    return false;
  }
  getDisclaimerText(): Observable<any> {
    //Getting disclaimer text from angular assets folder
    return this.localHttp.get('assets/disclaimer.txt',{responseType: 'text'});
    // return this.http.get<any>(this.baseUrlService.baseUrl + 'ui/assets/disclaimer.txt');
    //Getting disclaimer text from Java Webrot/ui folder
    // return this.http.get('/ui/assets/disclaimer.txt',{responseType: 'text'});
  }

  getRunMode(): Observable<any> {
    // return this.http.get<any>(this.baseUrlService.baseRestUrl + 'system/{runmode}');
    return this.http.get('/service/system/runmode',{responseType: 'text'});
  }

  getDatabaseList(): Observable<DialogueVo> {
    // return this.http.get<DialogueVo>(this.baseUrlService.baseRestUrl + 'system/dblist');
    return this.http.get<DialogueVo>('/service/system/dbs');
  }

  getUserList(): Observable<DialogueVo>{
    return this.http.get<DialogueVo>('/service/users/');
  }

  connectToSiteDatabase(databaseName: string, datasource: string): Observable<DialogueVo> {
    // console.log('connecting');
    const dbAvailVo = new DbAvailVo();
    dbAvailVo.databaseName = databaseName;
    dbAvailVo.datasource = datasource;
    // console.log(JSON.stringify(dbAvailVo).toString());
    // return this.http.post(this.baseUrlService.baseRestUrl + 'system/connectToSiteDatabase',dbAvailVo,this.baseUrlService.postHttpOptions);
    return this.http.post(
      '/service/system/dbs/site/connect',
      dbAvailVo,
      this.baseUrlService.postHttpOptions);
  }

  getSystemRoles(): Observable<DialogueVo> {
    return this.http.get<DialogueVo>('/service/system/roles');
  }

  getLastRecoverCode(dbName: String): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/system/lastRecoverCode/${dbName}`);
  }

  authenticateRecoverCode(dbName: String, authCode: String): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/system/authenticateRecoverCode/${dbName},${authCode}`);
  }

}
