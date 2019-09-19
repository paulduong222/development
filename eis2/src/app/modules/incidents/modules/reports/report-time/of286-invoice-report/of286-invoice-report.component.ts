import { Component, OnInit, ViewChild } from '@angular/core';
import * as _ from 'lodash';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import { ReportTimeService } from 'src/app/service/report-time.service';
import { NotificationService } from 'src/app/service/notification-service';
import { OF286TimeInvoiceReportFilter } from 'src/app/data/filter/of-286-time_invoice_report_filter';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { TimeReportFilter } from 'src/app/data/filter/time-report-filter';
import { ModalService } from 'src/app/service/modal-service';

@Component({
  selector: 'app-of286-invoice-report',
  templateUrl: './of286-invoice-report.component.html',
  styleUrls: ['./of286-invoice-report.component.css']
})
export class Of286InvoiceReportComponent implements OnInit {
  @ViewChild('actualReleaseDate') actualReleaseDate: EisDatepickerComponent;
  @ViewChild('lastDateToIncludeOnReport') lastDateToIncludeOnReport: EisDatepickerComponent;
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  @ViewChild('promptModal1') promptModal1: PromptModalComponent;
  tableDef: TableDefinition;
  data: any = [];
  scrollAble: boolean = false;
  actualReleaseTime: any;
  indexSelected: any = [];
  timeReportFilter: TimeReportFilter = <TimeReportFilter> {}
  of286TimeInvoiceReportFilter: OF286TimeInvoiceReportFilter = <OF286TimeInvoiceReportFilter> {
    printDraftInvoice: false,
    printOriginalInvoice: false,
    printDuplicateOriginalInvoice: false,

    printInvoiceOnly: false,
    printDeductionsAndInvoice: false,
    printItemizedDeductionsOnly: false,
    finalInvoice: false
  };
  selectBy: any = 'selectByRequestNumber';
  incident: any;
  printOption: any = 'printDraftInvoice';
  reportOption: any = 'printDeductionsAndInvoice';
  requestNumbers: any = [];
  resourceIdSelected: any;
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  isAbleDelete: boolean = true;
  promptActionEvent: any;
  constructor(
    public reportTimeService: ReportTimeService,
    private notificationService: NotificationService,
    private incidentSelectorService: IncidentSelectorService,
    private modalService: ModalService
  ) {
    this.tableDef = {
      columns: [
          { field: 'invoiceNumber', title: 'Invoice Number', style: {'width':'165px'}},
          { field: 'firstDate', title: 'First Date', style: {'width':'135px'}},
          { field: 'lastDate', title: 'Last Date', style: {'width':'138px'}}
      ]
    };
  }
  ngOnInit() {
    let today = new Date();
    this.lastDateToIncludeOnReport.writeValue(today);
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.of286TimeInvoiceReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.of286TimeInvoiceReportFilter.incidentId = 0;
    } else {
      this.of286TimeInvoiceReportFilter.incidentGroupId = 0;
      this.of286TimeInvoiceReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.of286TimeInvoiceReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.of286TimeInvoiceReportFilter.incidentId = 0;
        } else {
          this.of286TimeInvoiceReportFilter.incidentGroupId = 0;
          this.of286TimeInvoiceReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
        this.setupData();
    });

    this.setupData();
    this.promptModal.promptTitle = 'OF-286';
    this.promptModal.promptMessage1 = 'Please select a resource';
    this.promptModal.button1Label = 'Ok';
    this.promptModal1.promptTitle = 'Time Reports';
    this.promptModal1.button1Label = 'Yes';
    this.promptModal1.button2Label = 'No';
    this.setDataGrid();
  }

  setDataGrid() {
      if (this.data.length < 6) {
        let bonusLenth = 6 - this.data.length;
        for (let index = 0; index < bonusLenth; index++) {
          this.data.push({})
        }
      }
  }

  selectedChange() {
    this.reportTimeService.getResourceInvoiceList(this.resourceIdSelected).subscribe(rs => {
      this.data = [];
      this.isAbleDelete = true;
      if (rs['resultObject'] && rs['resultObject'][0]) {
          this.lastDateToIncludeOnReport.writeValue(new Date());
          _.each(rs['resultObject'], obj => {
            let cur: any = {};
            this.promptModal1.promptMessage1 = `Deleting Invoice ${obj.invoiceNumber} will unlock all time postings included in the invoice. Do you want to continue?`;
            cur.invoiceNumber = obj.invoiceNumber;
            cur.firstDate = this.convertDateToString(new Date(obj.firstIncludeDate));
            cur.lastDate = this.convertDateToString(new Date(obj.lastIncludeDate));
            this.data.push(cur);
          });
          if (this.data.length < 6) {
            let bonusLenth = 6 - this.data.length;
            for (let index = 0; index < bonusLenth; index++) {
              this.data.push({})
            }
          }
          this.data = _.cloneDeep(this.data);
        } else {
          this.isAbleDelete = false;
          this.lastDateToIncludeOnReport.writeValue(new Date(String(rs.resultObjectAlternate)));
          if (this.data.length < 6) {
            let bonusLenth = 6 - this.data.length;
            for (let index = 0; index < bonusLenth; index++) {
              this.data.push({})
            }
          }
        }
    });
  }

  convertDateToString(dt) {
    console.log(dt);
    return "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear();
  }

  promptActionResult1(action) {
    if (action === 'Yes') {
      this.modalService.open('addReason');
    }
    this.promptModal1.closeModal();
  }

  cancelAddReason() {
    this.modalService.close('addReason');
  }

  deleteModalOpen() {
    if (!this.isAbleDelete) {
      this.notificationService.showError2('There are no invoices to be deleted.', 'Validation Error: 4 of 4');
      return;
    }
    this.promptModal1.openModal();
  }

  deleteLastTimeInvoice() {
    this.timeReportFilter.timeInvoiceId = this.resourceIdSelected;
    this.reportTimeService.deleteLastTimeInvoice(this.timeReportFilter).subscribe(rs => {
      this.notificationService.inspectResult(rs);
      this.modalService.close('addReason');
    });
  }

  getClickedRow(event, indexType, data, shiftIndex) {
    if (this[data][event.index] && !_.isEmpty(this[data][event.index], true)) {
      if (event.event.ctrlKey) {
        this[shiftIndex] = event.index;
        if (!this[indexType].includes(event.id)) {
          this.indexSelected.push(event.id);
        } else {
          this[indexType] = _.pull(this[indexType], event.id);
        }
      } else if (!event.event.shiftKey && !event.event.ctrlKey) {
        this[shiftIndex] = event.index;
        this[indexType] = [];
        if (!this[indexType].includes(event.id)) {
          this.indexSelected.push(event.id);
        }
      } else if (event.event.shiftKey) {
        this[indexType] = [];
        if (!this[shiftIndex] && this[shiftIndex] !== 0) {
          return;
        }
        if (this[shiftIndex] <= event.index) {
          for (let index = this[shiftIndex]; index <= event.index; index++) {
            this.indexSelected.push(this[data][index].id);
          }
        } else {
          for (let index = this[shiftIndex]; index >= event.index; index--) {
            this.indexSelected.push(this[data][index].id);
          }
        }
      }
      this[indexType] = _.cloneDeep(this[indexType]);
    }
  }

  setRequestNumber(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('CONTRACTED_REQUEST_NUMBERS', incidentId, incidentGroupId).subscribe(rs => {
      this.reportTimeService.getResourceListForSelectedIncident('CONTRACTED_RESOURCES', incidentId, incidentGroupId).subscribe(rs1 => {
        if (rs1['resultObject']) {
          let datas = [];
          let rsCopy =  _.orderBy(rs['resultObject'], "label");
          _.each(rsCopy, obj => {
            let data = {incidentResourceId: obj.incidentResourceId, resourceId: obj.resourceId, label: obj.label};
            let resource = _.find(rs1['resultObject'], {resourceId: obj.resourceId});
            data.label += ' ' + resource.label;
            datas.push(data);
          });
          this.requestNumbers = this.sortBy(datas, 'label');
          this.resourceIdSelected = _.head(this.requestNumbers).incidentResourceId;
          this.selectedChange();
        }
      });
    });
  }

  setResources(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('CONTRACTED_RESOURCES', incidentId, incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          datas.push({incidentResourceId: obj.incidentResourceId, resourceId: obj.incidentResourceId, label: obj.label});
        });
        this.requestNumbers = this.sortBy(datas, "label")
        this.resourceIdSelected = _.head(this.requestNumbers).incidentResourceId;
        this.selectedChange();
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

  changeSelectBy(e) {
    if (e.target.value === 'selectByResourceName') {
      this.setResources(this.of286TimeInvoiceReportFilter.incidentId, this.of286TimeInvoiceReportFilter.incidentGroupId);
    } else {
      this.setRequestNumber(this.of286TimeInvoiceReportFilter.incidentId, this.of286TimeInvoiceReportFilter.incidentGroupId);
    }
    // this.resourceIdSelected = '';
  }

  setupData() {
    if (this.selectBy === 'selectByResourceName') {
      this.setResources(this.of286TimeInvoiceReportFilter.incidentId, this.of286TimeInvoiceReportFilter.incidentGroupId);
    } else {
      this.setRequestNumber(this.of286TimeInvoiceReportFilter.incidentId, this.of286TimeInvoiceReportFilter.incidentGroupId);
    }
  }

  promptActionResult(action) {
    if (action === 'Ok' || action === 'No') {
      this.promptModal.closeModal();
    } else if(action === 'Yes') {
      this.promptModal.closeModal();
      this.reportTimeService.generateOF286TimeInvoiceReport(this.of286TimeInvoiceReportFilter).subscribe(data => {
        this.notificationService.inspectResult(data);
        if (data['resultObject']) {
          window.open(String(data['resultObject']), "_blank");
        }
      });
    }
  }

  generateReport() {
    if (!this.resourceIdSelected) {
      this.promptModal.openModal();
      return;
    }
    this.of286TimeInvoiceReportFilter.selectByRequestNumber = false;
    this.of286TimeInvoiceReportFilter.selectByResourceName = false;
    this.of286TimeInvoiceReportFilter.printDraftInvoice = false;
    this.of286TimeInvoiceReportFilter.printOriginalInvoice = false;
    this.of286TimeInvoiceReportFilter.printDuplicateOriginalInvoice = false;
    this.of286TimeInvoiceReportFilter.printDeductionsAndInvoice = false;
    this.of286TimeInvoiceReportFilter.printInvoiceOnly = false;
    this.of286TimeInvoiceReportFilter.printItemizedDeductionsOnly = false;
    this.of286TimeInvoiceReportFilter[this.selectBy] = true;
    this.of286TimeInvoiceReportFilter.incidentResourceId = this.resourceIdSelected;
    this.of286TimeInvoiceReportFilter[this.printOption] = true;
    this.of286TimeInvoiceReportFilter[this.reportOption] = true;
    delete this.of286TimeInvoiceReportFilter.actualReleaseDate;
    if (this.actualReleaseDate.getFormattedDate()) {
      this.of286TimeInvoiceReportFilter.actualReleaseDate = new Date(this.actualReleaseDate.getFormattedDate());
    }
    delete this.of286TimeInvoiceReportFilter.lastDateToIncludeOnReport;
    if (this.lastDateToIncludeOnReport.getFormattedDate()) {
      this.of286TimeInvoiceReportFilter.lastDateToIncludeOnReport = new Date(this.lastDateToIncludeOnReport.getFormattedDate());
    }

    if(this.printOption == 'printOriginalInvoice'){
      this.promptModal.promptTitle = 'Confirm Generate Original Invoice';
      this.promptModal.promptMessage1 = 'The time postings will be marked as invoiced and locked.';
      this.promptModal.promptMessage2 = 'This option will change the display of time postings in the grid to red and label them as invoiced.'
      this.promptModal.promptMessage3 = 'Do you want to continue?';
      this.promptModal.button1Label = 'Yes';
      this.promptModal.button2Label = 'No'
      this.promptModal.openModal();
      return;
    }

    this.reportTimeService.generateOF286TimeInvoiceReport(this.of286TimeInvoiceReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
