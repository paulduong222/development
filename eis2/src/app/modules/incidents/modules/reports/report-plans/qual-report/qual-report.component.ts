import { Component, OnInit } from '@angular/core';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import * as _ from 'lodash';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { ItemCodeService } from 'src/app/service/item-code.service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { QualificationsReportFilter } from 'src/app/data/filter/qualifications-report-filter';
import { KindVo } from "src/app/data/kind-vo";
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-qual-report',
  templateUrl: './qual-report.component.html',
  styleUrls: ['./qual-report.component.css']
})
export class QualReportComponent implements OnInit {
  data: any = [];
  incident: any = {};
  scrollAble = true;
  bodyStyle = {'height':'180px'};
  dataSelected: KindVo[] = [];
  indexSelected: any = [];
  indexSelectedCopy: any = [];
  filter: boolean = true;
  tableDef: TableDefinition;
  tooltipBox: boolean = false;
  qualificationsReportFilter: QualificationsReportFilter = <QualificationsReportFilter>{};
  optionFilter: any = 'allResources';
  shiftIndex: any;
  shiftIndexCopy: any;
  sortListOrigin: any = [];
  sortListOriginV2: any = [];
  filterObject: any = {};
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  constructor(
    public reportPlansService: ReportPlansService,
    private itemCodeService: ItemCodeService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService
  ) {
    this.tableDef = {
      columns: [
          { field: 'code', title: 'Item Code', style: {'width':'120px', 'text-align': 'left', 'font-size': '13px', 'padding': '0 5px'}},
          { field: 'description', title: 'Item Name', style: {'width':'120px', 'text-align': 'left', 'font-size': '13px', 'padding': '0 5px'} },
          { field: 'resourceCategory', title: 'Resource Category', style: {'width':'135px', 'text-align': 'left', 'font-size': '13px', 'padding': '0 5px'}}
      ]
    };
  }

  ngOnInit() {
    this.data = [];
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.qualificationsReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.qualificationsReportFilter.incidentId = 0;
    } else {
      this.qualificationsReportFilter.incidentGroupId = 0;
      this.qualificationsReportFilter.incidentId = this.incident.id;
    }
    this.loadDataForGrid();
    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.qualificationsReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.qualificationsReportFilter.incidentId = 0;
        } else {
          this.qualificationsReportFilter.incidentGroupId = 0;
          this.qualificationsReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
        this.loadDataForGrid();
    });
  }

  loadDataForGrid() {
    this.itemCodeService.getGrid(this.qualificationsReportFilter.incidentId, this.qualificationsReportFilter.incidentGroupId, true)
    .subscribe(data => {
      _.each(data.recordset, obj => {
        obj.resourceCategory = obj.requestCategoryVo.code;
      });
      this.data = data.recordset;
      this.sortListOrigin = _.cloneDeep(this.data);
      this.sortListOriginV2 = _.cloneDeep(this.data);
    });
  }

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
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
      if (_.findIndex(this[to], {id: this[indexType][indexSelected]}) === -1) {
        this[to].push(elementCopy);
      }
      if (elementCopy) {
        this[from] = _.filter(this[from], function(x) { return x.id !== elementCopy.id; });
      }
    }

    this[to] = _.cloneDeep(this[to]);
    this[indexType] = [];
    this[shiftIndex] = '';
  }

  moveAll(from, to, indexType, shiftIndex) {
    let temp = [];
    if (from === 'data') {
      temp = _.filter(this.data, obj => {
        let flg = true;
        for (const key in this.filterObject) {
          if (this.filterObject.hasOwnProperty(key)) {
            if (!obj[key].includes(this.filterObject[key])) {
              flg = false;
              break;
            }
          }
        }
        return flg;
      });
    } else {
      temp = _.cloneDeep(this[from]);
    }

    for (let indexSelected = 0; indexSelected < temp.length; indexSelected++) {
      let elementCopy = temp[indexSelected];
      if (elementCopy) {
        this[from] = _.filter(this[from], function(x) { return x.id !== elementCopy.id; });
      }
      if (elementCopy && _.findIndex(this[to], {id: elementCopy.id}) === -1) {
        this[to].push(elementCopy);
      }
    }
    this[to] = _.cloneDeep(this[to]);
    this[indexType] = [];
    this[shiftIndex] = '';
    this.sortListOriginV2 = _.cloneDeep(this.data);
  }

  clear() {
    this.data = _.cloneDeep(this.sortListOrigin);
    this.filterObject = {};
  }

  generateReport() {
    this.qualificationsReportFilter.traineesOnly = false;
    this.qualificationsReportFilter.excludeTrainees = false;
    if (this.optionFilter !== 'allResources') {
      this.qualificationsReportFilter[this.optionFilter] = true;
    }
    this.qualificationsReportFilter.selectedKinds = [];
    _.each(this.dataSelected, obj => {
      let kindVo = <KindVo>{};
      kindVo.code = obj.code;
      kindVo.description = obj.description;
      this.qualificationsReportFilter.selectedKinds.push(kindVo);
    });
    this.reportPlansService.generateQualificationsReport(this.qualificationsReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }

  restoreDefault() {
    this.optionFilter = 'allResources';
    this.data = _.cloneDeep(this.sortListOrigin);
    this.sortListOriginV2 = _.cloneDeep(this.data);
    this.dataSelected = [];
    this.shiftIndex = '';
    this.indexSelected = [];
    this.indexSelectedCopy = [];
    this.filterObject = {};
  }

  filterChange(event, dataObject) {
    this.filterObject = event;
  }
}
