import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { TrainingSettingsData } from '../data/rest/training-settings-data';

@Injectable({
  providedIn: 'root'
})
export class TrainingSpecialistSettingsService {

  constructor(private http: HttpClient) { }

  getTrainingSettings(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId);
    return this.http.get<DialogueVo>(`/service/training-specialists/settings`, {params: params});   
  }  

  saveTrainingSettings(data: TrainingSettingsData, incidentGroupId, incidentIds): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentIds)
      .set('incidentGroupId', incidentGroupId);
    return this.http.post<DialogueVo>(`/service/training-specialists/settings`, data, {params: params});
  }

  getTrainingContactGrids(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
    .set('incidentId', incidentId)
    .set('incidentGroupId', incidentGroupId);
    return this.http.get<DialogueVo>(`/service/training-specialists/settings/training-contacts/grid`, {params: params});     
  }

  deleteTrainingContact(trainingContactId, incidentResourceId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
    .set('incidentResourceId', incidentResourceId);
    return this.http.delete<DialogueVo>(`/service/training-specialists/settings/training-contacts/${trainingContactId}`, {params: params});     
  }

  saveTrainingContact(data): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/training-specialists/settings/training-contacts/`, data);     
  }  

  getPriorityProgramGrid(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
    .set('incidentId', incidentId)
    .set('incidentGroupId', incidentGroupId);
    return this.http.get<DialogueVo>(`/service/training-specialists/settings/priority-programs/grid`, {params: params});     
  }  

  savePriorityProgram(data, incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
    .set('incidentId', incidentId)
    .set('incidentGroupId', incidentGroupId);
    return this.http.post<DialogueVo>(`/service/training-specialists/settings/priority-programs/`, data, {params: params});     
  }  

  deletePriorityProgram(priorityProgramId, incidentId, incidentGroupId, code): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
    .set('incidentId', incidentId)
    .set('incidentGroupId', incidentGroupId)
    .set('code', (code));
    return this.http.delete<DialogueVo>(`/service/training-specialists/settings/priority-programs/${priorityProgramId}`, {params: params});     
  }

  saveAcres(data: TrainingSettingsData): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/training-specialists/settings/acres/`, data);     
  }
}
