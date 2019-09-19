import { Component, OnInit, ViewChild } from '@angular/core';
import * as _ from 'lodash';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import { ReportTimeService } from 'src/app/service/report-time.service';
import { NotificationService } from 'src/app/service/notification-service';
import { SummaryHoursWorkedReportFilter } from 'src/app/data/filter/summary-hours-worked-report-filter';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-summary-of-hours-worked',
  templateUrl: './summary-of-hours-worked.component.html',
  styleUrls: ['./summary-of-hours-worked.component.css']
})
export class SummaryOfHoursWorkedComponent implements OnInit {
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  @ViewChild('startDate') startDate: EisDatepickerComponent;
  @ViewChild('endDate') endDate: EisDatepickerComponent;
  incident: any;
  resourceIdSelected:any;
  requestNumbers:any = [];
  resourceNames:any = [];
  sortBy: string = 'isSortByShifStartDate';
  allResourcesOrSpecificResources: string = 'isAllResources';
  noneOrSection: string = 'isNonGroupBy'
  summaryHoursWorkedReportFilter: SummaryHoursWorkedReportFilter = <SummaryHoursWorkedReportFilter>{
    sections: [],
    incidentIds: [],

    isNonGroupBy: true,
    isSection: false,
    isAllResources: true,
    isSpecificResources: false,
    isSortByShifStartDate: false,
    isSortByRequestNum: false,
    isSortByResourceName: false,
    isExcludeDemob: true
  };
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  sections = [
    {id: 0, value: "COMMAND", checked: false},
    {id: 1, value: "EXTERNAL", checked: false},
    {id: 2, value: "FINANCE", checked: false},
    {id: 3, value: "LOGISTICS", checked: false},
    {id: 4, value: "OPERATIONS", checked: false},
    {id: 5, value: "PLANS", checked: false},
  ]
  constructor(
    public reportTimeService: ReportTimeService,
    private notificationService: NotificationService,
    private incidentSelectorService: IncidentSelectorService
  ) {}

