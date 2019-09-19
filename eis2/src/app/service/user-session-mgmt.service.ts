import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';

@Injectable()
export class UserSessionMgmtService {

  constructor(private http: HttpClient) { }

  getActiveUsers(userId, dbName): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/sessions/dbs/${dbName}/users/${userId}`);
  }

  disconnectUser(userId, dbName): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/sessions/dbs/${dbName}/users/${userId}/disconnect`, null);
  }
}
