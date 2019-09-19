import { Component, OnInit, ViewChild, EventEmitter, Output } from '@angular/core';
import { TrainingSpecialistReportFilter } from 'src/app/data/filter/training-specialist-report-filter';
import { TrainingSpecialistReportService } from 'src/app/service/training-specialist-report.service';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingSpecialistVo } from 'src/app/data/training-specialist-vo';
import { ReportsEvaluationModalComponent } from '../modals/reports-evaluation-modal/reports-evaluation-modal.component';
import { RscTrainingTrainerVo } from 'src/app/data/rsc-training-trainer-vo';

@Component({
  selector: 'app-training-reports',
  templateUrl: './training-reports.component.html',
  styleUrls: ['./training-reports.component.css']
})
export class TrainingReportsComponent implements OnInit {
  @Output() promptEventTrainingReports = new EventEmitter(); 
  @ViewChild('reportsEvaluationModal') reportsEvaluationModal: ReportsEvaluationModalComponent;
  resourceTrainingId: number = 0;
  rscTrainingTrainerVos: RscTrainingTrainerVo[] = [];
  reportName = '';
  incidentId: number = 0;
  incidentGroupId: number = 0;
  trainingSpecialistVos: TrainingSpecialistVo[] = [];

  constructor(private notificationService: NotificationService,
              private trainingSpecialistReportService: TrainingSpecialistReportService) { }

  ngOnInit() {
  }

 generateReport(reportName: string) {
    if (this.resourceTrainingId > 0) {
     this.reportName = reportName;

       switch (this.reportName) {
         case 'Data Form':
           this.getTrainingSpecialists();
           break;
         case 'Evaluation Record':
           this.processForm();
           break;
         case 'Performance Evaluation':
           this.processForm();
           break;
         case 'Home Unit Letter':
           this.getTrainingSpecialists();
           break;
         case 'Exit Interview':
           this.getTrainingSpecialists();
           break;
       }
    } else {
      this.promptEventTrainingReports.emit();
   }
 }

 previewPrintEvent(event) {
   let tnspReportFilter: TrainingSpecialistReportFilter = event;
   tnspReportFilter.resourceTrainingId = this.resourceTrainingId;

   switch (this.reportName) {
     case 'Data Form':
       this.generateDataFormReport(tnspReportFilter);
       break;
     case 'Evaluation Record':
       this.generateEvaluatorFormReport(tnspReportFilter);
       break;
     case 'Performance Evaluation':
       this.generatePerformanceEvaluationReport(tnspReportFilter);
       break;
     case 'Home Unit Letter':
       this.generateHomeUnitLetterReport(tnspReportFilter);
       break;
     case 'Exit Interview':
       this.generateExitInterviewReport(tnspReportFilter);
       break;
   }
 }

 generateDataFormReport(tnspReportFilter: TrainingSpecialistReportFilter) {
   
   this.trainingSpecialistReportService.generateDataFormReport(tnspReportFilter)
     .subscribe(data => {
       this.notificationService.inspectResult(data);
       if (data['resultObject']) {
         window.open(String(data['resultObject']), "_blank");
       }
   });
 }

 generateEvaluatorFormReport(tnspReportFilter: TrainingSpecialistReportFilter) {

   this.trainingSpecialistReportService.generateEvaluatorFormReport(tnspReportFilter)
     .subscribe(data => {
       this.notificationService.inspectResult(data);
       if (data['resultObject']) {
         window.open(String(data['resultObject']), "_blank");
       }
   });
 }

 generatePerformanceEvaluationReport(tnspReportFilter: TrainingSpecialistReportFilter) {
   
   this.trainingSpecialistReportService.generatePerformanceEvaluationReport(tnspReportFilter)
     .subscribe(data => {
       this.notificationService.inspectResult(data);
       if (data['resultObject']) {
         window.open(String(data['resultObject']), "_blank");
       }
   });
 }

 generateHomeUnitLetterReport(tnspReportFilter: TrainingSpecialistReportFilter) {
   
   this.trainingSpecialistReportService.generateHomeUnitLetterReport(tnspReportFilter)
     .subscribe(data => {
       this.notificationService.inspectResult(data);
       if (data['resultObject']) {
         window.open(String(data['resultObject']), "_blank");
       }
   });
 }  

 generateExitInterviewReport(tnspReportFilter: TrainingSpecialistReportFilter) {

   this.trainingSpecialistReportService.generateExitInterviewReport(tnspReportFilter)
     .subscribe(data => {
       this.notificationService.inspectResult(data);
       if (data['resultObject']) {
         window.open(String(data['resultObject']), "_blank");
       }
   });
 }

