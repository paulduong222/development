import { Component, OnInit } from '@angular/core';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { ICS209ReportFilter } from 'src/app/data/filter/ics-209-report-filter';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-ics-report',
  templateUrl: './ics-report.component.html',
  styleUrls: ['./ics-report.component.css']
})
export class IcsReportComponent implements OnInit {
  incident: any;
  tooltipBox: boolean = false;
  ics209ReportFilter: ICS209ReportFilter = <ICS209ReportFilter>{};
  stateGrouping: any = 'stateGrouping';
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  constructor(
    public reportPlansService: ReportPlansService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.ics209ReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.ics209ReportFilter.incidentId = 0;
    } else {
      this.ics209ReportFilter.incidentGroupId = 0;
      this.ics209ReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.ics209ReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.ics209ReportFilter.incidentId = 0;
        } else {
          this.ics209ReportFilter.incidentGroupId = 0;
          this.ics209ReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
  }

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
  }

  generateReport() {
    this.ics209ReportFilter.stateGrouping = false;
    if (this.stateGrouping === 'stateGrouping') {
      this.ics209ReportFilter.stateGrouping = true;
    }
    this.reportPlansService.generateICS209Report(this.ics209ReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
