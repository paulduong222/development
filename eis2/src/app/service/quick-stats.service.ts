import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';

@Injectable({
  providedIn: 'root'
})
export class QuickStatsService {

  constructor(private http: HttpClient) { }

  getQuickStats(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId);
    return this.http.get<DialogueVo>('/service/quick-stats', {params: params});
  }

}
