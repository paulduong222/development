import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportCostComponent } from '../report-cost.component';
import { GroupCategoryTotalReportFilter } from 'src/app/data/filter/group-category-total-report-filter';
import { ReportCostService } from 'src/app/service/report-cost.service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import * as _ from 'lodash';

@Component({
  selector: 'app-group-category-summary-report',
  templateUrl: './group-category-summary-report.component.html',
  styleUrls: ['./group-category-summary-report.component.css',
              '../report-cost.component.css']
})
export class GroupCategorySummaryReportComponent extends ReportCostComponent implements OnInit {
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  groupCategoryTotalReportFilter: GroupCategoryTotalReportFilter = <GroupCategoryTotalReportFilter>{
    selectedReportGroup: 'Incident',
    additionalFilters: [],
  };
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  incident: any;
  filterBy: any = 'isReportOnly';
  additionalFilter: any = 'includeAllItemCode';
  additionalFilters: any = [];
  listCheckBox: any;
  listCheckBoxAble = ['selectiveAccountingCode', 'selectiveAgency', 'selectivePaymentAgencies', 'selectiveCostGroups', 'selectiveHomeUnits', 'subCategory'];
  constructor(
    public reportCostService: ReportCostService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService
  ) {
    super();
  }

  ngOnInit() {
    super.ngOnInit();
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.groupCategoryTotalReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.groupCategoryTotalReportFilter.incidentId = 0;
    } else {
      this.groupCategoryTotalReportFilter.incidentGroupId = 0;
      this.groupCategoryTotalReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
      this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
      if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
        this.groupCategoryTotalReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
        this.groupCategoryTotalReportFilter.incidentId = 0;
      } else {
        this.groupCategoryTotalReportFilter.incidentGroupId = 0;
        this.groupCategoryTotalReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
      }
      this.setDataList();
    });
    this.setDataList();
  }
  
  setDataList() {
    let id = this.groupCategoryTotalReportFilter.incidentId;
    let isGroupIncident = false;
    if (this.groupCategoryTotalReportFilter.incidentGroupId > 0) {
      id = this.groupCategoryTotalReportFilter.incidentGroupId;
      isGroupIncident = true;
    }
    this.additionalFilters = [];
    if (this.groupCategoryTotalReportFilter.selectedReportGroup === 'Accounting Code') {
      this.additionalFilter = 'includeAllAccountingCode';
      this.reportCostService.getGroupCategoryTotalFilter(id, 'ACCOUNTING_CODE', isGroupIncident).subscribe(rs => {
        this.listCheckBox = rs.resultObject;
      });
      return;
    }
    if (this.groupCategoryTotalReportFilter.selectedReportGroup === 'Agency') {
      this.additionalFilter = 'includeAllAgency';
      this.reportCostService.getGroupCategoryTotalFilter(id, 'AGENCY_CODE', isGroupIncident).subscribe(rs => {
        this.listCheckBox = rs.resultObject;
      });
      return;
    }
    if (this.groupCategoryTotalReportFilter.selectedReportGroup === 'Payment Agency') {
      this.additionalFilter = 'includeAllPaymentAgency';
      this.reportCostService.getGroupCategoryTotalFilter(id, 'PAYMENT_AGENCY_CODE', isGroupIncident).subscribe(rs => {
        this.listCheckBox = rs.resultObject;
      });
      return;
    }
    if (this.groupCategoryTotalReportFilter.selectedReportGroup === 'Cost Group') {
      this.additionalFilter = 'includeAllCostGroup';
      this.reportCostService.getGroupCategoryTotalFilter(id, 'COST_GROUP_NAME', isGroupIncident).subscribe(rs => {
        this.listCheckBox = rs.resultObject;
      });
      return;
    }
    if (this.groupCategoryTotalReportFilter.selectedReportGroup === 'Unit ID') {
      this.additionalFilter = 'includeAllUnitID';
      this.reportCostService.getGroupCategoryTotalFilter(id, 'UNIT_CODE', isGroupIncident).subscribe(rs => {
        this.listCheckBox = rs.resultObject;
      });
      return;
    }
    if (this.groupCategoryTotalReportFilter.selectedReportGroup === 'Incident') {
      this.additionalFilter = 'includeAllItemCode';
      this.reportCostService.getGroupCategoryTotalFilter(id, 'INCIDENT', isGroupIncident).subscribe(rs => {
        this.listCheckBox = rs.resultObject;
      });
      return;
    }
  }
  convertDate(dt) {
    return {
      dateString: + "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear(),
      timeString: ("0" + dt.getHours()).slice(-2)   + ":" + ("0" + dt.getMinutes()).slice(-2)
    }
  }

  handleCheckbox(label) {
    if (this.additionalFilters.includes(label)) {
      _.pull(this.additionalFilters, label);
    } else {
      this.additionalFilters.push(label);
    }
  }

  generateReport() {
    this.groupCategoryTotalReportFilter.additionalFilters = [];
    if (this.listCheckBoxAble.includes(this.additionalFilter)) {
      this.groupCategoryTotalReportFilter.additionalFilters = this.additionalFilters;
    }
    if (this.groupCategoryTotalReportFilter.isDateRangeSelected) {
      if (this.dtStart.getFormattedDate()) {
        this.groupCategoryTotalReportFilter.startDateVo = this.convertDate(new Date(this.dtStart.getFormattedDate()));
      }
      if (this.dtEnd.getFormattedDate()) {
        this.groupCategoryTotalReportFilter.endDateVo = this.convertDate(new Date(this.dtEnd.getFormattedDate()));
      }
    }
    
    this.groupCategoryTotalReportFilter = _.mapValues(this.groupCategoryTotalReportFilter, (obj) => {
      if (typeof(obj) == 'boolean') {
        return false;
      }
      return obj;
    });

    if (this.groupCategoryTotalReportFilter.incidentGroupId > 0) {
      this.groupCategoryTotalReportFilter.isIncidentGroup = true;
    }
    
    if (this.additionalFilter && this.additionalFilter !== 'subCategory') {
      this.groupCategoryTotalReportFilter[this.additionalFilter] = true;
    }
    this.groupCategoryTotalReportFilter[this.filterBy] = true;
    this.reportCostService.generateGroupCategorySummaryReport(this.groupCategoryTotalReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }

}
