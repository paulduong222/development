import { Component, OnInit, ViewChild } from '@angular/core';
import * as _ from 'lodash';
import { ReportTimeService } from 'src/app/service/report-time.service';
import { TimeReportFilter } from 'src/app/data/filter/time-report-filter';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import { ModalService } from 'src/app/service/modal-service';
@Component({
  selector: 'app-of288-invoice-report',
  templateUrl: './of288-invoice-report.component.html',
  styleUrls: ['./of288-invoice-report.component.css']
})
export class Of288InvoiceReportComponent implements OnInit {
  @ViewChild('lastDateToIncludeOnInvoice') lastDateToIncludeOnInvoice: EisDatepickerComponent;
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  @ViewChild('promptModal1') promptModal1: PromptModalComponent;
  @ViewChild('promptModal2') promptModal2: PromptModalComponent;
  tableDef: TableDefinition;
  data: any = [];
  scrollAble: boolean = false;
  indexSelected: any = [];
  timeReportFilter: TimeReportFilter = <TimeReportFilter>{
    printDraftInvoice: false,
    printOriginalInvoice: false,
    printDuplicateOriginalInvoice: false,

    printInvoiceOnly: false,
    printDeductionsAndInvoice: false,
    printItemizedDeductionsOnly: false,
    finalInvoice: false
  };
  printOption: any = 'printDraftInvoice';
  reportOption: any = 'printDeductionsAndInvoice';
  incident: any;
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  selectBy: any = 'selectByRequestNumber';
  requestNumbers: any = [];
  resourceIdSelected: any;
  isAbleDelete: boolean = true;
  iscrew: boolean = false;
  crewNames: any = [];
  resourceData: any = [];
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
    this.lastDateToIncludeOnInvoice.writeValue(today);
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.timeReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.timeReportFilter.incidentId = 0;
    } else {
      this.timeReportFilter.incidentGroupId = 0;
      this.timeReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.timeReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.timeReportFilter.incidentId = 0;
        } else {
          this.timeReportFilter.incidentGroupId = 0;
          this.timeReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
        this.setupData(this.timeReportFilter.incidentId, this.timeReportFilter.incidentGroupId);
    });

    this.setupData(this.timeReportFilter.incidentId, this.timeReportFilter.incidentGroupId);
    this.promptModal.promptTitle = 'OF-288';
    this.promptModal.promptMessage1 = 'Please select a resource';
    this.promptModal.button1Label = 'Ok';
    this.promptModal1.promptTitle = 'Time Reports';
    this.promptModal1.button1Label = 'Yes';
    this.promptModal1.button2Label = 'No';
    this.promptModal2.promptTitle = 'Time Reports';
    this.promptModal2.promptMessage1 = 'This will delete the last invoice for each crew member?  Do you want to continue?';
    this.promptModal2.button1Label = 'Yes';
    this.promptModal2.button2Label = 'No';
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

  setupData(incidentId, incidentGroupId) {
    this.getResourceData(incidentId, incidentGroupId)
    this.reportTimeService.getResourceListForSelectedIncident('CREW_NAMES', incidentId, incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          datas.push({resourceId: obj.resourceId, label: obj.label});
        });
        this.crewNames = this.sortBy(datas, "label");
        if (this.selectBy === 'selectByCrew') {
          this.requestNumbers = _.cloneDeep(this.crewNames);
          this.resourceIdSelected = _.head(this.requestNumbers).resourceId
        } else if (this.selectBy === 'selectByRequestNumber') {
          // this.setRequestNumber();
          this.getResourceData(incidentId, incidentGroupId);
        } else {
          this.setPersonName(incidentId, incidentGroupId);
        }
      }
    });
  }

  getResourceData(incidentId, incidentGroupId){
    this.reportTimeService.getResourceData(incidentId, incidentGroupId).subscribe(rs => {
      if(rs['recordset']){
        let datas = [];
        _.each(rs['recordset'], obj => {
          if(obj.requestNumber != null && obj.requestNumber != ''){
            if(obj.contracted==false){
              if(obj.resourceName){
                datas.push({incidentResourceId: obj.incidentResourceId, resourceId: obj.resourceId, label: `${obj.requestNumber} ${obj.resourceName}`})
              } else {
                datas.push({incidentResourceId: obj.incidentResourceId, resourceId: obj.resourceId, label: `${obj.requestNumber} ${obj.lastName}, ${obj.firstName}`})
              }
            }
          }
        })
        this.resourceData = datas;
        this.requestNumbers = this.sortBy(datas, "label")
        // this.requestNumbers = _.sortBy(datas, 'label')
        this.resourceIdSelected = _.head(this.requestNumbers).incidentResourceId;
        this.selectedChange();
      }
    })
  }
  /*setRequestNumber(){
    this.requestNumbers = _.sortBy(this.resourceData, 'label')
    this.resourceIdSelected = _.head(this.requestNumbers).incidentResourceId;
    this.selectedChange();
  }*/

  setRequestNumber(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('PERSON_REQUEST_NUMBERS', incidentId, incidentGroupId).subscribe(rs => {
      this.reportTimeService.getResourceListForSelectedIncident('PERSON_RESOURCE_NAMES', incidentId, incidentGroupId).subscribe(rs1 => {
        if (rs1['resultObject']) {
          let datas = [];
          _.each(rs['resultObject'], obj => {
            let data = {resourceId: obj.resourceId, label: obj.label};
            let resource = _.find(rs1['resultObject'], {resourceId: obj.resourceId});
            if (resource) {
              data.label += ' ' + resource.label;
              datas.push(data);
            }
          });
          this.requestNumbers = this.sortBy(datas, "label");
          this.resourceIdSelected = _.head(this.requestNumbers).resourceId;
          this.selectedChange();
        }
      });
    });
  }

  setPersonName(incidentId, incidentGroupId) {
    this.reportTimeService.getResourceListForSelectedIncident('PERSON_RESOURCE_NAMES', incidentId, incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          datas.push({incidentResourceId: obj.incidentResourceId, resourceId: obj.incidentResourceId, label: obj.label});
        });
        this.requestNumbers = this.sortBy(datas, "label");
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

  changeSelectBy(e) {
    this.reportTimeService.getResourceListForSelectedIncident('CREW_NAMES', this.timeReportFilter.incidentId, this.timeReportFilter.incidentGroupId).subscribe(rs => {
      if (rs['resultObject']) {
        let datas = [];
        _.each(rs['resultObject'], obj => {
          datas.push({incidentResourceId: obj.incidentResourceId, resourceId: obj.resourceId, label: obj.label});
        });
        this.crewNames = this.sortBy(datas, "label");
        if (e.target.value === 'selectByCrew') {
          this.requestNumbers = _.cloneDeep(this.crewNames);
          this.resourceIdSelected = _.head(this.requestNumbers).incidentResourceId
        } else if (e.target.value === 'selectByRequestNumber') {
          // this.setRequestNumber(this.timeReportFilter.incidentId, this.timeReportFilter.incidentGroupId);
          this.getResourceData(this.timeReportFilter.incidentId, this.timeReportFilter.incidentGroupId);
        } else {
          this.setPersonName(this.timeReportFilter.incidentId, this.timeReportFilter.incidentGroupId);
        }
      }
    });
  }

  convertDate(dt) {
    return {
      dateString: + "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear(),
      timeString: ("0" + dt.getHours()).slice(-2)   + ":" + ("0" + dt.getMinutes()).slice(-2)
    }
  }

  promptActionResult1(action) {
    if (action === 'Yes') {
      this.modalService.open('addReason');
    }
    this.promptModal1.closeModal();
  }

  promptActionResult2(action) {
    if (action === 'Yes') {
      this.modalService.open('addReason');
    }
    this.promptModal2.closeModal();
  }

  cancelAddReason() {
    this.modalService.close('addReason');
  }

  deleteModalOpen() {
    if (!this.resourceIdSelected) {
      this.notificationService.showError2('Please select a resource prior to deleting last invoice.', 'Error');
      return;
    }
    let iscrew = false;
    _.each(this.crewNames, obj => {
      if (this.resourceIdSelected == obj.resourceId) {
        iscrew = true;
      }
    });
    if (iscrew) {
      this.promptModal2.openModal();
      return;
    }
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

  convertDateToString(dt) {
    // console.log(dt);
    return "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear();
  }

  selectedChange() {
    this.reportTimeService.getResourceInvoiceList(this.resourceIdSelected).subscribe(rs => {
      this.data = [];
      this.isAbleDelete = true;;
      if (rs['resultObject'] && rs['resultObject'][0]) {
          this.lastDateToIncludeOnInvoice.writeValue(new Date());
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
          if (rs.resultObjectAlternate) {
            this.lastDateToIncludeOnInvoice.writeValue(new Date(String(rs.resultObjectAlternate)));
          } else {
            this.lastDateToIncludeOnInvoice.writeValue(new Date());
          }
          if (this.data.length < 6) {
            let bonusLenth = 6 - this.data.length;
            for (let index = 0; index < bonusLenth; index++) {
              this.data.push({})
            }
          }
        }
    });
  }

  promptActionResult(action) {
    if (action === 'Ok' || action === 'No') {
      this.promptModal.closeModal();
    } else if(action === 'Yes') {
      this.promptModal.closeModal();
      this.reportTimeService.generateOF288TimeInvoiceReport(this.timeReportFilter).subscribe(data => {
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
    this.timeReportFilter.printDraftInvoice = false;
    this.timeReportFilter.printOriginalInvoice = false;
    this.timeReportFilter.printDuplicateOriginalInvoice = false;
    this.timeReportFilter.printDeductionsAndInvoice = false;
    this.timeReportFilter.printInvoiceOnly = false;
    this.timeReportFilter.printItemizedDeductionsOnly = false;
    this.timeReportFilter.incidentResourceId = this.resourceIdSelected;
    this.timeReportFilter[this.printOption] = true;
    this.timeReportFilter[this.reportOption] = true;
    this.timeReportFilter.incidentResourceId = this.resourceIdSelected;
    if (this.lastDateToIncludeOnInvoice.getFormattedDate()) {
      this.timeReportFilter.lastDateToIncludeOnReportVo = this.convertDate(new Date(this.lastDateToIncludeOnInvoice.getFormattedDate()));
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

    this.reportTimeService.generateOF288TimeInvoiceReport(this.timeReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
