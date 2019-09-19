import { Injectable } from '@angular/core';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { Observable } from 'rxjs';
import { HttpParams, HttpClient } from '@angular/common/http';
import { DialogueData } from '../data/rest/dialogue-data';
import { TrainingSpecialistReportFilter } from '../data/filter/training-specialist-report-filter';
import { TrainingAssignmentsListReportFilter } from '../data/filter/training-assignments-list-report-filter';
import { IncidentTrainingSummaryReportFilter } from '../data/filter/incident-training-summary-report-filter';
import { HomeUnitContactLabelReportFilter } from '../data/filter/home-unit-contact-label-report-filter';

@Injectable({
  providedIn: 'root'
})
export class TrainingSpecialistReportService {

  constructor(private http: HttpClient) { }

  generateTrainingAssignmentsListReport(talReportFilter: TrainingAssignmentsListReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/training-specialists/reports/training-assignments', talReportFilter);
  }

  generateIncidentTrainingSummaryReport(itsReportFilter: IncidentTrainingSummaryReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/training-specialists/reports/incident-training-summary', itsReportFilter);
  }

  generateDataFormReport(tnspReportFilter: TrainingSpecialistReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/training-specialists/reports/data-form', tnspReportFilter);
  }

  generateEvaluatorFormReport(tnspReportFilter: TrainingSpecialistReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/training-specialists/reports/evaluator-form', tnspReportFilter);
  }

  generatePerformanceEvaluationReport(tnspReportFilter: TrainingSpecialistReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/training-specialists/reports/performance-evaluation', tnspReportFilter);
  }

  generateHomeUnitLetterReport(tnspReportFilter: TrainingSpecialistReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/training-specialists/reports/home-unit-letter', tnspReportFilter);
  }

  generateExitInterviewReport(tnspReportFilter: TrainingSpecialistReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/training-specialists/reports/exit-interview', tnspReportFilter);
  }

  generateHomeUnitContactLabelsReport(huclReportFilter: HomeUnitContactLabelReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/training-specialists/reports/home-unit-contact-labels', huclReportFilter);
  }

  getTrainingSpecialistList(incidentId, incidentGroupId, resourceTrainingId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
    .set('incidentId', incidentId)
    .set('incidentGroupId', incidentGroupId)
    .set('rtId', resourceTrainingId)
    return this.http.get<DialogueVo>('/service/training-specialists/reports/training-specialist-list', {params: params});
  }

  getEarliestStartDate(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
    .set('incidentId', incidentId)
    .set('incidentGroupId', incidentGroupId)
    return this.http.get<DialogueVo>('/service/training-specialists/reports/earliest-start-date', {params: params});
  }

  getHomeUnitContactGrid(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params: HttpParams = new HttpParams()
    .set('incidentId', incidentId)
    .set('incidentGroupId', incidentGroupId)
    return this.http.get<DialogueVo>('/service/training-specialists/reports//home-unit-contacts/grid', {params: params});

  }

}
