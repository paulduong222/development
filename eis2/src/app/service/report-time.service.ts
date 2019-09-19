import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';
import { HttpParams, HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReportTimeService {
  constructor(private http: HttpClient) { }

  generateOF288TimeInvoiceReport(timeReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/time/of-288', timeReportFilter);
  }

  generateOF286TimeInvoiceReport(of286TimeInvoiceReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/time/of-286', of286TimeInvoiceReportFilter);
  }

  generateShiftsInExcessOfStandardHoursReport(shiftsInExcessOfStandardHoursReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/time/shifts-in-excess-of-standard-hours', shiftsInExcessOfStandardHoursReportFilter);
  }

  generatePersonnelTimeReport(personnelTimeReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/time/personnel', personnelTimeReportFilter);
  }

  generateWorkRestRatioReport(of286TimeInvoiceReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/time/work-rest-ratio', of286TimeInvoiceReportFilter);
  }

  generateSummaryHoursWorkedReport(summaryHoursWorkedReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/time/summary-hours-worked', summaryHoursWorkedReportFilter);
  }

  generateMissingDaysOfPostingsReport(missingDaysOfPostingsReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/time/missing-days-of-postings', missingDaysOfPostingsReportFilter);
  }

  generateCrewRosterReport(timeReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/time/crew-roster', timeReportFilter);
  }

  generateVendorResourceSummaryReport(vendorResourceSummaryReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/time/vendor-resource-summary',vendorResourceSummaryReportFilter);
  }

  getResourceListForSelectedIncident(listName, incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId)
      .set('listName', listName);
    return this.http.get<DialogueVo>('/service/reports/time/get-resource-list-for-selected-incident', {params: params});
  }

  getRequestNumbersForSelectedIncident(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId);
    return this.http.get<DialogueVo>('/service/reports/time/get-request-numbers-for-selected-incident', {params: params});
  }

  getAgencyResourcesForSelectedIncident(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId);
    return this.http.get<DialogueVo>('/service/reports/time/get-agency-resources-for-selected-incident', {params: params});
  }

  getResourceInvoiceList(resourceId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
      .set('resourceId', resourceId)
    return this.http.get<DialogueVo>('/service/reports/time/resource-invoice-list', {params: params});
  }

  deleteLastTimeInvoice(timeReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/time/delete-last-invoice', timeReportFilter);
  }

  getResourceData(incidentId, incidentGroupId){
    const params: HttpParams = new HttpParams()
      .set('incidentId', incidentId)
      .set('incidentGroupId', incidentGroupId);
    return this.http.get<any>('/service/reports/time/get-resource-data', {params: params});
  }
}
