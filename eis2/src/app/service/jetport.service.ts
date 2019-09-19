import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { JetportData } from '../data/rest/jetport-data';

@Injectable({
  providedIn: 'root'
})
export class JetportService {

  constructor(private http: HttpClient) { }

  getGrid(incidentId, incidentGroupId, incidentOnly): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId)
      .set('incidentOnly', incidentOnly)
      ;
    return this.http.get<DialogueVo>('/service/jetports/grid', {params: params});
  }

  save(data: JetportData): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/jetports`, data);
  }

  delete(jetportId): Observable<DialogueVo> {
    return this.http.delete<DialogueVo>(`/service/jetports/${jetportId}`);
  }

}
