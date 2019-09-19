import { Component, OnInit } from '@angular/core';
import * as _ from 'lodash';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { AllIncidentResourcesReportFilter } from 'src/app/data/filter/allIncident-resources-report-filter';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { IncidentGroupService } from 'src/app/service/incident-group.service';
import { DropdownData } from 'src/app/data/dropdowndata';

@Component({
  selector: 'app-all-resources-report',
  templateUrl: './all-resources-report.component.html',
  styleUrls: ['./all-resources-report.component.css']
})
export class AllResourcesReportComponent implements OnInit {
  data: any = [
    {id: 1, code: "requestNumber" , description: 'Request #'},
    {id: 2, code: "resourceName" , description: 'Resource Name'},
    {id: 3, code: "itemCode" , description: 'Item Code'},
    {id: 4, code: "unitId" , description: 'Unit ID'},
    {id: 5, code: "agencyCode" , description: 'Agency'},
    {id: 6, code: "assignmentStatus" , description: 'Status'},
    {id: 7, code: "checkInDate" , description: 'Check-In Date'},
    {id: 8, code: "mobDate" , description: 'Mobilization Date'},
    {id: 9, code: "firstWorkDay" , description: 'First Work Day'},
    {id: 10, code: "lengthAtAssignment" , description: 'Length Of Assignment'},
    {id: 11, code: "demobDate" , description: 'Demob Date'},
    {id: 12, code: "mobilizationTravelMethod" , description: 'Mobilization Travel Method'},
    {id: 13, code: "cellPhone" , description: 'Cell Phone'},
  ];
  bodyStyle = {'height':'162px'};
  extraStyle = {
    row: {
      'background-color': 'white'
    }
  }
  scrollAble = true;
  dataSelected = []
  indexSelected: any = [];
  indexSelectedCopy: any = [];
  filter: boolean = true;
  tableDef: TableDefinition;
  noHeader: boolean = true;
  tooltipBox: boolean = false;
  incident: any;
  allIncidentResourcesReportFilter: AllIncidentResourcesReportFilter = <AllIncidentResourcesReportFilter> {
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
  shiftIndex: any;
  shiftIndexCopy: any;
  sortListOrigin: any = [];

  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  constructor(
    public reportPlansService: ReportPlansService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService,
    private incidentGroupService: IncidentGroupService
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
      this.allIncidentResourcesReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.allIncidentResourcesReportFilter.incidentId = 0;
    } else {
      this.allIncidentResourcesReportFilter.incidentGroupId = 0;
      this.allIncidentResourcesReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.allIncidentResourcesReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.allIncidentResourcesReportFilter.incidentId = 0;
        } else {
          this.allIncidentResourcesReportFilter.incidentGroupId = 0;
          this.allIncidentResourcesReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
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
    if (this.allIncidentResourcesReportFilter[event.target.value]) {
      this.allIncidentResourcesReportFilter[event.target.value] = false;
      this.handleUncheck();
    } else {
      this.allIncidentResourcesReportFilter[event.target.value] = true;
      this.handleCheckall();
    }
  }

  handleCheckall() {
    if (this.allIncidentResourcesReportFilter.allResourceCategories) {
      this.allIncidentResourcesReportFilter.aircraft = true;
      this.allIncidentResourcesReportFilter.crews = true;
      this.allIncidentResourcesReportFilter.overhead = true;
      this.allIncidentResourcesReportFilter.equipment = true;
    }
    if (this.allIncidentResourcesReportFilter.allGroups) {
      this.allIncidentResourcesReportFilter.operations = true;
      this.allIncidentResourcesReportFilter.command = true;
      this.allIncidentResourcesReportFilter.logistics = true;
      this.allIncidentResourcesReportFilter.plans = true;
      this.allIncidentResourcesReportFilter.finance = true;
      this.allIncidentResourcesReportFilter.external = true;
    }
    if (this.allIncidentResourcesReportFilter.allResourceStatuses) {
      this.allIncidentResourcesReportFilter.checkin = true;
      this.allIncidentResourcesReportFilter.demobed = true;
      this.allIncidentResourcesReportFilter.reassigned = true;
      this.allIncidentResourcesReportFilter.pendingDemob = true;
      this.allIncidentResourcesReportFilter.filled = true;
    }
  }

  handleUncheck() {
    if (this.allIncidentResourcesReportFilter.aircraft === false
      || this.allIncidentResourcesReportFilter.crews === false
      || this.allIncidentResourcesReportFilter.overhead === false
      || this.allIncidentResourcesReportFilter.equipment === false) {
        this.allIncidentResourcesReportFilter.allResourceCategories = false;
    }
    if (this.allIncidentResourcesReportFilter.operations === false
      || this.allIncidentResourcesReportFilter.command === false
      || this.allIncidentResourcesReportFilter.logistics === false
      || this.allIncidentResourcesReportFilter.plans === false
      || this.allIncidentResourcesReportFilter.finance === false
      || this.allIncidentResourcesReportFilter.external === false) {
      this.allIncidentResourcesReportFilter.allGroups = false;
    }
    if (this.allIncidentResourcesReportFilter.checkin === false
      || this.allIncidentResourcesReportFilter.demobed === false
      || this.allIncidentResourcesReportFilter.reassigned === false
      || this.allIncidentResourcesReportFilter.pendingDemob === false
      || this.allIncidentResourcesReportFilter.filled === false) {
      this.allIncidentResourcesReportFilter.allResourceStatuses = false;
    }
  }

  generateReport() {
    this.allIncidentResourcesReportFilter.allResourcesSort = [];
    _.each(this.dataSelected, obj => {
      this.allIncidentResourcesReportFilter.allResourcesSort.push(obj.code);
    });
    this.reportPlansService.generateAllIncidentResourcesReport(this.allIncidentResourcesReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }

  restoreDefault() {
    this.data = _.cloneDeep(this.sortListOrigin);
    this.dataSelected = [];
    this.allIncidentResourcesReportFilter = <AllIncidentResourcesReportFilter> {
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
      filled: true,

      strikeTeamTaskForce: false,
      subTotalsFirstSort: false
    }
    if (this.incident.incidentGroupId) {
      this.allIncidentResourcesReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.allIncidentResourcesReportFilter.incidentId = 0;
    } else {
      this.allIncidentResourcesReportFilter.incidentGroupId = 0;
      this.allIncidentResourcesReportFilter.incidentId = this.incident.id;
    }
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      this.allIncidentResourcesReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
      this.allIncidentResourcesReportFilter.incidentId = 0;
    } else if (this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      this.allIncidentResourcesReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
    }
    this.indexSelected = [];
    this.indexSelectedCopy = [];
  }
}
