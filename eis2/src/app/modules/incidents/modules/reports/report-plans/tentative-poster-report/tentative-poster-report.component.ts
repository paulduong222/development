import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { TentativeReleasePosterReportFilter } from 'src/app/data/filter/tentative-release-roster-report-filter';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-tentative-poster-report',
  templateUrl: './tentative-poster-report.component.html',
  styleUrls: ['./tentative-poster-report.component.css']
})
export class TentativePosterReportComponent implements OnInit {
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  date;
  incident: any;
  tooltipBox: boolean = false;
  tentativeReleasePosterReportFilter: TentativeReleasePosterReportFilter = <TentativeReleasePosterReportFilter>{};
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
      this.tentativeReleasePosterReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.tentativeReleasePosterReportFilter.incidentId = 0;
    } else {
      this.tentativeReleasePosterReportFilter.incidentGroupId = 0;
      this.tentativeReleasePosterReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.tentativeReleasePosterReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.tentativeReleasePosterReportFilter.incidentId = 0;
        } else {
          this.tentativeReleasePosterReportFilter.incidentGroupId = 0;
          this.tentativeReleasePosterReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
  }

  updateDate() {}

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
  }

  handleCheckbox() {
    if (this.tentativeReleasePosterReportFilter.includeSTTFComponents) {
      this.tentativeReleasePosterReportFilter.includeSTTFComponents = false;
    } else {
      this.tentativeReleasePosterReportFilter.includeSTTFComponents = true;
    }
  }

  generateReport() {
    let errMsg = '';
    if (!this.dtStart.getFormattedDate()) {
      errMsg += 'Start Date is a required field.'
    }
    if (!this.dtEnd.getFormattedDate()) {
      errMsg += errMsg ? '<br>' : '';
      errMsg += 'End Date is a required field.'
    }
    if (errMsg) {
      this.notificationService.showError2(errMsg, 'Error');
      return;
    }
    this.tentativeReleasePosterReportFilter.startDate = new Date(this.dtStart.getFormattedDate());
    this.tentativeReleasePosterReportFilter.endDate = new Date(this.dtEnd.getFormattedDate());
    this.reportPlansService.generateTentativeReleasePosterReport(this.tentativeReleasePosterReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
