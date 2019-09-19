import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { GroundSupportReportFilter } from 'src/app/data/filter/ground-support-report-filter';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-ground-support-report',
  templateUrl: './ground-support-report.component.html',
  styleUrls: ['./ground-support-report.component.css']
})
export class GroundSupportReportComponent implements OnInit {
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  minDate = new Date();
  date;
  tooltipBox: boolean = false;
  incident: any;
  groundSupportReportFilter: GroundSupportReportFilter = <GroundSupportReportFilter>{};
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
      this.groundSupportReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.groundSupportReportFilter.incidentId = 0;
    } else {
      this.groundSupportReportFilter.incidentGroupId = 0;
      this.groundSupportReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.groundSupportReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.groundSupportReportFilter.incidentId = 0;
        } else {
          this.groundSupportReportFilter.incidentGroupId = 0;
          this.groundSupportReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
  }

  updateDate() {}

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
  }

  handleCheckbox(event) {
    if (this.groundSupportReportFilter[event.target.value]) {
      this.groundSupportReportFilter[event.target.value] = false;
    } else {
      this.groundSupportReportFilter[event.target.value] = true;
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
    } else if (new Date(this.dtStart.getFormattedDate()) > new Date(this.dtEnd.getFormattedDate())) {
      this.notificationService.showError2('End Date cannot be before Start Date', 'Error');
      return;
    }
    this.groundSupportReportFilter.startDate = new Date(this.dtStart.getFormattedDate());
    this.groundSupportReportFilter.endDate = new Date(this.dtEnd.getFormattedDate());
    this.reportPlansService.generateGroundSupportReport(this.groundSupportReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
