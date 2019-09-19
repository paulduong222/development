import { Component, OnInit } from '@angular/core';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';
import { DialogueData } from 'src/app/data/rest/dialogue-data';
import { TrainingSpecialistReportService } from 'src/app/service/training-specialist-report.service';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingSpecialistReportFilter } from 'src/app/data/filter/training-specialist-report-filter';

@Component({
  selector: 'app-performance-evaluation',
  templateUrl: './performance-evaluation.component.html',
  styleUrls: ['./performance-evaluation.component.css']
})
export class PerformanceEvaluationComponent implements OnInit {

  constructor(private notificationService: NotificationService,
              private trainingSpecialistReportService: TrainingSpecialistReportService) { }

  ngOnInit() {
  }

  generateReport() {
    let tnspReportFilter = <TrainingSpecialistReportFilter> {};

    this.trainingSpecialistReportService.generatePerformanceEvaluationReport(tnspReportFilter)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        if (data['resultObject']) {
          window.open(String(data['resultObject']), "_blank");
        }
    });   
  }

}
