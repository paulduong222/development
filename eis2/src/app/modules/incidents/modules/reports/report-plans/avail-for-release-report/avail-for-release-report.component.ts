import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { AvailableForReleaseReportFilter } from 'src/app/data/filter/available-for-release-report-filter';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-avail-for-release-report',
  templateUrl: './avail-for-release-report.component.html',
  styleUrls: ['./avail-for-release-report.component.css']
})
export class AvailForReleaseReportComponent implements OnInit {
  tooltipBox: boolean = false;
  incident: any;
  availableForReleaseReportFilter: AvailableForReleaseReportFilter = <AvailableForReleaseReportFilter>{};
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  constructor(
    public reportPlansService: ReportPlansService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.promptModal.promptTitle = 'Incident Resources';
    this.promptModal.promptMessage1 = 'Do you want the system to mark the application Resource records as Tentative Release Dispatch Notified?';
    this.promptModal.button1Label = 'Yes';
    this.promptModal.button2Label = 'No';
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.availableForReleaseReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.availableForReleaseReportFilter.incidentId = 0;
    } else {
      this.availableForReleaseReportFilter.incidentGroupId = 0;
      this.availableForReleaseReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.availableForReleaseReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.availableForReleaseReportFilter.incidentId = 0;
        } else {
          this.availableForReleaseReportFilter.incidentGroupId = 0;
          this.availableForReleaseReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
  }

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
  }

  handleCheckbox(event) {
    if (this.availableForReleaseReportFilter[event.target.value]) {
      this.availableForReleaseReportFilter[event.target.value] = false;
    } else {
      this.availableForReleaseReportFilter[event.target.value] = true;
    }
  }

  promptActionResult(action) {
    if ( action === 'Yes') {
      this.availableForReleaseReportFilter.markDispatchNotified = true;
      this.promptModal.closeModal();
    }
    if ( action === 'No') {
      this.availableForReleaseReportFilter.markDispatchNotified = false;
      this.promptModal.closeModal();
    }
    this.generateReport();
  }

  confirm() {
    this.promptModal.openModal();
  }

  generateReport() {
    this.reportPlansService.generateAvailableForReleaseReport(this.availableForReleaseReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }

}
