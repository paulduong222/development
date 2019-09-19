import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { IncidentData } from '../data/rest/incident-data';
import { IncidentAccountCodeData } from '../data/rest/incident-account-code-data';
import { IncidentAccountCodeVo } from '../data/incident-account-code-vo';
import { KindData } from '../data/rest/kind-data';
import { IncidentPrefsOtherFieldsVo } from '../data/incident-prefs-other-fields-vo';
import { IncidentPrefsOtherFieldsData } from '../data/rest/incident-prefs-other-fields-data';
import { RestrictedIncidentUserData } from '../data/rest/restricted-incident-user-data';
import { RestrictedIncidentUserVo } from '../data/restricted-incident-user-vo';
import { IncidentPrefsVo } from '../data/incident-prefs-vo';
import { IncidentPrefsData } from '../data/rest/incident-prefs-data';
import { IncidentQuestionVo } from '../data/incident-question-vo';
import { IncidentQuestionData } from '../data/rest/incident-question-data';
import { CostSettingsVo } from '../data/cost-settings-vo';
import { CostSettingsData } from '../data/rest/cost-settings-data';
// import { IncidentGroupData } from '../data/rest/incident-group-data';

@Injectable({
  providedIn: 'root'
})
export class IncidentService {

  constructor(private http: HttpClient) { }

  getById(Id): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incidents/${Id}`);
  }

  getIapPositionItemCodes(Id): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incidents/${Id}/iap-position-item-codes`);
  }

  save(data: IncidentData): Observable<DialogueVo> {
    return this.http.post<DialogueVo>(`/service/incidents`, data);
  }

  delete(id): Observable<DialogueVo> {
    return this.http.delete<DialogueVo>(`/service/incidents/${id}`);
  }

  getIncidentAccountCode(id: number): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incidents/${id}/account-codes`);
  }

  saveIncidentAccountCode(incidentId: number, vo: IncidentAccountCodeVo, dvo: DialogueVo): Observable<DialogueVo> {
    const data = <IncidentAccountCodeData>{
      incidentAccountCodeVo: vo
      , dialogueVo: dvo
    };
    return this.http.post<DialogueVo>(`/service/incidents/${incidentId}/account-codes`, data);
  }

  deleteIncidentAccountCode(iacId: number): Observable<DialogueVo> {
    return this.http.delete<DialogueVo>(`/service/incidents/account-codes/${iacId}`);
  }

  getQSKinds(incidentId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incidents/${incidentId}/qskinds`);
  }

  saveQSKinds(incidentId, kindVos): Observable<DialogueVo> {
    const kindData = <KindData>{
      kindVos: kindVos
      , dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/incidents/${incidentId}/qskinds`, kindData);
  }

  getPrefsOtherFields(incidentId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incidents/${incidentId}/prefs-others`);
  }

  savePrefsOtherFields(incidentId, incidentPrefsOtherFieldsVo: IncidentPrefsOtherFieldsVo): Observable<DialogueVo> {
    const incidentPrefsOtherFieldsData = <IncidentPrefsOtherFieldsData>{
      incidentPrefsOtherFieldsVo: incidentPrefsOtherFieldsVo
      , dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/incidents/${incidentId}/prefs-others`, incidentPrefsOtherFieldsData);
  }

  getIncidentUserDefCheckin(userId, incidentId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incidents/${incidentId}/users/${userId}/defcheckindate`);
  }

  saveIncidentUserCheckin(vo: RestrictedIncidentUserVo): Observable<DialogueVo> {
    const restrictedIncidentUserData = <RestrictedIncidentUserData>{
      restrictedIncidentUserVo: vo
    };
    return this.http.post<DialogueVo>(`/service/incidents/users/checkin`, restrictedIncidentUserData);
  }

  getIncidentPrefs(incidentId: number): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incidents/${incidentId}/prefs`);
  }

  saveIncidentPrefs(incidentId: number, vos: IncidentPrefsVo[]): Observable<DialogueVo> {
    const incidentPrefsData = <IncidentPrefsData>{
      incidentPrefsVos: vos,
      dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/incidents/${incidentId}/prefs`, incidentPrefsData);
  }

  getIncidentAirTravelQuestions(incidentId: number): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incidents/${incidentId}/questions/airtravel`);
  }

  saveIncidentAirTravelQuestions(incidentId: number, vos: IncidentQuestionVo[]): Observable<DialogueVo> {
    const incidentQuestionData = <IncidentQuestionData>{
      questionType: 'AIRTRAVEL',
      incidentQuestionVos: vos,
      dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/incidents/${incidentId}/questions`, incidentQuestionData);
  }

  getIncidentCostSettings(incidentId): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/incidents/${incidentId}/cost-settings`);
  }

  saveIncidentCostSettings(incidentId: number, vo: CostSettingsVo): Observable<DialogueVo> {
    const data = <CostSettingsData>{
      costSettingsVo: vo,
      dialogueVo: null
    };
    return this.http.post<DialogueVo>(`/service/incidents/${incidentId}/cost-settings`, data);
  }

}
