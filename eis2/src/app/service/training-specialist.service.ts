import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { ResourceTrainingData } from '../data/rest/resource-training-data';
import { ResourceHomeUnitContactData } from '../data/rest/resource-home-unit-contact-data';
import { RscTrainingTrainerData } from '../data/rest/rsc-training-trainer-data';

@Injectable({
  providedIn: 'root'
})
export class TrainingSpecialistService {

  constructor(private http: HttpClient) { }

  getResourceTraining(incidentResourceId:number): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/training-specialists/resource-trainings/${incidentResourceId}`);
  }

  saveResourceTraining(data: ResourceTrainingData): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/training-specialists/resource-trainings/`, data);     
  }

  deleteResourceTraining(resourceTrainingId: number, incidentResourceId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()  
    .set('incidentResourceId', incidentResourceId)  
    return this.http.delete<DialogueVo>(`/service/training-specialists//resource-trainings/${resourceTrainingId}/incident-resources/{incidentResourceId}`, {params: params});
  }

  getHomeUnitContact(incidentResourceId: number): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/training-specialists/incident-resources/${incidentResourceId}/home-unit-contacts/`);
  }

  saveHomeUnitContact(data: ResourceHomeUnitContactData): Observable<DialogueVo> {
     return this.http.post<DialogueVo>(`/service/training-specialists/home-unit-contacts/`, data);     
   }

   getIncidentEvaluatorsGrid(incidentResourceId: number, incidentId, incidentGroupId): Observable<DialogueVo> {
      const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId);
      return this.http.get<DialogueVo>(`/service/training-specialists/incident-resources/${incidentResourceId}/incident-evaluators/grid/`, {params: params});
   }

   saveEvaluator(data: RscTrainingTrainerData): Observable<DialogueVo> {
      return this.http.post<DialogueVo>(`/service/training-specialists//trainers/`, data); 
   }

   deleteEvaluator(trainerId: number, incidentResourceId): Observable<DialogueVo> {
      const params: HttpParams = new HttpParams()  
      .set('incidentResourceId', incidentResourceId)  
      return this.http.delete<DialogueVo>(`/service/training-specialists/trainers/${trainerId}`, {params: params});
    }

    getPriorityTrainees(incidentId, incidentGroupId): Observable<DialogueVo> {
      const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId);
      return this.http.get<DialogueVo>(`/service/training-specialists/trainees/totals/priority/`, {params: params});
    }

    getTraineeTotal(incidentId, incidentGroupId): Observable<DialogueVo> {
      const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId);
      return this.http.get<DialogueVo>(`/service/training-specialists/trainees/totals/`, {params: params});
    }

}
