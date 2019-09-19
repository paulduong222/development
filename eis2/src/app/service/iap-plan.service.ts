import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { IapPlanVo } from '../data/iap-plan-vo';
import { IapPlanData } from '../data/rest/iap-plan-data';
import { DialogueData } from '../data/rest/dialogue-data';
import { IapForm202Vo } from '../data/iap-form202-vo';
import { IapForm202Data } from '../data/rest/iap-form202-data';
import { IapPrintJobVo } from '../data/iap-print-job-vo';
import { IapPrintJobData } from '../data/rest/iap-print-job-data';
import { IapForm205Data } from '../data/rest/iap-form205-data';
import { IapForm205Vo } from '../data/iap-form205-vo';
import { IapFrequencyVo } from '../data/iap-frequency-vo';
import { IapFrequencyData } from '../data/rest/iap-frequency-data';
import { IapForm220Vo } from '../data/iap-form220-vo';
import { IapForm220Data } from '../data/rest/iap-form220-data';
import { IapAircraftData } from '../data/rest/iap-aircraft-data';
import { IapAircraftVo } from '../data/iap-aircraft-vo';
import { IapAircraftTaskVo } from '../data/iap-aircraft-task-vo';
import { IapAircraftTaskData } from '../data/rest/iap-aircraft-task-data';
import { IapForm206Vo } from '../data/iap-form206-vo';
import { IapForm206Data } from '../data/rest/iap-form206-data';
import { IapMedicalAidVo } from '../data/iap-medical-aid-vo';
import { IapMedicalAidData } from '../data/rest/iap-medical-aid-data';
import { IapHospitalVo } from '../data/iap-hospital-vo';
import { IapHospitalData } from '../data/rest/iap-hospital-data';
import { IapAreaLocationCapabilityVo } from '../data/iap-area-location-capability-vo';
import { IapAreaLocationCapabilityData } from '../data/rest/iap-area-location-capability-data';
import { IapRemoteCampLocationsVo } from '../data/iap-remote-camp-locations-vo';
import { IapRemoteCampLocationData } from '../data/rest/iap-remote-camp-location-data';
import { IapForm203Vo } from '../data/iap-form203-vo';
import { IapForm203Data } from '../data/rest/iap-form203-data';

@Injectable({
  providedIn: 'root'
})
export class IapPlanService {

  constructor(private http: HttpClient) { }

  printIapPlan(printJobVo: IapPrintJobVo): Observable<DialogueVo> {
    const data = <IapPrintJobData>{
      iapPrintJobVo: printJobVo
      , dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/iaps/plans/job-data/print`, data);
  }

  getIapPlanGrid(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId)
      ;
    return this.http.get<DialogueVo>('/service/iaps/grid', {params: params});
  }

  getIapPlan(planId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/iaps/plans/${planId}`);
  }

  deleteIapPlan(planId): Observable<DialogueVo> {
    return this.http.delete<DialogueVo>(`/service/iaps/plans/${planId}`);
  }

  saveIapPlan(iapPlanVo: IapPlanVo): Observable<DialogueVo> {
    const data = <IapPlanData>{
      iapPlanVo: iapPlanVo
      , dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/iaps/plans`, data);
  }

  lockUnlockPlan(planId, action: string): Observable<DialogueVo> {
    const data = <DialogueData>{};
    return this.http.put<DialogueVo>(`/service/iaps/plans/lock-unlock/${planId}/${action}`, data );
  }

  deleteIapForm(id, formType): Observable<DialogueVo> {
    return this.http.delete<DialogueVo>(`/service/iaps/forms/${formType}/${id}`);
  }

  lockUnlockForm(formId, formType, action: string): Observable<DialogueVo> {
    const data = <DialogueData>{};
    return this.http.put<DialogueVo>(`/service/iaps/forms/lock-unlock/${formType}/${formId}/${action}`, data );
  }

  addIapForm202(vo: IapForm202Vo): Observable<DialogueVo> {
    const data = <IapForm202Data>{
      iapForm202Vo: vo
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-202`, data );
  }

