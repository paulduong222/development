import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ContractorRateVo } from '../data/contractor-rate-vo';
import { WorkPeriodVo } from '../data/work-period-vo';
import { ContractorRateData } from '../data/rest/contractor-rate-data';

@Injectable({
  providedIn: 'root'
})
export class ContractorRateService {

  constructor(private http: HttpClient) { }

  saveRate(vo: ContractorRateVo, wpvo: WorkPeriodVo, dvo: DialogueVo): Observable<DialogueVo>{
    const data = <ContractorRateData>{
      contractorRateVo: vo
      , workPeriodVo: wpvo
      , contractorRateFilter: null
      , dialogueVo: dvo
    };
    return this.http.post<DialogueVo>(`/service/contractor-rates`, data);
  }

  deleteRate(id, assignmentTimeId, contractorPaymentInfoId): Observable<DialogueVo> {
    const params = new HttpParams()
    .set('assignmentTimeId', assignmentTimeId)
    .set('contractorPaymentInfoId', contractorPaymentInfoId)
    return this.http.delete<DialogueVo>(`/service/contractor-rates/${id}`, {params: params});
  }
}
