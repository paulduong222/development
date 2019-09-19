import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { BaseUrlService } from '../service/base-url.service';
import { headersToString } from 'selenium-webdriver/http';

@Injectable()
export class AuthService {
  private _headers = new HttpHeaders({
    'Access-Control-Allow-Origin': '*'
  })
  /*
  httpOptions = {
    headers: new HttpHeaders({
      'Access-Control-Allow-Origin': '*',
    })
  };
  */
  constructor(private http: HttpClient, private baseUrlService: BaseUrlService) { }

  login(userName: string, pwd: string): Observable<DialogueVo> {
    /*
    const params: HttpParams = new HttpParams()
      .set('loginName', userName)
      .set('pwd', pwd);
    console.log('111authservice: ' + params.toString());
    return this.http.post(
      this.baseUrlService.baseRestUrl + 'auth'
      , params
      , this.httpOptions);
    */
    //  const rest = this.baseUrlService.baseRestUrl + 'auth?loginName=' + userName + '&pwd=' + pwd;
    // const rest = '/service/auth?loginName=' + userName + '&pwd=' + pwd;
    // console.log(rest)
    // return this.http.post<DialogueVo>(rest, this.httpOptions);
    return this.http.post<DialogueVo>('/service/auth', 
      {
        "username": userName,
        "password": pwd
      }, {
      headers: this._headers
    });
  }

  logout(userId): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/auth/${userId}/logout`, '', {
      headers: this._headers
    });
  }

  checkPasswordStatus( userId ): Observable<any> {
      return this.http.get(`/service/users/${userId}/password/status`, { responseType: 'text'});
    }
}
