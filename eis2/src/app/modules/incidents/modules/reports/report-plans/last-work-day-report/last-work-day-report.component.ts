import { Component, OnInit, ViewChild } from '@angular/core';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import * as _ from 'lodash';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { NotificationService } from 'src/app/service/notification-service';
import { LastWorkDayReportFilter } from 'src/app/data/filter/last-work-day-report-filter';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-last-work-day-report',
  templateUrl: './last-work-day-report.component.html',
  styleUrls: ['./last-work-day-report.component.css']
})
export class LastWorkDayReportComponent implements OnInit {
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  data: any = [
    {id: 1, code: "itemCode" , description: 'Item Code'},
    {id: 2, code: "requestNumber" , description: 'Request Number'},
    {id: 3, code: "name" , description: 'Name'},
    {id: 4, code: "requestCategory" , description: 'Request Category'}
  ];
  groupBy: any = 'groupByDateResourceCategory'
  bodyStyle= {'height':'95px'};
  scrollAble = true;
  dataSelected = []
  indexSelected: any = [];
  indexSelectedCopy: any = [];
  filter: boolean = true;
  tableDef: TableDefinition;
  noHeader: boolean = true;
  tooltipBox: boolean = false;
  incident: any;
  lastWorkDayReportFilter: LastWorkDayReportFilter = <LastWorkDayReportFilter> {
    allSections: true,
    operations: true,
    command: true,
    logistics: true,
    plans: true,
    finance: true,
    externalResources: true,
    includeAllDates: true
  };
  shiftIndex: any;
  shiftIndexCopy: any;
  sortListOrigin: any = [];
  extraStyle = {
    row: {
      'background-color': 'white'
    }
  }
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  constructor(
    public reportPlansService: ReportPlansService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService
  ) {
    this.tableDef = {
      columns: [
          { field: 'description', title: 'description' ,  style: {'text-align': 'left', 'font-size': '13px', 'border': 'none'}},
      ]
    };
  }

  ngOnInit() {
    this.data = _.sortBy(this.data);
    this.sortListOrigin = _.cloneDeep(this.data);
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.lastWorkDayReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.lastWorkDayReportFilter.incidentId = 0;
    } else {
      this.lastWorkDayReportFilter.incidentGroupId = 0;
      this.lastWorkDayReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.lastWorkDayReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.lastWorkDayReportFilter.incidentId = 0;
        } else {
          this.lastWorkDayReportFilter.incidentGroupId = 0;
          this.lastWorkDayReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
    this.lastWorkDayReportFilter.includeAllDates = true;
    this.lastWorkDayReportFilter.command = true;
    this.lastWorkDayReportFilter.externalResources = true;
    this.lastWorkDayReportFilter.finance = true;
    this.lastWorkDayReportFilter.logistics = true;
    this.lastWorkDayReportFilter.operations = true;
    this.lastWorkDayReportFilter.plans = true;
    this.dtStart.setDisabledState(true);
    this.dtEnd.setDisabledState(true);
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
      if (_.findIndex(this[to], { id: temp[indexSelected].id }) == -1) {
        let elementCopy = temp[indexSelected];
        this[to].push(elementCopy);
        this[from] = _.filter(this[from], function(x) { return x.id !== elementCopy.id; });
      }
    }
    this[to] = _.cloneDeep(this[to]);
    this[from] = _.cloneDeep(this[from]);
    this[indexType] = [];
    this[shiftIndex] = '';
  }

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
  }

  handleCheckbox(event) {
    if (this.lastWorkDayReportFilter[event.target.value]) {
      this.lastWorkDayReportFilter[event.target.value] = false;
      this.handleUncheck();
    } else {
      this.lastWorkDayReportFilter[event.target.value] = true;
      this.handleCheckall();
    }
    if (this.lastWorkDayReportFilter.includeAllDates) {
      this.dtStart.setDisabledState(true);
      this.dtEnd.setDisabledState(true);
    } else {
      this.dtStart.setDisabledState(false);
      this.dtEnd.setDisabledState(false);
    }
  }

  handleCheckall() {
    if (this.lastWorkDayReportFilter.allSections) {
      this.lastWorkDayReportFilter.operations = true;
      this.lastWorkDayReportFilter.command = true;
      this.lastWorkDayReportFilter.logistics = true;
      this.lastWorkDayReportFilter.plans = true;
      this.lastWorkDayReportFilter.finance = true;
      this.lastWorkDayReportFilter.externalResources = true;
      this.lastWorkDayReportFilter.includeAllDates = true;
    }
  }

  handleUncheck() {
    if (this.lastWorkDayReportFilter.operations === false
      || this.lastWorkDayReportFilter.command === false
      || this.lastWorkDayReportFilter.logistics === false
      || this.lastWorkDayReportFilter.plans === false
      || this.lastWorkDayReportFilter.finance === false
      || this.lastWorkDayReportFilter.externalResources === false
      || this.lastWorkDayReportFilter.includeAllDates === false) {
        this.lastWorkDayReportFilter.allSections = false;
    }
  }

  generateReport() {
    if (!this.lastWorkDayReportFilter.includeAllDates) {
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
    }

    this.lastWorkDayReportFilter.reportStartDate = new Date(this.dtStart.getFormattedDate());
    this.lastWorkDayReportFilter.reportEndDate = new Date(this.dtEnd.getFormattedDate());
    this.lastWorkDayReportFilter.groupByDateResourceCategory = false;
    this.lastWorkDayReportFilter.groupBySectionDate = false;
    this.lastWorkDayReportFilter.groupBySectionResourceCategoryDate = false;
    this.lastWorkDayReportFilter[this.groupBy] = true;
    this.lastWorkDayReportFilter.sortBy = [];
    _.each(this.dataSelected, obj => {
      this.lastWorkDayReportFilter.sortBy.push(obj.code);
    });
    this.reportPlansService.generateLastWorkDayReport(this.lastWorkDayReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }

  restoreDefault() {
    this.groupBy = 'groupByDateResourceCategory';
    this.lastWorkDayReportFilter = <LastWorkDayReportFilter> {
      allSections: true,
      operations: true,
      command: true,
      logistics: true,
      plans: true,
      finance: true,
      externalResources: true,
      includeAllDates: true
    };
    if (this.incident.incidentGroupId) {
      this.lastWorkDayReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.lastWorkDayReportFilter.incidentId = 0;
    } else {
      this.lastWorkDayReportFilter.incidentGroupId = 0;
      this.lastWorkDayReportFilter.incidentId = this.incident.id;
    }
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      this.lastWorkDayReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
      this.lastWorkDayReportFilter.incidentId = 0;
    } else if (this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      this.lastWorkDayReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
    }
    this.data = _.cloneDeep(this.sortListOrigin);
    this.dataSelected = [];
    this.shiftIndex = '';
    this.indexSelected = [];
    this.indexSelectedCopy = [];
    this.dtStart.writeValue('');
    this.dtEnd.writeValue('');
  }
}
