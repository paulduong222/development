import { Component, OnInit, ViewChild } from '@angular/core';
import * as _ from 'lodash';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import { ReportTimeService } from 'src/app/service/report-time.service';
import { AgencyService } from 'src/app/service/agency.service';
import { NotificationService } from 'src/app/service/notification-service';
import { MissingDaysOfPostingsReportFilter } from 'src/app/data/filter/missing-days-of-postings-report-filter';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { DropdownData } from 'src/app/data/dropdowndata';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-missing-days-of-postings',
  templateUrl: './missing-days-of-postings.component.html',
  styleUrls: ['./missing-days-of-postings.component.css']
})
export class MissingDaysOfPostingsComponent implements OnInit {
  @ViewChild('startDate') startDate: EisDatepickerComponent;
  @ViewChild('endDate') endDate: EisDatepickerComponent;
  @ViewChild('promptModal') promptModal: PromptModalComponent;

  dropdownData: DropdownData[] = [];
  missingDaysOfPostingsReportFilter: MissingDaysOfPostingsReportFilter = <MissingDaysOfPostingsReportFilter>{
    isIncidentGroup: false,
    personnelOrVendor: 'Personnel',
    noneOrAgency: 'None',
    reqNumOrResNameOrAgencyOrCode: 'Request Number',
    reqNumOrResName: 'Request Number',
    excludeDemobReassigned: false
  }
  incident: any;
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{}
  incidentSelectorSubscription;
  constructor(
    public reportTimeService: ReportTimeService,
    public agencyService: AgencyService,
    private notificationService: NotificationService,
    private incidentSelectorService: IncidentSelectorService
  ) {}
  ngOnInit() {
    let today = new Date();
    this.endDate.writeValue(today);
    this.incident = this.incidentSelectorService.selectedGridRow;
    this.missingDaysOfPostingsReportFilter.incidentName = this.incident.name;
    if (this.incident.incidentGroupId) {
      this.missingDaysOfPostingsReportFilter.isIncidentGroup = true;
      this.missingDaysOfPostingsReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.missingDaysOfPostingsReportFilter.incidentTag = '';
      this.missingDaysOfPostingsReportFilter.incidentId = 0;
    } else {
      this.missingDaysOfPostingsReportFilter.isIncidentGroup = false;
      this.missingDaysOfPostingsReportFilter.incidentGroupId = 0;
      this.missingDaysOfPostingsReportFilter.incidentTag = this.missingDaysOfPostingsReportFilter.incidentNumber = this.incident.incidentNumber;
      this.missingDaysOfPostingsReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        this.missingDaysOfPostingsReportFilter.incidentName = this.currentSelectedIncidentSelectorVo.name;
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.missingDaysOfPostingsReportFilter.isIncidentGroup = true;
          this.missingDaysOfPostingsReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.missingDaysOfPostingsReportFilter.incidentTag = '';
          this.missingDaysOfPostingsReportFilter.incidentId = 0;
        } else {
          this.missingDaysOfPostingsReportFilter.isIncidentGroup = false;
          this.missingDaysOfPostingsReportFilter.incidentGroupId = 0;
          this.missingDaysOfPostingsReportFilter.incidentTag = this.missingDaysOfPostingsReportFilter.incidentNumber = this.currentSelectedIncidentSelectorVo.incidentNumber;
          this.missingDaysOfPostingsReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
        this.getAgency();
    });
    this.getAgency();
    this.promptModal.promptTitle = 'Missing Days of Postings';
    this.promptModal.button1Label = 'Ok';
  }

  getAgency(){
    this.agencyService.getGrid().subscribe(data=>{
      let items:any = data.recordset
      items.forEach(item => {
        this.dropdownData.push({id:item.id,code:item.agencyCd,desc:item.agencyNm})
      });
    })
  }

  setAgencyName(event){
    this.missingDaysOfPostingsReportFilter.agencyName = event.desc;
  }

  convertDate(dt) {
    console.log(dt);
    return {
      dateString: + "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear(),
      timeString: ("0" + dt.getHours()).slice(-2)   + ":" + ("0" + dt.getMinutes()).slice(-2)
    }
  }

  promptActionResult(action) {
    if (action === 'Ok') {
      this.promptModal.closeModal();
    }
  }

  generateReport() {
    if (!this.startDate.getFormattedDate()) {
      this.notificationService.showError2('Start Date can not be empty', 'Error');
      return;
    }
    if (!this.endDate.getFormattedDate()) {
      this.notificationService.showError2('End Date can not be empty', 'Error');
      return;
    }
    if (new Date(this.startDate.getFormattedDate()) > new Date(this.endDate.getFormattedDate())) {
      this.notificationService.showError2('End Date cannot be before Start Date', 'Error');
      return;
    }
    this.missingDaysOfPostingsReportFilter.printDraftInvoice = false;
    this.missingDaysOfPostingsReportFilter.printOriginalInvoice = false;
    this.missingDaysOfPostingsReportFilter.printDuplicateOriginalInvoice = false;
    this.missingDaysOfPostingsReportFilter.printDeductionsAndInvoice = false;
    this.missingDaysOfPostingsReportFilter.printInvoiceOnly = false;
    this.missingDaysOfPostingsReportFilter.printItemizedDeductionsOnly = false;
    // delete this.missingDaysOfPostingsReportFilter.startDate;
    if (this.startDate.getFormattedDate()) {
      this.missingDaysOfPostingsReportFilter.startDate = new Date(this.startDate.getFormattedDate());
      this.missingDaysOfPostingsReportFilter.startDateChar = this.startDate.getFormattedDate();
      this.missingDaysOfPostingsReportFilter.startDateVo = this.convertDate(new Date(this.startDate.getFormattedDate()));
    }
    // delete this.missingDaysOfPostingsReportFilter.endDate;
    if (this.endDate.getFormattedDate()) {
      this.missingDaysOfPostingsReportFilter.endDate = new Date(this.endDate.getFormattedDate());
      this.missingDaysOfPostingsReportFilter.endDateDbChar = this.missingDaysOfPostingsReportFilter.endDateChar = this.endDate.getFormattedDate();
      this.missingDaysOfPostingsReportFilter.endDateVo = this.convertDate(new Date(this.endDate.getFormattedDate()));
    }

    console.log(this.missingDaysOfPostingsReportFilter);
    this.reportTimeService.generateMissingDaysOfPostingsReport(this.missingDaysOfPostingsReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data)
      if (data['resultObject']) {
        window.open(String(data['resultObject']), '_blank');
      }
    });
  }
}