 getTrainingSpecialists() {

   this.trainingSpecialistReportService.getTrainingSpecialistList(this.incidentId, this.incidentGroupId, this.resourceTrainingId)
     .subscribe(data => {
       this.notificationService.inspectResult(data);
       if ( data['courseOfActionVo']['coaName'] === 'GET_TRAINING_SPECIALIST_LIST') {
         this.trainingSpecialistVos = data['resultObject'] as TrainingSpecialistVo[];
         this.processForm();  
       }
   });   
 }

 processForm() {
   let showTrainingSpecialistsDD: boolean = false;
   let showEvaluatorsDD: boolean = false;
   let tnspReportFilter = <TrainingSpecialistReportFilter> {
     resourceTrainingId: this.resourceTrainingId,
     blankForm: 'no',
     trainingSpecialistId: 0,
     trainerId: 0,
     evaluationRecordNumber: ''
   };

   switch (this.trainingSpecialistVos.length) {
     case 0:
       break;
     case 1:
       tnspReportFilter.trainingSpecialistId = this.trainingSpecialistVos[0].id;
       break;
     default:
       showTrainingSpecialistsDD = true;
       break;
   }

   switch (this.rscTrainingTrainerVos.length) {
     case 0:
       break;
     case 1:
       tnspReportFilter.trainerId = this.rscTrainingTrainerVos[0].id;
       break;
     default:
       showEvaluatorsDD = true;
       break;
   }

   switch (this.reportName) {
     case 'Data Form':
       if (showTrainingSpecialistsDD || showEvaluatorsDD) {
         this.openEvaluatorModal(tnspReportFilter);
       } else {
         this.generateDataFormReport(tnspReportFilter);
       }
       break;
     case 'Evaluation Record':
       this.openEvaluatorModal(tnspReportFilter);
       break;
     case 'Performance Evaluation':
       if (showEvaluatorsDD) {
         this.openEvaluatorModal(tnspReportFilter);
       } else {
         this.generatePerformanceEvaluationReport(tnspReportFilter);
       }
       break;
     case 'Home Unit Letter':
       if (showTrainingSpecialistsDD) {
         this.openEvaluatorModal(tnspReportFilter);
       } else {
         this.generateHomeUnitLetterReport(tnspReportFilter);
       }
       break;
     case 'Exit Interview':
       if (showTrainingSpecialistsDD || showEvaluatorsDD) {
         this.openEvaluatorModal(tnspReportFilter);
       } else {
         this.generateExitInterviewReport(tnspReportFilter);
       }
       break;
   }

 }

 openEvaluatorModal(tnspReportFilter: TrainingSpecialistReportFilter) {

   this.reportsEvaluationModal.reportName = this.reportName;
   this.reportsEvaluationModal.windowTitle = 'Preview/Print ' + this.reportName;
   this.reportsEvaluationModal.showTrainingSpecialistsDD = false;
   this.reportsEvaluationModal.showEvaluatorsDD = false;
   this.reportsEvaluationModal.showEvaluationRecord = false;

   switch (this.reportName) {
     case 'Data Form':
       this.reportsEvaluationModal.showTrainingSpecialistsDD = this.trainingSpecialistVos.length > 0;
       this.reportsEvaluationModal.showEvaluatorsDD = this.rscTrainingTrainerVos.length > 0;
       break;
     case 'Evaluation Record':
       this.reportsEvaluationModal.showEvaluatorsDD = this.rscTrainingTrainerVos.length > 0;
       this.reportsEvaluationModal.showEvaluationRecord = true;
       break;
     case 'Performance Evaluation':
       this.reportsEvaluationModal.showEvaluatorsDD = this.rscTrainingTrainerVos.length > 0;
       break;
     case 'Home Unit Letter':
       this.reportsEvaluationModal.showTrainingSpecialistsDD = this.trainingSpecialistVos.length > 0;
       break;
     case 'Exit Interview':
       this.reportsEvaluationModal.showTrainingSpecialistsDD = this.trainingSpecialistVos.length > 0;
       this.reportsEvaluationModal.showEvaluatorsDD = this.rscTrainingTrainerVos.length > 0;
       break;
   }

   this.reportsEvaluationModal.setTrainingSpecialists(this.trainingSpecialistVos);
   this.reportsEvaluationModal.setEvaluators(this.rscTrainingTrainerVos);

   this.reportsEvaluationModal.openModal('reports-evaluation-modal');
 }

}
