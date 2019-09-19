import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';
import { HttpParams, HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReportPlansService {
  constructor(private http: HttpClient) { }

  generateICS209Report(ics209ReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/ics209', ics209ReportFilter);
  }

  generateAllIncidentResourcesReport(allIncidentResourcesReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/all-incident-resources', allIncidentResourcesReportFilter);
  }

  generateQualificationsReport(qualificationsReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/qualifications', qualificationsReportFilter);
  }

  generateStrikeTeamTaskForceReport(strikeTeamTaskForceReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/strike-team-task-force', strikeTeamTaskForceReportFilter);
  }

  generateCheckoutReport(checkoutReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/checkout', checkoutReportFilter);
  }

  generateLastWorkDayReport(lastWorkDayReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/last-work-day', lastWorkDayReportFilter);
  }

  generateDemobPlanningReport(demobPlanningReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/demob', demobPlanningReportFilter);
  }

  generateTentativeReleasePosterReport(tentativeReleasePosterReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/tentative', tentativeReleasePosterReportFilter);
  }

  generateAvailableForReleaseReport(availableForReleaseReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/available', availableForReleaseReportFilter);
  }

  generateAirTravelRequestReport(airTravelRequestReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/air-travel', airTravelRequestReportFilter);
  }

  generateActualDemobReport(availableForReleaseReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/actual-demob', availableForReleaseReportFilter);
  }

  generateGroundSupportReport(groundSupportReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/ground-support', groundSupportReportFilter);
  }

  generateGlidePathReport(glidePathReportFilter): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/reports/plans/glide-path', glidePathReportFilter);
  }

  getStrikeTeams(incidentId, incidentGroupId): Observable<DialogueVo> {
    const params = new HttpParams()
    .set('incidentId', incidentId)
    .set('incidentGroupId', incidentGroupId)
    return this.http.get<DialogueVo>('/service/incident-resources/strike-teams', {params});
  }
}
