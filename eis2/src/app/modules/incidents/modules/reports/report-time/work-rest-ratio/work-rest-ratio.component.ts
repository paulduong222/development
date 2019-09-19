import { Component, OnInit, ViewChild } from '@angular/core';
import * as _ from 'lodash';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import { ReportTimeService } from 'src/app/service/report-time.service';
import { WorkRestRatioReportFilter } from 'src/app/data/filter/work-rest-ratio-report-filter';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
@Component({
  selector: 'app-work-rest-ratio',
  templateUrl: './work-rest-ratio.component.html',
  styleUrls: ['./work-rest-ratio.component.css']
})
export class WorkRestRatioComponent implements OnInit {
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  groupBy: any = 'groupByNone';
  sortBy: any = 'sectionSortByShiftStartDate';
  dateOrder: any = 'dateTypeAscending';
  resourceType: any = 'allResources';
  resourceIdSelected:any;
  requestNumbers:any = [];
  resourceNames:any = [];
  sections = [
    {value: 'sectionTypeAll', name: 'All', checked: true},
    {value: 'sectionTypeCommand', name: 'Command', checked: false},
    {value: 'sectionTypeOperations', name: 'Operations', checked: false},
    {value: 'sectionTypeFinance', name: 'Finance', checked: false},
    {value: 'sectionTypePlanning', name: 'Planning', checked: false},
    {value: 'sectionTypeLogistics', name: 'Logistics', checked: false},
    {value: 'sectionTypeExternal', name: 'External', checked: false}
  ];
  incident: any;
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  workRestRatioReportFilter: WorkRestRatioReportFilter = <WorkRestRatioReportFilter> {
    allResources: true,
    groupByNone: true,
    sectionTypeAll: false,
    sectionSortByShiftStartDate: false,
    dateTypeAscending: false,
    dateSortByRequestNumber: false,
    excludeDemob: true
  };
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
      this.workRestRatioReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.workRestRatioReportFilter.incidentId = 0;
    } else {
      this.workRestRatioReportFilter.incidentGroupId = 0;
      this.workRestRatioReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.workRestRatioReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.workRestRatioReportFilter.incidentId = 0;
        } else {
          this.workRestRatioReportFilter.incidentGroupId = 0;
          this.workRestRatioReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
        this.setData();
    });
    this.setData();
  }
  setData(){
    this.resourceIdSelected = -1;
    this.setRequestNumber(this.workRestRatioReportFilter.incidentId,this.workRestRatioReportFilter.incidentGroupId);
    this.setResources(this.workRestRatioReportFilter.incidentId,this.workRestRatioReportFilter.incidentGroupId);
  };

  setRequestNumber(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('NON_CONTRACTED_REQUEST_NUMBERS', incidentId, incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          if(obj.isPerson){
            datas.push({incidentResourceId: obj.incidentResourceId, resourceId: obj.resourceId, label: obj.label});
          }
        });
        // this.requestNumbers = this.sortArrayBy(datas, "label")
        this.requestNumbers = _.sortBy(datas,"label")
      }
    });
  }

  setResources(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('NON_CONTRACTED_PERSON_RESOURCE_NAMES', incidentId, incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          datas.push({incidentResourceId: obj.incidentResourceId, resourceId: obj.resourceId, label: obj.label});
        });
        // this.resourceNames = this.sortArrayBy(datas, "label")
        this.resourceNames = _.sortBy(datas,"label")
      }
    });
  }

  convertDate(dt) {
    return {
      dateString: + "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear(),
      timeString: ("0" + dt.getHours()).slice(-2)   + ":" + ("0" + dt.getMinutes()).slice(-2)
    }
  }

  checkboxHandler(index){
    if(index == 0){ //All
      this.sections.forEach(section=>{
        section.checked = false;
      });
    } else {
      this.sections[0].checked = false
    }
  }

  generateReport() {
    this.clearFilter()
    if (!this.dtStart.getFormattedDate()) {
      this.notificationService.showError2('Start Date is a required field', 'Error');
      return;
    }
    if (!this.dtEnd.getFormattedDate()) {
      this.notificationService.showError2('End Date is a required field', 'Error');
      return;
    }
    this.workRestRatioReportFilter.startDateVo = this.convertDate(new Date(this.dtStart.getFormattedDate()));
    this.workRestRatioReportFilter.endDateVo = this.convertDate(new Date(this.dtEnd.getFormattedDate()));
    this.workRestRatioReportFilter.resourceId = this.resourceIdSelected;
    this.workRestRatioReportFilter.allResources = false;
    this.workRestRatioReportFilter.specificResources = false;
    this.workRestRatioReportFilter.groupByNone = false;
    this.workRestRatioReportFilter.groupBySection = false;
    this.workRestRatioReportFilter.groupByDate = false;
    this.workRestRatioReportFilter[this.resourceType] = true;
    this.workRestRatioReportFilter[this.groupBy] = true;
    this.workRestRatioReportFilter[this.sortBy] = true;
    this.workRestRatioReportFilter[this.dateOrder] = true;
    this.sections.forEach(section=>{
      if(section.checked){
        this.workRestRatioReportFilter[section.value] = true
      }
    })
    this.reportTimeService.generateWorkRestRatioReport(this.workRestRatioReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }

  clearFilter(){
    Object.keys(this.workRestRatioReportFilter).forEach(key=>{
      if(typeof this.workRestRatioReportFilter[key] === "boolean" && key !== 'excludeDemob'){
        this.workRestRatioReportFilter[key] = false;
      }
    })
  }
}
