import { Component, OnInit } from '@angular/core';
import { ReportCostComponent } from '../report-cost.component';
import { CostShareReportFilter } from 'src/app/data/filter/cost-share-report-filter';
import { ReportCostService } from 'src/app/service/report-cost.service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import * as _ from 'lodash';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-cost-share-report',
  templateUrl: './cost-share-report.component.html',
  styleUrls: ['./cost-share-report.component.css',
              '../report-cost.component.css']
})
export class CostShareReportComponent extends ReportCostComponent implements OnInit {
  costShareReportFilter: CostShareReportFilter = <CostShareReportFilter> {};
  title: string;
  filterBy: any = 'isSummaryReport';
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  incident: any;
  constructor(
    public reportCostService: ReportCostService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService
  ) {
    super();
  }

  ngOnInit() {
    this.title = "Cost Share Summary";
    this.tooltipBox = true;
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.costShareReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.costShareReportFilter.incidentId = 0;
    } else {
      this.costShareReportFilter.incidentGroupId = 0;
      this.costShareReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.costShareReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.costShareReportFilter.incidentId = 0;
        } else {
          this.costShareReportFilter.incidentGroupId = 0;
          this.costShareReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
  }

  generateReport() {    
    this.costShareReportFilter = _.mapValues(this.costShareReportFilter, (obj) => {
      if (typeof(obj) == 'boolean') {
        return false;
      }
      return obj;
    });

    if (this.costShareReportFilter.incidentGroupId > 0) {
      this.costShareReportFilter.isIncidentGroup = true;
    }

    this.costShareReportFilter[this.filterBy] = true;
    this.reportCostService.generateCostShareReport(this.costShareReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }

}
