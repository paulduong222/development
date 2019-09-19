import { Injectable, Output, EventEmitter } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { BaseUrlService } from './base-url.service';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class ReferenceDataService {

  constructor(private http: HttpClient, private baseUrlService: BaseUrlService) { }

    getEventTypeList(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/eventtypes');
    }

    getOrgTypeList(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/orgtypes');
    }

    getStandardOrgList(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/standardOrgs');
    }

    getPdcList(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/pdcs');
    }

    getStateList(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/states');
    }

    getAgencyList(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/agencytypes');
    }

    getAgencyGroupList(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/agencygrouptypes');
    }

    getRegionList(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/regions');
    }

    getRateGroupList(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/rategrouptypes');
    }

    getKindSubData(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/kindsubtypes');
    }

    getRecommendationList(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/recommendations');
    }

    getComplexityList(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/complexities');
    }

    getResourceReferenceData(incidentId, incidentGroupId): Observable<DialogueVo> {
      const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId)
      ;
      return this.http.get<DialogueVo>('/service/refdata/resourceRefData', {params: params});
    }

    getResourceTimeReferenceData(): Observable<DialogueVo> {
      return this.http.get<DialogueVo>('/service/refdata/resourceTimeRefData');
    }

}
