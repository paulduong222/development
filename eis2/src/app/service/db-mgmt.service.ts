import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { DbAvailVo } from '../data/db-avail-vo';
import { DbBackupData } from '../data/rest/db-backup-data';
import { DbRestoreData } from '../data/rest/db-restore-data';
import { DbCopyData } from '../data/rest/db-copy-data';

@Injectable({
  providedIn: 'root'
})
export class DbMgmtService {

  constructor(private http: HttpClient) { }

  getGrid(): Observable<DialogueVo> {
    return this.http.get<DialogueVo>('/service/mgmt/dbs');
  }

  save(dbAvailVo: DbAvailVo): Observable<DialogueVo> {
    return this.http.post('/service/mgmt/dbs', dbAvailVo);
  }

  remove(dbAvailVo: DbAvailVo): Observable<DialogueVo> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: dbAvailVo,
    };
    const id = dbAvailVo.id;
    return this.http.delete<DialogueVo>(`/service/mgmt/dbs/${id}`, options);
  }

  backup(dbBackupData: DbBackupData): Observable<DialogueVo> {
    const id = dbBackupData.vo.id;
    return this.http.post<DialogueVo>(`/service/mgmt/dbs/${id}/backup`, dbBackupData);
  }

  uploadBackupFile(data) {
    return this.http.post<any>('/servlet/db/DatabaseUploadServlet', data, {
      responseType: 'text' as 'json'
    });
  }

  restore(dbRestoreData: DbRestoreData): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/mgmt/dbs/restore`, dbRestoreData);
  }

  copy(dbCopyData: DbCopyData): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/mgmt/dbs/${dbCopyData.sourceVo.id}/copy`, dbCopyData);
  }

  recover(filename): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('filename', filename);
    return this.http.post<DialogueVo>(`/service/mgmt/dbs/recover`, null, {params: params});
  }
}
