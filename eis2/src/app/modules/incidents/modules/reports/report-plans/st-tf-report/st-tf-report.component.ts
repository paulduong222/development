import { Component, OnInit } from '@angular/core';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { StrikeTeamTaskForceReportFilter } from 'src/app/data/filter/strike-team-task-force-report-filter';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import * as _  from 'lodash';

@Component({
  selector: 'app-st-tf-report',
  templateUrl: './st-tf-report.component.html',
  styleUrls: ['./st-tf-report.component.css']
})
export class StTfReportComponent implements OnInit {
  tableDef: TableDefinition;
  data: any = [];
  scrollAble: boolean = false;
  tooltipBox: boolean = false;
  incident: any;
  strikeTeamTaskForceReportFilter: StrikeTeamTaskForceReportFilter = <StrikeTeamTaskForceReportFilter>{}
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  indexSelected: any = [];
  constructor(
    public reportPlansService: ReportPlansService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService
  ) {
    this.tableDef = {
      columns: [
          { field: 'requestNumber', title: 'Request #', style: {'width':'100px'}},
          { field: 'resourceName', title: 'Name', style: {'width':'356px'}}
      ]
    };
  }

  ngOnInit() {
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.strikeTeamTaskForceReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.strikeTeamTaskForceReportFilter.incidentId = 0;
    } else {
      this.strikeTeamTaskForceReportFilter.incidentGroupId = 0;
      this.strikeTeamTaskForceReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.strikeTeamTaskForceReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.strikeTeamTaskForceReportFilter.incidentId = 0;
        } else {
          this.strikeTeamTaskForceReportFilter.incidentGroupId = 0;
          this.strikeTeamTaskForceReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
        this.setDataGrid();
    });
    this.setDataGrid();
  }

  setDataGrid() {
    this.reportPlansService.getStrikeTeams(this.strikeTeamTaskForceReportFilter.incidentId, this.strikeTeamTaskForceReportFilter.incidentGroupId).subscribe(rs => {
      this.data = rs.recordset;
      _.each(this.data, obj => {
        obj.id = obj.resourceId;
      });
      if (this.data.length < 6) {
        let bonusLenth = 6 - this.data.length;
        for (let index = 0; index < bonusLenth; index++) {
          this.data.push({})
        }
      }
      this.data = _.cloneDeep(this.data);
    }, err => {
      this.data = [];
        for (let index = 0; index < 6; index++) {
          this.data.push({})
        }
    });
  }

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
  }

  // getClickedRow(event) {
  //   this.indexSelected = [];
  //   if (event.id) {
  //     this.indexSelected.push(event.id);
  //   }
  // }

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

  selectAll() {
    this.indexSelected = [];
    _.each(this.data, obj => {
      if (obj.id) {
        this.indexSelected.push(obj.id);
      }
    });
    this.indexSelected = _.cloneDeep(this.indexSelected);
  }

  deSelectAll() {
    this.indexSelected = [];
  }

  refresh() {
    this.indexSelected = [];
  }

  generateReport() {
    this.strikeTeamTaskForceReportFilter.resourceIds = this.indexSelected;
    if (!this.strikeTeamTaskForceReportFilter.resourceIds || this.strikeTeamTaskForceReportFilter.resourceIds.length === 0) {
      this.notificationService.showError2('At least 1 Strike Team/Task Force must be selected.', 'Error');
      return;
    }
    this.reportPlansService.generateStrikeTeamTaskForceReport(this.strikeTeamTaskForceReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
