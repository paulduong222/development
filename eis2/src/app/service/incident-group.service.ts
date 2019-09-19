import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { IncidentGroupData } from '../data/rest/incident-group-data';
import { KindData } from '../data/rest/kind-data';
import { IncidentPrefsOtherFieldsVo } from '../data/incident-prefs-other-fields-vo';
import { IncidentPrefsOtherFieldsData } from '../data/rest/incident-prefs-other-fields-data';
import { IncidentGroupPrefsData } from '../data/rest/incident-group-prefs-data';
import { IncidentGroupPrefsVo } from '../data/incident-group-prefs-vo';
import { IncidentGroupQuestionData } from '../data/rest/incident-group-question-data';
import { IncidentGroupQuestionVo } from '../data/incident-group-question-vo';
import { CostSettingsData } from '../data/rest/cost-settings-data';
import { CostSettingsVo } from '../data/cost-settings-vo';

@Injectable({
  providedIn: 'root'
})
export class IncidentGroupService {

  constructor(private http: HttpClient) { }

  getById(groupId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incident-groups/${groupId}`);
  }

  getIapPositionItemCodes(groupId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incident-groups/${groupId}/iap-position-item-codes`);
  }

  saveGroup(igData: IncidentGroupData): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/incident-groups', igData);
  }

  getQSKinds(incidentGroupId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incident-groups/${incidentGroupId}/qs-kinds`);
  }

  saveQSKinds(incidentGroupId, kindVos): Observable<DialogueVo> {
    const kindData = <KindData>{
      kindVos: kindVos
      , dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/incident-groups/${incidentGroupId}/qs-kinds`, kindData);
  }

  getPrefsOtherFields(incidentGroupId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incident-groups/${incidentGroupId}/prefs/other`);
  }

  savePrefsOtherFields(incidentGroupId, incidentPrefsOtherFieldsVo: IncidentPrefsOtherFieldsVo): Observable<DialogueVo> {
    const incidentPrefsOtherFieldsData = <IncidentPrefsOtherFieldsData>{
      incidentPrefsOtherFieldsVo: incidentPrefsOtherFieldsVo
      , dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/incident-groups//prefs/other`, incidentPrefsOtherFieldsData);
  }

  getIncidentGroupCheckoutQuestions(incidentGroupId: number): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incident-groups/${incidentGroupId}/questions/checkout`);
  }

  saveIncidentGroupCheckoutQuestions(incidentGroupId: number, vos: IncidentGroupPrefsVo[]): Observable<DialogueVo> {
    const incidentGroupPrefsData = <IncidentGroupPrefsData>{
      incidentGroupPrefsVos: vos,
      dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/incident-groups/${incidentGroupId}/questions/checkout`, incidentGroupPrefsData);
  }

  getIncidentGroupAirTravelQuestions(incidentGroupId: number): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incident-groups/${incidentGroupId}/questions/airtravel`);
  }

  saveIncidentGroupAirTravelQuestions(incidentGroupId: number, vos: IncidentGroupQuestionVo[]): Observable<DialogueVo> {
    const incidentGroupQuestionData = <IncidentGroupQuestionData>{
      questionType: 'AIRTRAVEL',
      incidentGroupQuestionVos: vos,
      dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/incident-groups/${incidentGroupId}/questions`, incidentGroupQuestionData);
  }

  getIncidentDropdownList(incidentGroupId): Observable<DialogueVo>{
    return this.http.get<DialogueVo>(`/service/incident-groups/${incidentGroupId}/inc-dropdown-list`);
  }

  getIncidentGroupCostSettings(incidentGroupId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incident-groups/${incidentGroupId}/cost-settings`);
  }

  saveIncidentGroupCostSettings(incidentGroupId: number, vo: CostSettingsVo): Observable<DialogueVo> {
    const data = <CostSettingsData>{
      costSettingsVo: vo,
      dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/incident-groups/${incidentGroupId}/cost-settings`, data);
  }

}
