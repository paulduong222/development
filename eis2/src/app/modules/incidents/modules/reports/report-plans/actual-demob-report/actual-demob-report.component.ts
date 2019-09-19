import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { ActualDemobReportFilter } from 'src/app/data/filter/actual-demob-report-filter';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-actual-demob-report',
  templateUrl: './actual-demob-report.component.html',
  styleUrls: ['./actual-demob-report.component.css']
})
export class ActualDemobReportComponent implements OnInit {
  tooltipBox: boolean = false;
  incident: any;
  actualDemobReportFilter: ActualDemobReportFilter = <ActualDemobReportFilter>{};
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  constructor(
    public reportPlansService: ReportPlansService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.actualDemobReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.actualDemobReportFilter.incidentId = 0;
    } else {
      this.actualDemobReportFilter.incidentGroupId = 0;
      this.actualDemobReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.actualDemobReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.actualDemobReportFilter.incidentId = 0;
        } else {
          this.actualDemobReportFilter.incidentGroupId = 0;
          this.actualDemobReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
    this.promptModal.promptTitle = 'Incident Resources';
    this.promptModal.promptMessage1 = 'Do you want the system to mark the application Resource records as Actual Release Dispatch Notified?';
    this.promptModal.button1Label = 'Yes';
    this.promptModal.button2Label = 'No';
  }

  ngOnDestroy() {
    this.incidentSelectorSubscription.unsubscribe();
  }

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
  }

  handleCheckbox(event) {
    if (this.actualDemobReportFilter[event.target.value]) {
      this.actualDemobReportFilter[event.target.value] = false;
    } else {
      this.actualDemobReportFilter[event.target.value] = true;
    }
  }

  promptActionResult(action) {
    if ( action === 'Yes') {
      this.actualDemobReportFilter.markDispatchNotified = true;
      this.promptModal.closeModal();
    }
    if ( action === 'No') {
      this.actualDemobReportFilter.markDispatchNotified = false;
      this.promptModal.closeModal();
    }
    this.generateReport();
  }

  confirm() {
    this.promptModal.openModal();
  }

  generateReport() {
    this.reportPlansService.generateActualDemobReport(this.actualDemobReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
}
