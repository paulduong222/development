import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { AgencyVo } from '../data/agency-vo';
import { AgencyData } from '../data/rest/agency-data';

@Injectable({
  providedIn: 'root'
})
export class AgencyService {

  constructor(private http: HttpClient) { }

  getGrid(): Observable<DialogueVo>{
    return this.http.get<DialogueVo>('/service/agencies/grid');
  }
  getGridIncidentOrGroup(incidentId, incidentGroupId, incidentOnly): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId)
      .set('incidentOnly', incidentOnly);
    return this.http.get<DialogueVo>('/service/agencies', {params: params});
  }

  save(data: AgencyData): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/agencies`, data);
  }

  delete(agencyId): Observable<DialogueVo> {
    return this.http.delete<DialogueVo>(`/service/agencies/${agencyId}`);
  }
}
