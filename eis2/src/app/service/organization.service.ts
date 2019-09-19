import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { OrganizationData } from '../data/rest/organization-data';

@Injectable({
  providedIn: 'root'
})
export class OrganizationService {

  constructor(private http: HttpClient) { }

  getGrid(incidentId, incidentGroupId, incidentOnly): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId)
      .set('incidentOnly', incidentOnly);
    return this.http.get<DialogueVo>('/service/organizations/grid', {params: params});
  }

  save(data: OrganizationData): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/organizations`, data);
  }

  delete(orgId): Observable<DialogueVo> {
    return this.http.delete<DialogueVo>(`/service/organizations/${orgId}`);
  }
}
