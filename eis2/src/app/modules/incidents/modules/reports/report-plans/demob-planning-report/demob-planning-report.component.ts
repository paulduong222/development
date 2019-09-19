import { Component, OnInit } from '@angular/core';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import * as _ from 'lodash';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { DemobPlanningReportFilter } from 'src/app/data/filter/demob-planning-report-filter';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-demob-planning-report',
  templateUrl: './demob-planning-report.component.html',
  styleUrls: ['./demob-planning-report.component.css']
})
export class DemobPlanningReportComponent implements OnInit {
  data: any = [
    {id: 1, code: "requestNumber" , description: 'Request #'},
    {id: 2, code: "resourceName" , description: 'Resource Name'},
    {id: 3, code: "itemCode" , description: 'Item Code'},
    {id: 4, code: "agency" , description: 'Agency'},
    {id: 5, code: "unit" , description: 'Unit ID'},
    {id: 6, code: "demobCity" , description: 'Demob City'},
    {id: 7, code: "demobState" , description: 'Demob State'},
    {id: 8, code: "returnTravelMethod" , description: 'Demobilization Travel Method'},
    {id: 9, code: "lastWorkDay" , description: 'Last Work Day'},
    {id: 10, code: "daysLeft" , description: 'Days Left'},
    {id: 11, code: "status" , description: 'Status'}
  ];
  bodyStyle= {'height':'162px'};
  scrollAble = true;
  dataSelected = []
  indexSelected: any = [];
  indexSelectedCopy: any = [];
  filter: boolean = true;
  tableDef: TableDefinition;
  noHeader: boolean = true;
  tooltipBox: boolean = false;
  extraStyle = {
    row: {
      'background-color': 'white'
    }
  }
  demobPlanningReportFilter: DemobPlanningReportFilter = <DemobPlanningReportFilter>{
    allResourceCategories: true,
   	aircraft: true,
    crews: true,
    overhead: true,
    equipment: true,

    allGroups: true,
    operations: true,
    command: true,
    logistics: true,
    plans: true,
    finance: true,
    external: true,

    allResourceStatuses: true,
    checkin: true,
    demobed: true,
    reassigned: true,
    pendingDemob: true,
    filled: true
  };
  incident: any;
  shiftIndex: any;
  shiftIndexCopy: any;
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
          { field: 'description', title: 'description', style: {'text-align': 'left', 'font-size': '13px', 'border': 'none'}},
      ]
    };
  }

  ngOnInit() {
    this.data = _.sortBy(this.data);
    this.sortListOrigin = _.cloneDeep(this.data);
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.demobPlanningReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.demobPlanningReportFilter.incidentId = 0;
    } else {
      this.demobPlanningReportFilter.incidentGroupId = 0;
      this.demobPlanningReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.demobPlanningReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.demobPlanningReportFilter.incidentId = 0;
        } else {
          this.demobPlanningReportFilter.incidentGroupId = 0;
          this.demobPlanningReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
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

  handleCheckbox(event) {
    if (this.demobPlanningReportFilter[event.target.value]) {
      this.demobPlanningReportFilter[event.target.value] = false;
      this.handleUncheck();
    } else {
      this.demobPlanningReportFilter[event.target.value] = true;
      this.handleCheckall();
    }
  }

  handleCheckall() {
    if (this.demobPlanningReportFilter.allResourceCategories) {
      this.demobPlanningReportFilter.aircraft = true;
      this.demobPlanningReportFilter.crews = true;
      this.demobPlanningReportFilter.overhead = true;
      this.demobPlanningReportFilter.equipment = true;
    }
    if (this.demobPlanningReportFilter.allGroups) {
      this.demobPlanningReportFilter.operations = true;
      this.demobPlanningReportFilter.command = true;
      this.demobPlanningReportFilter.logistics = true;
      this.demobPlanningReportFilter.plans = true;
      this.demobPlanningReportFilter.finance = true;
      this.demobPlanningReportFilter.external = true;
    }
    if (this.demobPlanningReportFilter.allResourceStatuses) {
      this.demobPlanningReportFilter.checkin = true;
      this.demobPlanningReportFilter.demobed = true;
      this.demobPlanningReportFilter.reassigned = true;
      this.demobPlanningReportFilter.pendingDemob = true;
      this.demobPlanningReportFilter.filled = true;
    }
  }

  handleUncheck() {
    if (this.demobPlanningReportFilter.aircraft === false
      || this.demobPlanningReportFilter.crews === false
      || this.demobPlanningReportFilter.overhead === false
      || this.demobPlanningReportFilter.equipment === false) {
        this.demobPlanningReportFilter.allResourceCategories = false;
    }
    if (this.demobPlanningReportFilter.operations === false
      || this.demobPlanningReportFilter.command === false
      || this.demobPlanningReportFilter.logistics === false
      || this.demobPlanningReportFilter.plans === false
      || this.demobPlanningReportFilter.finance === false
      || this.demobPlanningReportFilter.external === false) {
      this.demobPlanningReportFilter.allGroups = false;
    }
    if (this.demobPlanningReportFilter.checkin === false
      || this.demobPlanningReportFilter.demobed === false
      || this.demobPlanningReportFilter.reassigned === false
      || this.demobPlanningReportFilter.pendingDemob === false
      || this.demobPlanningReportFilter.filled === false) {
      this.demobPlanningReportFilter.allResourceStatuses = false;
    }
  }

  generateReport() {
    this.demobPlanningReportFilter.sorts = [];
    _.each(this.dataSelected, obj => {
      this.demobPlanningReportFilter.sorts.push(obj.code);
    });
    this.reportPlansService.generateDemobPlanningReport(this.demobPlanningReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }

  restoreDefault() {
    this.data = _.cloneDeep(this.sortListOrigin);
    this.dataSelected = [];
    this.indexSelectedCopy = []
    this.indexSelected = [];
    this.demobPlanningReportFilter = <DemobPlanningReportFilter>{
      allResourceCategories: true,
       aircraft: true,
      crews: true,
      overhead: true,
      equipment: true,

      allGroups: true,
      operations: true,
      command: true,
      logistics: true,
      plans: true,
      finance: true,
      external: true,

      allResourceStatuses: true,
      checkin: true,
      demobed: true,
      reassigned: true,
      pendingDemob: true,
      filled: true
    };

    if (this.incident.incidentGroupId) {
      this.demobPlanningReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.demobPlanningReportFilter.incidentId = 0;
    } else {
      this.demobPlanningReportFilter.incidentGroupId = 0;
      this.demobPlanningReportFilter.incidentId = this.incident.id;
    }
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      this.demobPlanningReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
      this.demobPlanningReportFilter.incidentId = 0;
    } else if (this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      this.demobPlanningReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
    }
  }
}
