import { Component, OnInit, ViewChild } from '@angular/core';
import * as _ from 'lodash';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import { ReportTimeService } from 'src/app/service/report-time.service';
import { NotificationService } from 'src/app/service/notification-service';
import { TimeReportFilter } from 'src/app/data/filter/time-report-filter';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-crew-roster',
  templateUrl: './crew-roster.component.html',
  styleUrls: ['./crew-roster.component.css']
})
export class CrewRosterComponent implements OnInit {
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  incident: any;
  resourceIdSelected:any;
  requestNumbers:any = [];
  resourceNames:any = [];
  timeReportFilter: TimeReportFilter = <TimeReportFilter>{
    printDraftInvoice: false,
    printOriginalInvoice: false,
    printDuplicateOriginalInvoice: false,

    printInvoiceOnly: false,
    printDeductionsAndInvoice: false,
    printItemizedDeductionsOnly: false,
    finalInvoice: false
  }
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  constructor(
    public reportTimeService: ReportTimeService,
    private notificationService: NotificationService,
    private incidentSelectorService: IncidentSelectorService
  ) {}
  ngOnInit() {
    this.incident = this.incidentSelectorService.selectedGridRow;
    this.timeReportFilter.incidentName = this.incident.name;
    this.timeReportFilter.excludeDemob = true;
    if (this.incident.incidentGroupId) {
      this.timeReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.timeReportFilter.incidentTag = '';
      this.timeReportFilter.incidentId = 0;
    } else {
      this.timeReportFilter.incidentGroupId = 0;
      this.timeReportFilter.incidentTag = this.timeReportFilter.incidentNumber = this.incident.incidentNumber;
      this.timeReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        this.timeReportFilter.incidentName = this.currentSelectedIncidentSelectorVo.name;
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.timeReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.timeReportFilter.incidentTag = '';
          this.timeReportFilter.incidentId = 0;
        } else {
          this.timeReportFilter.incidentGroupId = 0;
          this.timeReportFilter.incidentTag = this.timeReportFilter.incidentNumber = this.currentSelectedIncidentSelectorVo.incidentNumber;
          this.timeReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
        this.setData()
        console.log(this.resourceIdSelected);
    });
    this.setData()
    this.promptModal.promptTitle = 'Crew Roster';
    this.promptModal.promptMessage1 = 'A Request Number or a Resource Name must be selected.';
    this.promptModal.button1Label = 'Ok';
  }

  setData(){
    this.setRequestNumber(this.timeReportFilter.incidentId,this.timeReportFilter.incidentGroupId);
    this.setResources(this.timeReportFilter.incidentId,this.timeReportFilter.incidentGroupId);
  }
  setRequestNumber(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('CREW_REQUEST_NUMBERS', incidentId, incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          datas.push({incidentResourceId: obj.incidentResourceId, resourceId: obj.resourceId, label: obj.label});
        });
        this.requestNumbers = this.sortBy(datas, 'label')
        // this.resourceIdSelected = _.head(datas).resourceId
      }
    });
  }

  setResources(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('CREW_NAMES', incidentId, incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          datas.push({incidentResourceId: obj.incidentResourceId, resourceId: obj.resourceId, label: obj.label});
        });
        this.resourceNames = this.sortBy(datas, "label")
      }
    });
  }

  sortBy(array, field) {
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

  promptActionResult(action) {
    if (action === 'Ok') {
      this.promptModal.closeModal();
    }
  }

  generateReport() {
    if (!this.resourceIdSelected || this.resourceIdSelected === '') {
      this.promptModal.openModal();
      return;
    }
    else{
      this.requestNumbers.forEach(rq => {
        if(this.resourceIdSelected == rq.resourceId){
          this.timeReportFilter.requestNumber = rq.label
        }
      });
      this.resourceNames.forEach(rs => {
        if(this.resourceIdSelected == rs.resourceId){
          this.timeReportFilter.resourceName = rs.label
        }
      });
    }
    this.timeReportFilter.printDraftInvoice = false;
    this.timeReportFilter.printOriginalInvoice = false;
    this.timeReportFilter.printDuplicateOriginalInvoice = false;
    this.timeReportFilter.printDeductionsAndInvoice = false;
    this.timeReportFilter.printInvoiceOnly = false;
    this.timeReportFilter.printItemizedDeductionsOnly = false;
    this.timeReportFilter.resourceId = this.resourceIdSelected;
    this.reportTimeService.generateCrewRosterReport(this.timeReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data)
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
