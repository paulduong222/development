import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { IapMasterFrequencyFilter } from 'src/app/data/filter/iap-master-frequency-filter';

@Injectable({
  providedIn: 'root'
})
export class IapMasterService {

  constructor(private http: HttpClient) { }

  getMasterFrequencyGrid(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params = new HttpParams()
    .set('incidentId', incidentId)
    .set('incidentGroupId', incidentGroupId);

    return this.http.get<DialogueVo>(`/service/iaps/master-frequencies/grid`, {params});
  }

}
