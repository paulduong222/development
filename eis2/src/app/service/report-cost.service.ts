import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';
import { HttpParams, HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReportCostService {
  constructor(private http: HttpClient) { }

  generateGroupCategorySummaryReport(body): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/costs/group-category-summary', body);
  }

  generateSummaryByResourceReport(body): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/costs/summary-by-resource', body);
  }

  generateSummaryForCurrentDayReport(body): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/costs/summary-for-current-day', body);
  }

  generateDetailByResourceReport(body): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/costs/details-by-resource', body);
  }

  generateGroupCategoryTotalReport(body): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/costs/group-category-total-by-filter', body);
  }

  generateAircraftDetailReport(body): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/costs/aircraft-detail', body);
  }

  generateAnalysisReport(body): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/costs/analysis', body);
  }

  generateCostShareReport(body): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/costs/cost-share', body);
  }

  getGroupCategoryTotalFilter(id, filternName, isIncidentGroup): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('id', id)
      .set('filternName', filternName)
      .set('isIncidentGroup', isIncidentGroup);
    return this.http.post<DialogueVo>('/service/reports/costs/group-category-total', params);
  }
}
