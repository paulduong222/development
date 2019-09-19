import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportCostComponent } from '../report-cost.component';
import { AnalysisReportFilter } from 'src/app/data/filter/analysis-report-filter';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { ReportCostService } from 'src/app/service/report-cost.service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import * as _ from 'lodash';

@Component({
  selector: 'app-analysis-report',
  templateUrl: './analysis-report.component.html',
  styleUrls: ['./analysis-report.component.css',
              '../report-cost.component.css']
})
export class AnalysisReportComponent extends ReportCostComponent implements OnInit {
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  analysisReportFilter: AnalysisReportFilter = <AnalysisReportFilter> {
    analysisReport: 'resourceCost',
    itemCodeOrResource: 'itemCode',
    analysisReportFilter: 'noActualTimePosted',
    exceeds10000: 10000,
    threeOrMoreDays: 3
  };
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  incident: any;
  additionalFilter: any = 'isIncludeAllAircraftType'
  title: string;
  days: string;
  costExceeds: string;
  resourceCostSelected: boolean;
  exceptionSelected: boolean;
  byCostSelected: boolean;
  byCostOverheadSelected: boolean;

  constructor(
    public reportCostService: ReportCostService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService
  ) {
    super();
  }

  ngOnInit() {
    this.title = "Analysis Resource Cost";
    super.ngOnInit();
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.analysisReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.analysisReportFilter.incidentId = 0;
    } else {
      this.analysisReportFilter.incidentGroupId = 0;
      this.analysisReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.analysisReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.analysisReportFilter.incidentId = 0;
        } else {
          this.analysisReportFilter.incidentGroupId = 0;
          this.analysisReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
  }

  convertDate(dt) {
    return {
      dateString: + "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear(),
      timeString: ("0" + dt.getHours()).slice(-2)   + ":" + ("0" + dt.getMinutes()).slice(-2)
    }
  }

  generateReport() {
    if (this.analysisReportFilter.dateRangeIncluded) {
      if (this.dtStart.getFormattedDate()) {
        this.analysisReportFilter.startDateVo = this.convertDate(new Date(this.dtStart.getFormattedDate()));
      }
      if (this.dtEnd.getFormattedDate()) {
        this.analysisReportFilter.endDateVo = this.convertDate(new Date(this.dtEnd.getFormattedDate()));
      }
    }
    
    this.analysisReportFilter = _.mapValues(this.analysisReportFilter, (obj) => {
      if (typeof(obj) == 'boolean') {
        return false;
      }
      return obj;
    });

    if (this.analysisReportFilter.incidentGroupId > 0) {
      this.analysisReportFilter.isIncidentGroup = true;
    }

    this.reportCostService.generateAnalysisReport(this.analysisReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
