import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ReportTnspViewComponent } from './report-tnsp-view/report-tnsp-view.component';
import { TrainingAssignmentsListComponent } from './training-assignments-list/training-assignments-list.component';
import { IncidentTrainingSummaryComponent } from './incident-training-summary/incident-training-summary.component';
import { DataFormComponent } from './data-form/data-form.component';
import { EvaluatorFormComponent } from './evaluator-form/evaluator-form.component';
import { PerformanceEvaluationComponent } from './performance-evaluation/performance-evaluation.component';
import { HomeUnitLetterComponent } from './home-unit-letter/home-unit-letter.component';
import { ExitInterviewComponent } from './exit-interview/exit-interview.component';
import { HomeUnitContactLabelsComponent } from './home-unit-contact-labels/home-unit-contact-labels.component';
import { SharedModule } from 'src/app/shared';

const routes: Routes = [
  { path: '', outlet: 'incidents-outlet', component: ReportTnspViewComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule
  ],
  declarations: [ReportTnspViewComponent, 
    TrainingAssignmentsListComponent, 
    IncidentTrainingSummaryComponent, 
    DataFormComponent, 
    EvaluatorFormComponent, 
    PerformanceEvaluationComponent, 
    HomeUnitLetterComponent, 
    ExitInterviewComponent, 
    HomeUnitContactLabelsComponent]
})
export class ReportTnspModule { }
