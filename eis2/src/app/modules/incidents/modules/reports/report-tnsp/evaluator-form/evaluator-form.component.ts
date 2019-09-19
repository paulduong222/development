import { Component, OnInit } from '@angular/core';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';
import { DialogueData } from 'src/app/data/rest/dialogue-data';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingSpecialistReportService } from 'src/app/service/training-specialist-report.service';
import { TrainingSpecialistReportFilter } from 'src/app/data/filter/training-specialist-report-filter';

@Component({
  selector: 'app-evaluator-form',
  templateUrl: './evaluator-form.component.html',
  styleUrls: ['./evaluator-form.component.css']
})
export class EvaluatorFormComponent implements OnInit {

  constructor(private notificationService: NotificationService,
              private trainingSpecialistReportService: TrainingSpecialistReportService) { }

  ngOnInit() {
  }

  generateReport() {
    let tnspReportFilter = <TrainingSpecialistReportFilter> {};

    this.trainingSpecialistReportService.generateEvaluatorFormReport(tnspReportFilter)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        if (data['resultObject']) {
          window.open(String(data['resultObject']), "_blank");
        }
    }); 
  }

}
