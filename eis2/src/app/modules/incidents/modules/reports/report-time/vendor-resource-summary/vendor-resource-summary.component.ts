import { Component, OnInit } from '@angular/core';
import * as _ from 'lodash';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import { ReportTimeService } from 'src/app/service/report-time.service';
import { NotificationService } from 'src/app/service/notification-service';
import { VendorResourceSummaryReportFilter } from 'src/app/data/filter/vendor-resource-summary-filter';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-vendor-resource-summary',
  templateUrl: './vendor-resource-summary.component.html',
  styleUrls: ['./vendor-resource-summary.component.css']
})
export class VendorResourceSummaryComponent implements OnInit {
  incident: any;
  groupBy: any = 'groupByNone';
  sortBy: any = 'sortByRequestNumber';
  vendorResourceSummaryReportFilter: VendorResourceSummaryReportFilter = <VendorResourceSummaryReportFilter>{};
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  constructor(
    public reportTimeService: ReportTimeService,
    private notificationService: NotificationService,
    private incidentSelectorService: IncidentSelectorService
  ) {}

  ngOnInit() {
    this.incident = this.incidentSelectorService.selectedGridRow;
    console.log(this.incident);
    this.vendorResourceSummaryReportFilter.incidentName = this.incident.name;
    if (this.incident.incidentGroupId) {
      this.vendorResourceSummaryReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.vendorResourceSummaryReportFilter.incidentTag = '';
      this.vendorResourceSummaryReportFilter.incidentId = 0;
    } else {
      this.vendorResourceSummaryReportFilter.incidentGroupId = 0;
      this.vendorResourceSummaryReportFilter.incidentTag = this.vendorResourceSummaryReportFilter.incidentNumber = this.incident.incidentNumber;
      this.vendorResourceSummaryReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        this.vendorResourceSummaryReportFilter.incidentName = this.currentSelectedIncidentSelectorVo.name;
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.vendorResourceSummaryReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.vendorResourceSummaryReportFilter.incidentTag = '';
          this.vendorResourceSummaryReportFilter.incidentId = 0;
        } else {
          this.vendorResourceSummaryReportFilter.incidentGroupId = 0;
          this.vendorResourceSummaryReportFilter.incidentTag = this.vendorResourceSummaryReportFilter.incidentNumber = this.currentSelectedIncidentSelectorVo.incidentNumber;
          this.vendorResourceSummaryReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
  }

  generateReport() {
    this.defaultValues()
    this.vendorResourceSummaryReportFilter.printDraftInvoice = false;
    this.vendorResourceSummaryReportFilter.printOriginalInvoice = false;
    this.vendorResourceSummaryReportFilter.printDuplicateOriginalInvoice = false;
    this.vendorResourceSummaryReportFilter.printDeductionsAndInvoice = false;
    this.vendorResourceSummaryReportFilter.printInvoiceOnly = false;
    this.vendorResourceSummaryReportFilter.printItemizedDeductionsOnly = false;
    this.vendorResourceSummaryReportFilter[this.groupBy] = true;
    this.vendorResourceSummaryReportFilter[this.sortBy] = true;

    this.reportTimeService.generateVendorResourceSummaryReport(this.vendorResourceSummaryReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data)
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      } else {
        alert('No data available')
      }
    });
  }

  defaultValues(){
    this.vendorResourceSummaryReportFilter.groupByNone = false;
    this.vendorResourceSummaryReportFilter.groupByItemCode = false;
    this.vendorResourceSummaryReportFilter.groupByVendor = false;
    this.vendorResourceSummaryReportFilter.groupByHireDate = false;

    this.vendorResourceSummaryReportFilter.sortByRequestNumber = false;
    this.vendorResourceSummaryReportFilter.sortByItemCode = false;
    this.vendorResourceSummaryReportFilter.sortByVendor = false;
    this.vendorResourceSummaryReportFilter.sortByHireDate = false;
  }
}
