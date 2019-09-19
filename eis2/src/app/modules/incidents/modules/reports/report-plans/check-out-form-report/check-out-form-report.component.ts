import { Component, OnInit, ViewChild } from '@angular/core';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import* as _ from 'lodash';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { CheckoutReportFilter } from 'src/app/data/filter/checkout-report-filter';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-check-out-form-report',
  templateUrl: './check-out-form-report.component.html',
  styleUrls: ['./check-out-form-report.component.css']
})
export class CheckOutFormReportComponent implements OnInit {
  data: any = [
    {id: 1, code: "requestNumber" , description: 'Request #'},
    {id: 2, code: "resourceName" , description: 'Resource Name'},
    {id: 3, code: "incidentName" , description: 'Incident Name'},
    {id: 4, code: "incidentNumber" , description: 'Incident Number'},
    {id: 5, code: "tentativeReleaseDate" , description: 'Tentative Release Date'},
    {id: 6, code: "actualReleaseDate" , description: 'Actual Release Date'},
    {id: 7, code: "unitId" , description: 'Unit ID'},
    {id: 8, code: "agency" , description: 'Agency'},
  ];
  bodyStyle= {'height':'245px'};
  scrollAble = true;
  dataSelected = []
  indexSelected: any = [];
  indexSelectedCopy: any = [];
  filter: boolean = true;
  tableDef: TableDefinition;
  noHeader: boolean = true;
  tooltipBox: boolean = false;
  checkoutReportFilter: CheckoutReportFilter = <CheckoutReportFilter>{};
  incident: any;
  includeSTTFComponents: any;
  shiftIndex: any;
  shiftIndexCopy: any;
  extraStyle = {
    row: {
      'background-color': 'white'
    }
  }
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  @ViewChild('promptModal2') promptModal2: PromptModalComponent;
  sortListOrigin: any = [];
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  constructor(
    public reportPlansService: ReportPlansService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService
  ) {
    this.tableDef = {
      columns: [
          { field: 'description', title: 'description', style: {'width':'165px','font-size':'13px', 'text-align': 'left', 'border': 'none'}},
      ]
    };
  }

  ngOnInit() {
    this.data = _.sortBy(this.data);
    this.sortListOrigin = _.cloneDeep(this.data);
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.checkoutReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.checkoutReportFilter.incidentId = 0;
    } else {
      this.checkoutReportFilter.incidentGroupId = 0;
      this.checkoutReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.checkoutReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.checkoutReportFilter.incidentId = 0;
        } else {
          this.checkoutReportFilter.incidentGroupId = 0;
          this.checkoutReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
    this.promptModal.promptTitle = 'Incident Resources';
    this.promptModal.promptMessage1 = 'Do you want the system to mark the applicable Resource records as Check-Out Form Printed?';
    this.promptModal.button1Label = 'Yes';
    this.promptModal.button2Label = 'No';
    this.promptModal2.promptTitle = 'Incident Resources';
    this.promptModal2.promptMessage1 = 'Do you want the system to include Estimated Date/Time of Arrival and Rest Overnight Information in Box 12?';
    this.promptModal2.button1Label = 'Yes';
    this.promptModal2.button2Label = 'No';
  }

  getClickedRow(event, indexType, data, shiftIndex) {
    if (this[data][event.index] && !_.isEmpty(this[data][event.index], true)) {
      if (event.event.ctrlKey) {
        this[shiftIndex] = event.index;
        if (!this[indexType].includes(event.id)) {
          this[indexType].push(event.id);
        } else {
          this[indexType] = _.pull(this[indexType], event.id);
        }
      } else if (!event.event.shiftKey && !event.event.ctrlKey) {
        this[shiftIndex] = event.index;
        this[indexType] = [];
        if (!this[indexType].includes(event.id)) {
          this[indexType].push(event.id);
        }
      } else if (event.event.shiftKey) {
        this[indexType] = [];
        if (!this[shiftIndex] && this[shiftIndex] !== 0) {
          return;
        }
        if (this[shiftIndex] <= event.index) {
          for (let index = this[shiftIndex]; index <= event.index; index++) {
            this[indexType].push(this[data][index].id);
          }
        } else {
          for (let index = this[shiftIndex]; index >= event.index; index--) {
            this[indexType].push(this[data][index].id);
          }
        }
      }
      this[indexType] = _.cloneDeep(this[indexType]);
    }
  }

  moveSelected(from, to, indexType, shiftIndex) {
    if (!this[indexType] || this[indexType].length == 0) {
      return;
    }

    for (let indexSelected = 0; indexSelected < this[indexType].length; indexSelected++) {
      let elementCopy = _.find(this[from], {id: this[indexType][indexSelected]});
      if (elementCopy) {
        this[to].push(elementCopy);
        this[from] = _.filter(this[from], function(x) { return x.id !== elementCopy.id; });
      }
    }

    this[to] = _.cloneDeep(this[to]);
    this[indexType] = [];
    this[shiftIndex] = '';
  }

  moveAll(from, to, indexType, shiftIndex) {
    let temp = _.cloneDeep(this[from]);
    for (let indexSelected = 0; indexSelected < temp.length; indexSelected++) {
      if (temp[indexSelected]) {
        let elementCopy = temp[indexSelected];
        this[to].push(elementCopy);
        this[from] = _.filter(this[from], function(x) { return x.id !== elementCopy.id; });
      }
    }
    this[to] = _.cloneDeep(this[to]);
    this[indexType] = [];
    this[shiftIndex] = '';
  }

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
  }

  promptActionResult(action) {
    if (action === 'Yes') {
      this.checkoutReportFilter.markCheckOutFormPrinted = true;
      this.promptModal.closeModal();
    }
    if (action === 'No') {
      this.checkoutReportFilter.markCheckOutFormPrinted = false;
      this.promptModal.closeModal();
    }
    this.promptModal2.openModal();
  }

  promptActionResult2(action) {
    if (action === 'Yes') {
      this.checkoutReportFilter.markIncludeBox12 = true;
      this.promptModal2.closeModal();
    }
    if (action === 'No') {
      this.checkoutReportFilter.markIncludeBox12 = false;
      this.promptModal2.closeModal();
    }
    this.generateReport();
  }

  confirm() {
    this.promptModal.openModal();
  }

  generateReport() {
    this.checkoutReportFilter.sorts = [];
    _.each(this.dataSelected, obj => {
      this.checkoutReportFilter.sorts.push(obj.code);
    });
    if (this.includeSTTFComponents === 'includeSTTFComponents') {
      this.checkoutReportFilter.includeSTTFComponents = true;
    } else {
      this.checkoutReportFilter.includeSTTFComponents = false;
    }
    this.reportPlansService.generateCheckoutReport(this.checkoutReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