  ngOnInit() {
    this.incident = this.incidentSelectorService.selectedGridRow;
    this.summaryHoursWorkedReportFilter.incidentName = this.incident.name;
    this.summaryHoursWorkedReportFilter.incidentIds = [];
    if (this.incident.incidentGroupId) {
      this.summaryHoursWorkedReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.summaryHoursWorkedReportFilter.incidentTag = '';
      this.summaryHoursWorkedReportFilter.incidentId = 0;
      this.incident.children.forEach(child =>{
        this.summaryHoursWorkedReportFilter.incidentIds.push(child.id)
      });
    } else {
      this.summaryHoursWorkedReportFilter.incidentGroupId = 0;
      this.summaryHoursWorkedReportFilter.incidentTag = this.summaryHoursWorkedReportFilter.incidentNumber = this.incident.incidentNumber;
      this.summaryHoursWorkedReportFilter.incidentId = this.incident.id;
      this.summaryHoursWorkedReportFilter.incidentIds.push(this.incident.id)
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
      this.summaryHoursWorkedReportFilter.incidentIds = [];
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        this.summaryHoursWorkedReportFilter.incidentName = this.currentSelectedIncidentSelectorVo.name;
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.summaryHoursWorkedReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.summaryHoursWorkedReportFilter.incidentTag = '';
          this.summaryHoursWorkedReportFilter.incidentId = 0;
          this.currentSelectedIncidentSelectorVo.children.forEach(child => {
            this.summaryHoursWorkedReportFilter.incidentIds.push(child.id)
          });
        } else {
          this.summaryHoursWorkedReportFilter.incidentGroupId = 0;
          this.summaryHoursWorkedReportFilter.incidentTag = this.summaryHoursWorkedReportFilter.incidentNumber = this.currentSelectedIncidentSelectorVo.incidentNumber;
          this.summaryHoursWorkedReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
          this.summaryHoursWorkedReportFilter.incidentIds.push(this.currentSelectedIncidentSelectorVo.id)
          this.setData();
        }
    });
    this.setData();
    this.promptModal.promptTitle = 'Crew Roster';
    this.promptModal.button1Label = 'Ok';
  }

  setData(){
    this.resourceIdSelected = -1;
    this.setRequestNumber(this.summaryHoursWorkedReportFilter.incidentId,this.summaryHoursWorkedReportFilter.incidentGroupId);
    this.setResources(this.summaryHoursWorkedReportFilter.incidentId,this.summaryHoursWorkedReportFilter.incidentGroupId);
  };

  setRequestNumber(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('CREW_REQUEST_NUMBERS', incidentId, incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          datas.push({resourceId: obj.incidentResourceId, label: obj.label});
        });
        this.requestNumbers = this.sortArrayBy(datas, "label")
      }
    });
  }

  setResources(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('CREW_NAMES', incidentId, incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          datas.push({resourceId: obj.incidentResourceId, label: obj.label});
        });
        this.resourceNames = this.sortArrayBy(datas, "label")
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

  promptActionResult(action){
    if (action === 'Ok') {
      this.promptModal.closeModal();
    }
  }

  convertDate(dt) {
    return {
      dateString: + "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear(),
      timeString: ("0" + dt.getHours()).slice(-2)   + ":" + ("0" + dt.getMinutes()).slice(-2)
    }
  }

  generateReport() {
    this.clearFilter();
    if (!this.startDate.getFormattedDate()) {
      this.notificationService.showError2('Start Date is a required field', 'Error');
      return;
    }
    if (!this.endDate.getFormattedDate()) {
      this.notificationService.showError2('End Date is a required field', 'Error');
      return;
    }
    if (new Date(this.startDate.getFormattedDate()) > new Date(this.endDate.getFormattedDate())) {
      this.notificationService.showError2('End Date cannot be before Start Date', 'Error');
      return;
    }
    this.sections.forEach(section=>{
      if(section.checked){
        this.summaryHoursWorkedReportFilter.sections.push(section.value)
      }else{
        delete this.summaryHoursWorkedReportFilter.sections[section.id]
      }
    })
    this.summaryHoursWorkedReportFilter[this.sortBy] = true;
    this.summaryHoursWorkedReportFilter[this.allResourcesOrSpecificResources] = true;
    if(this.summaryHoursWorkedReportFilter[this.noneOrSection]){
      this.summaryHoursWorkedReportFilter.isNonGroupBy = true;
      delete this.summaryHoursWorkedReportFilter.sections
    }
    this.summaryHoursWorkedReportFilter.startDateVo = this.convertDate(new Date(this.startDate.getFormattedDate()));
    this.summaryHoursWorkedReportFilter.endDateVo = this.convertDate(new Date(this.endDate.getFormattedDate()));

    if(this.summaryHoursWorkedReportFilter.isSpecificResources){
      if(!this.resourceIdSelected || this.resourceIdSelected == -1){
        this.promptModal.promptMessage1 = 'Request Number is a required field'
        this.promptModal.openModal();
      } else{
        this.requestNumbers.forEach(rq => {
          if(this.resourceIdSelected == rq.resourceId){
            this.summaryHoursWorkedReportFilter.requestNumber = rq.label
          }
        });
        this.resourceNames.forEach(rs => {
          if(this.resourceIdSelected == rs.resourceId){
            this.summaryHoursWorkedReportFilter.resourceName = rs.label
          }
        });
      }
    }

    this.summaryHoursWorkedReportFilter.printDraftInvoice = false;
    this.summaryHoursWorkedReportFilter.printOriginalInvoice = false;
    this.summaryHoursWorkedReportFilter.printDuplicateOriginalInvoice = false;
    this.summaryHoursWorkedReportFilter.printDeductionsAndInvoice = false;
    this.summaryHoursWorkedReportFilter.printInvoiceOnly = false;
    this.summaryHoursWorkedReportFilter.printItemizedDeductionsOnly = false;

    this.reportTimeService.generateSummaryHoursWorkedReport(this.summaryHoursWorkedReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
  clearFilter(){
    Object.keys(this.summaryHoursWorkedReportFilter).forEach(key=>{
      if(typeof this.summaryHoursWorkedReportFilter[key] === "boolean" && key !== 'isExcludeDemob'){
        this.summaryHoursWorkedReportFilter[key] = false;
      }
    })
    this.summaryHoursWorkedReportFilter.sections = []
  }
}
