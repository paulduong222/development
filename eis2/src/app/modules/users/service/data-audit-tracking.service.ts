import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../../../data/dialogue/dialoguevo';
import { BaseUrlService } from '../../../service/base-url.service';
import { DataAuditTrackingFilter } from 'src/app/data/filter/data-audit-tracking-filter';

@Injectable()
export class DataAuditTrackingService {

  constructor(private http: HttpClient, private baseUrlService: BaseUrlService ) { }

  getGrid(filter: DataAuditTrackingFilter): Observable<DialogueVo> {
   // const filter = {};
   // const params: HttpParams = new HttpParams()
   //   .set('filter', {JSON.stringify(filter)});
    console.log('before call to /dataaudit/{filter}');
    return this.http.put<DialogueVo>(
      '/service/dataaudit',
      filter
    );
  }
}
