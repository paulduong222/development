import { Component, OnInit, ViewChild } from '@angular/core';
import * as _ from 'lodash';
import { ReportTimeService } from 'src/app/service/report-time.service';
import { ShiftsInExcessOfStandardHoursReportFilter } from 'src/app/data/filter/shifts-in-excess-of-standard-hours-report-filter';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';

@Component({
  selector: 'app-shifts-in-excess-of-standard-hours',
  templateUrl: './shifts-in-excess-of-standard-hours.component.html',
  styleUrls: ['./shifts-in-excess-of-standard-hours.component.css']
})
export class ShiftsInExcessOfStandardHoursComponent implements OnInit {
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  shiftsInExcessOfStandardHoursReportFilter: ShiftsInExcessOfStandardHoursReportFilter = <ShiftsInExcessOfStandardHoursReportFilter>{
    sortBy: 'REQUEST_NUMBER',
    excludeDemob: true
  };
  incident: any;
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  selectBy: any = 'isCrew';
  requestNumbers:any = [];
  resourceNames:any = [];
  resourceIdSelected: any;
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
      this.shiftsInExcessOfStandardHoursReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.shiftsInExcessOfStandardHoursReportFilter.incidentId = 0;
    } else {
      this.shiftsInExcessOfStandardHoursReportFilter.incidentGroupId = 0;
      this.shiftsInExcessOfStandardHoursReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.shiftsInExcessOfStandardHoursReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.shiftsInExcessOfStandardHoursReportFilter.incidentId = 0;
        } else {
          this.shiftsInExcessOfStandardHoursReportFilter.incidentGroupId = 0;
          this.shiftsInExcessOfStandardHoursReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
    this.setData();
  }

  setData(){
    this.resourceIdSelected = -1;
    this.setRequestNumber(this.shiftsInExcessOfStandardHoursReportFilter.incidentId,this.shiftsInExcessOfStandardHoursReportFilter.incidentGroupId);
    this.setResources(this.shiftsInExcessOfStandardHoursReportFilter.incidentId,this.shiftsInExcessOfStandardHoursReportFilter.incidentGroupId);
  };

  setRequestNumber(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('PERSON_REQUEST_NUMBERS', incidentId, incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          datas.push({resourceId: obj.incidentResourceId, label: obj.label});
        });
        this.requestNumbers = this.sortArrayBy(datas, "label")
      }
    });
  }

  sortArrayBy(array, field) {
    return array.sort(function (a, b) {
      let aStr = a[field].split("-")[0];
      let bStr = b[field].split("-")[0];
      let aStrF = a[field].split(/[-, ,.]+/)[1];
      let bStrF = b[field].split(/[-, ,.]+/)[1];
      if (aStr > bStr) {
        return 1;
      }
      if (aStr < bStr) {
        return -1;
      }

      if (aStrF.length > bStrF.length) {
        return 1;
      }
      if (aStrF.length < bStrF.length) {
        return -1;
      }
      if (aStrF >= bStrF) {
        return 1;
      }
      return -1;
    });
  }

  setResources(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('PERSON_RESOURCE_NAMES', incidentId, incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          datas.push({resourceId: obj.incidentResourceId, label: obj.label});
        });
        this.resourceNames = this.sortArrayBy(datas, "label")
      }
    });
  }

  convertDate(dt) {
    return {
      dateString: + "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear(),
      timeString: ("0" + dt.getHours()).slice(-2)   + ":" + ("0" + dt.getMinutes()).slice(-2)
    }
  }

  generateReport() {
    if (this.shiftsInExcessOfStandardHoursReportFilter.standardHours) {
      let standardHours = parseFloat(this.shiftsInExcessOfStandardHoursReportFilter.standardHours);
      if(isNaN(standardHours)) {
        this.shiftsInExcessOfStandardHoursReportFilter.standardHours = '';
      }
    }
    this.shiftsInExcessOfStandardHoursReportFilter.isCrew = false;
    this.shiftsInExcessOfStandardHoursReportFilter.isCrew = false;
    this.shiftsInExcessOfStandardHoursReportFilter[this.selectBy] = true;
    if (this.dtStart.getFormattedDate()) {
      this.shiftsInExcessOfStandardHoursReportFilter.firstDateToIncludeOnReportVo = this.convertDate(new Date(this.dtStart.getFormattedDate()));
    }
    if (this.dtEnd.getFormattedDate()) {
      this.shiftsInExcessOfStandardHoursReportFilter.lastDateToIncludeOnReportVo = this.convertDate(new Date(this.dtEnd.getFormattedDate()));
    }
    this.reportTimeService.generateShiftsInExcessOfStandardHoursReport(this.shiftsInExcessOfStandardHoursReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
