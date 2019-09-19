import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingSpecialistReportService } from 'src/app/service/training-specialist-report.service';
import { TrainingSpecialistReportFilter } from 'src/app/data/filter/training-specialist-report-filter';

@Component({
  selector: 'app-home-unit-letter',
  templateUrl: './home-unit-letter.component.html',
  styleUrls: ['./home-unit-letter.component.css']
})
export class HomeUnitLetterComponent implements OnInit {

  constructor(private notificationService: NotificationService,
              private trainingSpecialistReportService: TrainingSpecialistReportService) { 
  }

  ngOnInit() {
  }

  generateReport() {
    let tnspReportFilter = <TrainingSpecialistReportFilter> {};

    this.trainingSpecialistReportService.generateHomeUnitLetterReport(tnspReportFilter)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        if (data['resultObject']) {
          window.open(String(data['resultObject']), "_blank");
        }
    });     
  }

}
