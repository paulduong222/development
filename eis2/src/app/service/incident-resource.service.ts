import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { IncidentResourceData } from '../data/rest/incident-resource-data';
import { IncidentResourceVo } from '../data/incident-resource-vo';

@Injectable({
  providedIn: 'root'
})
export class IncidentResourceService {

  constructor(private http: HttpClient) { }

  getIncidentResourceGrid(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', ( incidentId !== null ? incidentId : 0))
      .set('incidentGroupId', (incidentGroupId !== null ? incidentGroupId : 0))
      .set('incidentsOnly', false.toString())
      .set('filterExcluded', false.toString());
    return this.http.get<DialogueVo>(`/service/incident-resources/grid`,{params: params});
  }

  getIncidentResourceById(incidentResourceId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incident-resources/${incidentResourceId}`);
  }

  saveIncidentResource(vo: IncidentResourceVo, propagateToChildren, dvo: DialogueVo): Observable<DialogueVo> {
    const data = <IncidentResourceData> {
      propagateToChildren: propagateToChildren
      , incidentResourceVo: vo
      , dialogueVo: dvo
    };
    return this.http.post<DialogueVo>(`/service/incident-resources`, data);
  }

  deleteIncidentResource(irId, resId, dvo): Observable<DialogueVo> {
    const data = <IncidentResourceData> {
      dialogueVo: dvo
    };
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };
    return this.http.delete<DialogueVo>(`/service/incident-resources/${irId}/resources/${resId}`, options);
  }
}
