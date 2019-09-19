import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportCostComponent } from '../report-cost.component';
import * as _ from 'lodash';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { ReportCostService } from 'src/app/service/report-cost.service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { AircraftDetailReportFilter } from 'src/app/data/filter/aircraft-detail-report-filter';
import { ItemCodeService } from 'src/app/service/item-code.service';

@Component({
  selector: 'app-aircraft-detail-report',
  templateUrl: './aircraft-detail-report.component.html',
  styleUrls: ['./aircraft-detail-report.component.css',
              '../report-cost.component.css']
})
export class AircraftDetailReportComponent extends ReportCostComponent implements OnInit {

  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  aircraftDetailReportFilter: AircraftDetailReportFilter = <AircraftDetailReportFilter>{
    isIncludeAllAircraftType: true
  };
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  incident: any;
  filterBy: any = 'isReportOnly';
  additionalFilter: any = 'isIncludeAllAircraftType';
  aircraftTypes: any = [];
  listCheckBox: any;
  listCheckBoxAble = ['isSelectiveAircraftTypes'];
  constructor(
    public reportCostService: ReportCostService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService,
    private itemCodeService: ItemCodeService
  ) {
    super();
  }

  ngOnInit() {
    super.ngOnInit();
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.aircraftDetailReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.aircraftDetailReportFilter.incidentId = 0;
    } else {
      this.aircraftDetailReportFilter.incidentGroupId = 0;
      this.aircraftDetailReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.aircraftDetailReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.aircraftDetailReportFilter.incidentId = 0;
        } else {
          this.aircraftDetailReportFilter.incidentGroupId = 0;
          this.aircraftDetailReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
        this.setDataList();
    });
    this.setDataList();
  }

  setDataList() {
    this.additionalFilter = 'isIncludeAllAircraftType';
    this.aircraftTypes = [];
    this.itemCodeService.getGrid(this.aircraftDetailReportFilter.incidentId, this.aircraftDetailReportFilter.incidentGroupId, false).subscribe(rs => {  
      let datas = _.filter(rs.recordset, {aircraft: true, dailyFormVo: {code: 'A'}});
      let dataList = [];
      _.each(datas, data => {
        dataList.push({label: data.code + ' - ' + data.description, id: data.code})
      });
      this.listCheckBox = _.cloneDeep(dataList);
    });
  }

  convertDate(dt) {
    return {
      dateString: + "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear(),
      timeString: ("0" + dt.getHours()).slice(-2)   + ":" + ("0" + dt.getMinutes()).slice(-2)
    }
  }

  handleCheckbox(label) {
    if (this.aircraftTypes.includes(label)) {
      _.pull(this.aircraftTypes, label);
    } else {
      this.aircraftTypes.push(label);
    }
  }

  generateReport() {
    this.aircraftDetailReportFilter.aircraftTypes = [];
    if (this.listCheckBoxAble.includes(this.additionalFilter)) {
      this.aircraftDetailReportFilter.aircraftTypes = this.aircraftTypes;
    }
    if (this.aircraftDetailReportFilter.isDateRangeSelected) {
      if (this.dtStart.getFormattedDate()) {
        this.aircraftDetailReportFilter.startDateVo = this.convertDate(new Date(this.dtStart.getFormattedDate()));
      }
      if (this.dtEnd.getFormattedDate()) {
        this.aircraftDetailReportFilter.endDateVo = this.convertDate(new Date(this.dtEnd.getFormattedDate()));
      }
    }
    
    this.aircraftDetailReportFilter = _.mapValues(this.aircraftDetailReportFilter, (obj) => {
      if (typeof(obj) == 'boolean') {
        return false;
      }
      return obj;
    });

    if (this.aircraftDetailReportFilter.incidentGroupId > 0) {
      this.aircraftDetailReportFilter.isIncidentGroup = true;
    }

    if (this.additionalFilter && this.additionalFilter !== 'subCategory') {
      this.aircraftDetailReportFilter[this.additionalFilter] = true;
    }
    this.aircraftDetailReportFilter[this.filterBy] = true;
    this.reportCostService.generateAircraftDetailReport(this.aircraftDetailReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }

}
