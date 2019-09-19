import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { KindData } from 'src/app/data/rest/kind-data';

@Injectable({
  providedIn: 'root'
})
export class ItemCodeService {

  constructor(private http: HttpClient) { }

  getGrid(incidentId, incidentGroupId, incidentOnly): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId)
      .set('incidentOnly', incidentOnly);
    return this.http.get<DialogueVo>('/service/kinds/grid', {params: params});
  }

  save(data: KindData): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/kinds`, data);
  }

  delete(kindId): Observable<DialogueVo> {
    return this.http.delete<DialogueVo>(`/service/kinds/${kindId}`);
  }

}
