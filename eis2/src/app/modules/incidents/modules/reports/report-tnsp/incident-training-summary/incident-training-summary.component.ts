import { Component, OnInit, ViewChild } from '@angular/core';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingSpecialistReportService } from 'src/app/service/training-specialist-report.service';
import { IncidentTrainingSummaryReportFilter } from 'src/app/data/filter/incident-training-summary-report-filter';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';

@Component({
  selector: 'app-incident-training-summary',
  templateUrl: './incident-training-summary.component.html',
  styleUrls: ['./incident-training-summary.component.css']
})
export class IncidentTrainingSummaryComponent implements OnInit {

  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  @ViewChild('promptModal') promptModal: PromptModalComponent;

  incidentId: number = 0;
  incidentGroupId: number = 0;

  constructor(private notificationService: NotificationService,
              private trainingSpecialistReportService: TrainingSpecialistReportService) { }

  ngOnInit() {
    
  }

  getDefaultDates() {
    this.dtEnd.writeValue(new Date());
    this.getEarliestStartDate();
  }

  getEarliestStartDate() {
    this.trainingSpecialistReportService.getEarliestStartDate(this.incidentId, this.incidentGroupId)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_EARLIEST_START_DATE') {
          this.dtStart.writeValue(data['resultObject']);
        }
    });     
  }
  
  generateReport() {
    
    if (this.dtStart.value === '' || this.dtStart.value === null || this.dtEnd.value === '' || this.dtEnd.value === null ) {
      this.showPrompt('Incident Training Summary'
      , 'Start and End Dates are required fields.'
      , 'OK');
    } else {
      let itsReportFilter = <IncidentTrainingSummaryReportFilter> {
        incidentId:this.incidentId,
        incidentGroupId: this.incidentGroupId,
        startDateVo: {},
        endDateVo: {}
      };

      itsReportFilter.startDateVo.dateString = this.dtStart.getFormattedDate();
      itsReportFilter.endDateVo.dateString = this.dtEnd.getFormattedDate();

      this.trainingSpecialistReportService.generateIncidentTrainingSummaryReport(itsReportFilter)
        .subscribe(data => {
          this.notificationService.inspectResult(data);
          if (data['resultObject']) {
            window.open(String(data['resultObject']), "_blank");
          }
      });     
    }    
  }

  showPrompt(title, msg, btn1) {
    this.promptModal.reset();
    this.promptModal.promptTitle = title;
    this.promptModal.promptMessage1 = msg;
    this.promptModal.button1Label = btn1;
    this.promptModal.openModal();
  }

  promptModalActionResult() {
    this.promptModal.closeModal();
  }

}
