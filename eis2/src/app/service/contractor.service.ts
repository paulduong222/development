import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ContractorVo } from '../data/contractor-vo';
import { ContractorData } from '../data/rest/contractor-data';
import { ContractorAgreementVo } from '../data/contractor-agreement-vo';
import { ContractorAgreementData } from '../data/rest/contractor-agreement-data';

@Injectable({
  providedIn: 'root'
})
export class ContractorService {

  constructor(private http: HttpClient) { }


  getGrid(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params = new HttpParams()
    .set('incidentId', incidentId)
    .set('incidentGroupId', incidentGroupId)
    return this.http.get<DialogueVo>(`/service/contractors/grid`, {params});
  }

  getById(id): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/contractors/${id}`);
  }

  saveAgreement(vo: ContractorAgreementVo, dvo: DialogueVo): Observable<DialogueVo>{
    const data = <ContractorAgreementData>{
      contractorAgreementVo: vo
      , dialogueVo: dvo
    };
    const contractorId = vo.contractorVo.id;
    return this.http.post<DialogueVo>(`/service/contractors/${contractorId}/agreement`, data);
  }

  deleteAgreement(vo: ContractorAgreementVo): Observable<DialogueVo> {
    const data = <ContractorAgreementData>{
      contractorAgreementVo: vo
      , dialogueVo: null
    };
    const contractorId = vo.contractorVo.id;
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };
    return this.http.delete<DialogueVo>(`/service/contractors/${contractorId}/agreement`, options);
  }

  saveContractor(vo: ContractorVo): Observable<DialogueVo> {
    const data = <ContractorData>{
      contractorVo: vo
      , dialogueVo: null
    };
    return this.http.post<DialogueVo>('/service/contractors', data);
  }

  delete(vo: ContractorVo): Observable<DialogueVo> {
    const data = <ContractorData>{
      contractorVo: vo
      , dialogueVo: null
    };
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };
    return this.http.delete<DialogueVo>(`/service/contractors`, options);
  }

}