  saveIapForm202(vo: IapForm202Vo): Observable<DialogueVo> {
    const data = <IapForm202Data>{
      iapForm202Vo: vo
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-202`, data );
  }

  addIapForm203(vo: IapForm203Vo): Observable<DialogueVo> {
    const data = <IapForm203Data>{
      iapForm203Vo: vo
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-203`, data );
  }

  getIapForm(formId, formType: string): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/iaps/forms/${formType}/${formId}`);
  }

  addIapForm205(vo: IapForm205Vo): Observable<DialogueVo> {
    const data = <IapForm205Data>{
      iapForm205Vo: vo
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-205`, data );
  }

  addIapForm206(vo: IapForm206Vo): Observable<DialogueVo> {
    const data = <IapForm206Data>{
      iapForm206Vo: vo
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-206`, data );
  }

  addIapForm220(vo: IapForm220Vo): Observable<DialogueVo> {
    const data = <IapForm220Data>{
      iapForm220Vo: vo
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-220`, data );
  }

  saveIapForm205(vo: IapForm205Vo): Observable<DialogueVo> {
    const data = <IapForm205Data>{
      iapForm205Vo: vo
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-205`, data );
  }


  saveIapForm205Frequency(iapForm205Id, vo: IapFrequencyVo): Observable<DialogueVo> {
    const data = <IapFrequencyData>{
      iapFrequencyVo: vo
      , iapFrequencyVos: []
    };
    const params: HttpParams = new HttpParams()
      .set('iapForm205Id', iapForm205Id)
      ;

    return this.http.post<DialogueVo>(`/service/iaps/forms/form-205/frequency`, data, { params: params} );
  }

  addIapForm205Frequencies(iapForm205Id, vos: IapFrequencyVo[]): Observable<DialogueVo> {
    const data = <IapFrequencyData>{
      iapFrequencyVo: null
      , iapFrequencyVos: vos
    };
    const params: HttpParams = new HttpParams()
      .set('iapForm205Id', iapForm205Id)
      ;

    return this.http.post<DialogueVo>(`/service/iaps/forms/form-205/frequencies`, data, { params: params} );
  }

  saveIapForm205FrequencyPositions(formId, vos: IapFrequencyVo[]): Observable<DialogueVo> {
    const data = <IapFrequencyData>{
      iapFrequencyVo: <IapFrequencyVo>{}
      , iapFrequencyVos: vos
      , dialogueVo: null
    };

    return this.http.post<DialogueVo>(`/service/iaps/forms/form-205/freq/positions`, data );
  }

  verifyIapForm205Frequencies(planId, formId): Observable<DialogueVo> {
    const data = <IapFrequencyData>{
      iapFrequencyVo: <IapFrequencyVo>{}
      , iapFrequencyVos: null
      , dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/iaps/plans/${planId}/forms/form-205/${formId}/frequencies/verify`, data);
  }

  deleteIapForm205Frequency(vo: IapFrequencyVo): Observable<DialogueVo> {
    const id = vo.id;
    const data = <IapFrequencyData>{
      iapFrequencyVo: vo
      , iapFrequencyVos: null
      , dialogueVo: null
    };
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };

    return this.http.delete<DialogueVo>(`/service/iaps/forms/form-205/${id}/frequency`, options);
  }

  saveIapForm206(id, vo: IapForm206Vo): Observable<DialogueVo> {
    const data = <IapForm206Data>{
      iapForm206Vo: vo
    };
    return this.http.put<DialogueVo>(`/service/iaps/forms/form-206/${id}`, data );
  }

  saveIapForm206Ambulance(formId, vo: IapMedicalAidVo): Observable<DialogueVo> {
    const data = <IapMedicalAidData>{
      iapMedicalAidVo: vo
      , iapMedicalAidVos: null
    };
    const params: HttpParams = new HttpParams()
      .set('id', formId)
      ;
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-206/ambulance`, data, {params: params} );
  }

  saveIapForm206AmbulancePositions(formId, vos: IapMedicalAidVo[]): Observable<DialogueVo> {
    const data = <IapMedicalAidData>{
      iapMedicalAidVo: null
      , iapMedicalAidVos: vos
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-206/positions/ambulance`, data );
  }

  saveIapForm206AirAmbulancePositions(formId, vos: IapMedicalAidVo[]): Observable<DialogueVo> {
    const data = <IapMedicalAidData>{
      iapMedicalAidVo: null
      , iapMedicalAidVos: vos
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-206/positions/air-ambulance`, data );
  }

  saveIapForm206AreaLocCapPositions(formId, vos: IapAreaLocationCapabilityVo[]): Observable<DialogueVo> {
    const data = <IapAreaLocationCapabilityData>{
      iapAreaLocationCapabilityVo: null
      , iapAreaLocationCapabilityVos: vos
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-206/positions/alc`, data );
  }

  saveIapForm206RemoteCampLocPositions(formId, vos: IapRemoteCampLocationsVo[]): Observable<DialogueVo> {
    const data = <IapRemoteCampLocationData>{
      iapRemoteCampLocationsVo: null
      , iapRemoteCampLocationsVos: vos
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-206/positions/rcl`, data );
  }

  saveIapForm206HospitalPositions(formId, vos: IapHospitalVo[]): Observable<DialogueVo> {
    const data = <IapHospitalData>{
      iapHospitalVo: null
      , iapHospitalVos: vos
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-206/positions/hospital`, data );
  }


  deleteIapForm206Ambulance(formId, vo: IapMedicalAidVo): Observable<DialogueVo> {
    const data = <IapMedicalAidData>{
      iapMedicalAidVo: vo
      , iapMedicalAidVos: null
    };
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };
    return this.http.delete<DialogueVo>(`/service/iaps/forms/form-206/${formId}/ambulance`, options );
  }

  saveIapForm206AirAmbulance(formId, vo: IapMedicalAidVo): Observable<DialogueVo> {
    const data = <IapMedicalAidData>{
      iapMedicalAidVo: vo
      , iapMedicalAidVos: null
    };
    const params: HttpParams = new HttpParams()
      .set('id', formId)
      ;
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-206/air-ambulance`, data, {params: params} );
  }

  deleteIapForm206AirAmbulance(formId, vo: IapMedicalAidVo): Observable<DialogueVo> {
    const data = <IapMedicalAidData>{
      iapMedicalAidVo: vo
      , iapMedicalAidVos: null
    };
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };
    return this.http.delete<DialogueVo>(`/service/iaps/forms/form-206/${formId}/air-ambulance`, options );
  }

  saveIapForm206Hospital(formId, vo: IapHospitalVo): Observable<DialogueVo> {
    const data = <IapHospitalData>{
      iapHospitalVo: vo
      , iapHospitalVos: null
    };
    const params: HttpParams = new HttpParams()
      .set('id', formId)
      ;
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-206/hospitals`, data, {params: params} );
  }

  deleteIapForm206Hospital(formId, vo: IapHospitalVo): Observable<DialogueVo> {
    const data = <IapHospitalData>{
      iapHospitalVo: vo
      , iapHospitalVos: null
    };
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };
    return this.http.delete<DialogueVo>(`/service/iaps/forms/form-206/${formId}/hospitals`, options );
  }

  saveIapForm206AreaLocCap(formId, vo: IapAreaLocationCapabilityVo): Observable<DialogueVo> {
    const data = <IapAreaLocationCapabilityData>{
      iapAreaLocationCapabilityVo: vo
      , iapAreaLocationCapabilityVos: null
    };
    const params: HttpParams = new HttpParams()
      .set('id', formId)
      ;
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-206/area-location-cap`, data, {params: params} );
  }

  deleteIapForm206AreaLocCap(formId, vo: IapAreaLocationCapabilityVo): Observable<DialogueVo> {
    const data = <IapAreaLocationCapabilityData>{
        iapAreaLocationCapabilityVo: vo
      , iapAreaLocationCapabilityVos: null
    };
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };
    return this.http.delete<DialogueVo>(`/service/iaps/forms/form-206/${formId}/area-location-cap`, options );
  }

  saveIapForm206RemoteCampLoc(formId, vo: IapRemoteCampLocationsVo): Observable<DialogueVo> {
    const data = <IapRemoteCampLocationData>{
      iapRemoteCampLocationsVo: vo
      , iapRemoteCampLocationsVos: null
    };
    const params: HttpParams = new HttpParams()
      .set('id', formId)
      ;
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-206/remote-camp-location`, data, {params: params} );
  }

  deleteIapForm206RemoteCampLoc(formId, vo: IapRemoteCampLocationsVo): Observable<DialogueVo> {
    const data = <IapRemoteCampLocationData>{
      iapRemoteCampLocationsVo: vo
      , iapRemoteCampLocationsVos: null
    };
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };
    return this.http.delete<DialogueVo>(`/service/iaps/forms/form-206/${formId}/remote-camp-location`, options );
  }

  updateIapForm220(vo: IapForm220Vo): Observable<DialogueVo> {
    const id = vo.id;
    const data = <IapForm220Data>{
      iapForm220Vo: vo
    };
    return this.http.put<DialogueVo>(`/service/iaps/forms/form-220/${id}`, data );
  }

  saveIapForm220Helicopter(iapForm220Id, vo: IapAircraftVo): Observable<DialogueVo> {
    const data = <IapAircraftData>{
      iapAircraftVo: vo
      , iapAircraftVos: []
    };
    const params: HttpParams = new HttpParams()
      .set('id', iapForm220Id)
      ;

    return this.http.post<DialogueVo>(`/service/iaps/forms/form-220/helicopter`, data, { params: params} );
  }

  saveIapForm220HelicopterPositions(formId, vos: IapAircraftVo[]): Observable<DialogueVo> {
    const data = <IapAircraftData>{
      iapAircraftVo: null
      , iapAircraftVos: vos
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-220/positions/aircraft`, data );
  }

  deleteIapForm220Helicopter(vo: IapAircraftVo): Observable<DialogueVo> {
    const id = vo.id;
    const data = <IapAircraftData>{
      iapAircraftVo: vo
      , iapAircraftVos: null
      , dialogueVo: null
    };
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };

    return this.http.delete<DialogueVo>(`/service/iaps/forms/form-220/${id}/helicopter`, options);
  }

  saveIapForm220Task(iapForm220Id, vo: IapAircraftTaskVo): Observable<DialogueVo> {
    const data = <IapAircraftTaskData>{
      iapAircraftTaskVo: vo
      , iapAircraftTaskVos: []
    };
    const params: HttpParams = new HttpParams()
      .set('id', iapForm220Id)
      ;

    return this.http.post<DialogueVo>(`/service/iaps/forms/form-220/task`, data, { params: params} );
  }

  saveIapForm220TaskPositions(formId, vos: IapAircraftTaskVo[]): Observable<DialogueVo> {
    const data = <IapAircraftTaskData>{
      iapAircraftTaskVo: null
      , iapAircraftTaskVos: vos
    };
    return this.http.post<DialogueVo>(`/service/iaps/forms/form-220/positions/task`, data );
  }

  deleteIapForm220Task(vo: IapAircraftTaskVo): Observable<DialogueVo> {
    const id = vo.id;
    const data = <IapAircraftTaskData>{
      iapAircraftTaskVo: vo
      , iapAircraftTaskVos: null
      , dialogueVo: null
    };
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };

    return this.http.delete<DialogueVo>(`/service/iaps/forms/form-220/${id}/task`, options);
  }

  saveAttachment(id, iapAttachmentData) {
    return this.http.post<DialogueVo>(`/service/iaps/plans/${id}/attachments`, iapAttachmentData);
  }

  deleteAttachment(id) {
    return this.http.delete<DialogueVo>(`/service/iaps/plans/${id}/attachments`);
  }

}
