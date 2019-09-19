import { Buffer } from 'buffer';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { UserGridVo } from '../modules/users/data/userGridVo';
import { ConcatSource } from 'webpack-sources';
import { TagContentType } from '@angular/compiler';
import { UserVo } from '../data/user-vo';


@Injectable()
export class UserService {
  httpOptions = {
    headers: new HttpHeaders({
      'Access-Control-Allow-Origin': '*',
    })
  };
  constructor(private http: HttpClient) { }

  getUserList(): Observable<DialogueVo> {
    return this.http.get<DialogueVo>('/service/users')
  }

  getById(userId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/users/${userId}`);
  }

  updateRob(userId, robType): Observable<DialogueVo> {
    const params = new HttpParams()
      .append('robType', robType);
    return this.http.put<DialogueVo>(`/service/users/${userId}/robupdate`, null, { params: params });
  }

  changePassword(userId, curPwd, newPwd, confPwd): Observable<DialogueVo> {
    return this.http.put<DialogueVo>(`/service/users/${userId}/password`,
      {
        "curPwd": curPwd,
        "newPwd": newPwd,
        "confPwd": confPwd
      });
  }

  exportUsers(userGridVos) {
    return this.http.post('/service/users/export', userGridVos, {responseType: 'arraybuffer'}); 
  }

  
  importUsers(defaultPassword, confirmDefaultPassword, data) {
    const params = new HttpParams()
    .set('defaultPassword',defaultPassword)
    .set('confirmDefaultPassword',confirmDefaultPassword)

    return this.http.post<DialogueVo>('/service/users/import',data, {params:params});
  }

  resolveImport(userGridVo){
    return this.http.post<DialogueVo>('/service/users/resolveImport', userGridVo);
  }

  saveUser(userVo: UserVo): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/users/${userVo.id}/save`, userVo);
  }

  deleteUser(userId): Observable<DialogueVo> {
    return this.http.delete<DialogueVo>(`/service/users/${userId}`);
  }

  createNewAccountManager(userVo: UserVo): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/users/createNewAccountManager`, userVo);
  }

}
