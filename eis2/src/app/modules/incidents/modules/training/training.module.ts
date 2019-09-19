import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { IncResSharedModule } from '../../../inc-res-shared/inc-res-shared.module';
import { SharedModule } from 'src/app/shared';
import { AngularSplitModule } from 'angular-split';
import { TrainingViewComponent } from './training-view/training-view.component';
import { TrainingFormComponent } from './training-form/training-form.component';
import { AssignmentCloseoutModalComponent } from './modals/assignment-closeout-modal/assignment-closeout-modal.component';
import { TraineeTabComponent } from './training-form/trainee-tab/trainee-tab.component';
import { EvaluatorTabComponent } from './training-form/evaluator-tab/evaluator-tab.component';
import { EvaluatorModalComponent } from './training-form/evaluator-tab/evaluator-modal/evaluator-modal.component';
import { TrainingResourceGridComponent } from './training-resource-grid/training-resource-grid.component';
import { ReportsEvaluationModalComponent } from './modals/reports-evaluation-modal/reports-evaluation-modal.component';
import { HomeUnitContactComponent } from './training-form/trainee-tab/home-unit-contact/home-unit-contact.component';
import { QualsModalComponent } from './modals/quals-modal/quals-modal.component';
import { TrainingReportsComponent } from './training-reports/training-reports.component';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'incidents-outlet', component: TrainingViewComponent },
];

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    IncResSharedModule,
    SharedModule,
    AngularSplitModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    TrainingViewComponent,
    TrainingFormComponent,
    AssignmentCloseoutModalComponent,
    TraineeTabComponent,
    EvaluatorTabComponent,
    EvaluatorModalComponent,
    TrainingResourceGridComponent,
    ReportsEvaluationModalComponent,
    HomeUnitContactComponent,
    QualsModalComponent,
    TrainingReportsComponent
  ]
})
export class TrainingModule { }
