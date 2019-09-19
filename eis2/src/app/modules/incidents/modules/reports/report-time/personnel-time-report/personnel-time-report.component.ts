import { Component, OnInit, ViewChild } from '@angular/core';
import * as _ from 'lodash';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import { ReportTimeService } from 'src/app/service/report-time.service';
import { PersonnelTimeReportFilter } from 'src/app/data/filter/personnel-time-report-filter';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
@Component({
  selector: 'app-personnel-time-report',
  templateUrl: './personnel-time-report.component.html',
  styleUrls: ['./personnel-time-report.component.css']
})
export class PersonnelTimeReportComponent implements OnInit {
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  personnelTimeReportFilter: PersonnelTimeReportFilter = <PersonnelTimeReportFilter>{};
  incident: any;
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  agencies: any = [];
  constructor(
    public reportTimeService: ReportTimeService,
    private notificationService: NotificationService,
    private incidentSelectorService: IncidentSelectorService
  ) {}
  ngOnInit() {
    let today = new Date();
    this.dtEnd.writeValue(today);
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.personnelTimeReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.personnelTimeReportFilter.incidentId = 0;
    } else {
      this.personnelTimeReportFilter.incidentGroupId = 0;
      this.personnelTimeReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.personnelTimeReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.personnelTimeReportFilter.incidentId = 0;
        } else {
          this.personnelTimeReportFilter.incidentGroupId = 0;
          this.personnelTimeReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });

    this.reportTimeService.getAgencyResourcesForSelectedIncident(this.personnelTimeReportFilter.incidentId, this.personnelTimeReportFilter.incidentGroupId).subscribe(rs => {
      this.agencies = rs.resultObject;
    });
  }

  collapseOption(e, data) {
    if (!data.collapse) {
      data.collapse = false;
    }
    data.collapse = !data.collapse;
    e.preventDefault();
  }

  clickCheckBox(data) {
    if (!data.checked) {
      data.checked = true;
      this.checkAll(true, data);
    } else {
      data.checked = false;
      this.checkAll(false, data);
    }
  }

  checkAll(checked, data) {
    data.checked = checked;
    if (data.children && data.children.length > 0) {
      _.each(data.children, obj => {
        this.checkAll(checked, obj);
      });
    }
  }

  convertDate(dt) {
    return {
      dateString: + "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear(),
      timeString: ("0" + dt.getHours()).slice(-2)   + ":" + ("0" + dt.getMinutes()).slice(-2)
    }
  }

  generateReport() {
    this.personnelTimeReportFilter.resourceIds = [];
    if (this.dtStart.getFormattedDate()) {
      this.personnelTimeReportFilter.startDateVo = this.convertDate(new Date(this.dtStart.getFormattedDate()));
    }
    if (this.dtEnd.getFormattedDate()) {
      this.personnelTimeReportFilter.endDateVo = this.convertDate(new Date(this.dtEnd.getFormattedDate()));
    }
    this.reportTimeService.generatePersonnelTimeReport(this.personnelTimeReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
