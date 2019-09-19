import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { AirTravelRequestReportFilter } from 'src/app/data/filter/airTravel-request-report-filter';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-air-travel-req-report',
  templateUrl: './air-travel-req-report.component.html',
  styleUrls: ['./air-travel-req-report.component.css']
})
export class AirTravelReqReportComponent implements OnInit {
  airTravelRequestReportFilter: AirTravelRequestReportFilter = <AirTravelRequestReportFilter>{};
  tooltipBox: boolean = false;
  incident: any;
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
      this.airTravelRequestReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.airTravelRequestReportFilter.incidentId = 0;
    } else {
      this.airTravelRequestReportFilter.incidentGroupId = 0;
      this.airTravelRequestReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.airTravelRequestReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.airTravelRequestReportFilter.incidentId = 0;
        } else {
          this.airTravelRequestReportFilter.incidentGroupId = 0;
          this.airTravelRequestReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
    this.promptModal.promptTitle = 'Incident Resources';
    this.promptModal.promptMessage1 = 'Do you want the system to mark the application Resource records as Air Travel Dispatch Notified?';
    this.promptModal.button1Label = 'Yes';
    this.promptModal.button2Label = 'No';
  }

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
  }

  promptActionResult(action) {
    if (action === 'Yes') {
      this.airTravelRequestReportFilter.markDispatchNotified = true;
      this.promptModal.closeModal();
    }
    if (action === 'No') {
      this.airTravelRequestReportFilter.markDispatchNotified = false;
      this.promptModal.closeModal();
    }
    this.generateReport();
  }

  confirm() {
    this.promptModal.openModal();
  }

  generateReport() {
    this.reportPlansService.generateAirTravelRequestReport(this.airTravelRequestReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }

}
