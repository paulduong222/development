import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { HttpClient, HttpParams } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class CostAccrualsService {

  constructor(private http: HttpClient) { }

  getGrid(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params = new HttpParams()
    .set('incidentId', incidentId)
    .set('page', incidentGroupId)
    return this.http.get<DialogueVo>(`/service/cost-accruals/grid`, {params});
  }
}
