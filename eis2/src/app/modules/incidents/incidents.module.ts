import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { IncResSharedModule } from '../inc-res-shared/inc-res-shared.module';
import { AdminOfficeModule } from './modules/admin-office/admin-office.module';
import { AdminOffice2Module } from './modules/admin-office2/admin-office2.module';
import { RossimportModule } from './modules/rossimport/rossimport.module';
import { ResourcesModule } from './modules/resources/resources.module';
import { ReportPlansModule } from './modules/reports/report-plans/report-plans.module';
import { ReportTimeModule } from './modules/reports/report-time/report-time.module';
import { ReportCostModule } from './modules/reports/report-cost/report-cost.module';
import { ReportTnspModule } from './modules/reports/report-tnsp/report-tnsp.module';
import { ReportCustomModule } from './modules/reports/report-custom/report-custom.module';
import { IncidentsViewComponent } from './incidents-view/incidents-view.component';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentGroupService } from 'src/app/service/incident-group.service';
import { IncidentService } from 'src/app/service/incident.service';
import { ContractorsModule } from './modules/contractors/contractors.module';
import { IapModule } from './modules/iap/iap.module';
import { CostGroupsModule } from './modules/cost-groups/cost-groups.module';
import { CostAccrualsModule } from './modules/cost-accruals/cost-accruals.module';
import { TrainingModule } from './modules/training/training.module';

const routes: Routes = [
  { path: '', component: IncidentsViewComponent, children: [
    { path: '', redirectTo: 'inc', pathMatch: 'full'},
    { path: 'inc', loadChildren: './modules/inc/inc.module#IncModule'},
  ]},
  { path: 'costgroups', component: IncidentsViewComponent, children: [
      {path: '', loadChildren: './modules/cost-groups/cost-groups.module#CostGroupsModule'},
  ]},
  { path: 'costaccruals', component: IncidentsViewComponent, children: [
      {path: '', loadChildren: './modules/cost-accruals/cost-accruals.module#CostAccrualsModule'},
  ]},
  { path: 'training', component: IncidentsViewComponent, children: [
      {path: '', loadChildren: './modules/training/training.module#TrainingModule'},
  ]},
  { path: 'resources', component: IncidentsViewComponent, children: [
      {path: '', loadChildren: './modules/resources/resources.module#ResourcesModule'},
  ]},
  { path: 'reports/plans', component: IncidentsViewComponent, children: [
      {path: '', loadChildren: './modules/reports/report-plans/report-plans.module#ReportPlansModule'}
  ]},
  { path: 'reports/time', component: IncidentsViewComponent, children: [
      {path: '', loadChildren: './modules/reports/report-time/report-time.module#ReportTimeModule'}
  ]},
  { path: 'reports/cost', component: IncidentsViewComponent, children: [
      {path: '', loadChildren: './modules/reports/report-cost/report-cost.module#ReportCostModule'}
  ]},
  { path: 'reports/training', component: IncidentsViewComponent, children: [
      {path: '', loadChildren: './modules/reports/report-tnsp/report-tnsp.module#ReportTnspModule'}
  ]},
  { path: 'reports/custom', component: IncidentsViewComponent, children: [
      {path: '', loadChildren: './modules/reports/report-custom/report-custom.module#ReportCustomModule'}
  ]},
  { path: 'rossimport', component: IncidentsViewComponent, children: [
    { path: '', loadChildren: './modules/rossimport/rossimport.module#RossimportModule'},
  ]},
  { path: 'finexp', component: IncidentsViewComponent, children: [
    { path: '', loadChildren: './modules/financialexport/financialexport.module#FinancialexportModule'},
  ]},
  { path: 'datatransfer', component: IncidentsViewComponent, children: [
    { path: '', loadChildren: './modules/datatransfer/datatransfer.module#DatatransferModule'},
  ]},
  { path: 'adminoffice', component: IncidentsViewComponent, children: [
    { path: '', loadChildren: './modules/admin-office2/admin-office2.module#AdminOffice2Module'},
  ]},
//  { path: 'adminoffice2', component: IncidentsViewComponent, children: [
 //   { path: '', loadChildren: './modules/admin-office2/admin-office2.module#AdminOffice2Module'},
//  ]},
  { path: 'contractors', component: IncidentsViewComponent, children: [
    { path: '', loadChildren: './modules/contractors/contractors.module#ContractorsModule'},
  ]},
  { path: 'iap', component: IncidentsViewComponent, children: [
    { path: '', loadChildren: './modules/iap/iap.module#IapModule'},
  ]},
];

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    IncResSharedModule,
    RossimportModule,
    AdminOfficeModule,
    AdminOffice2Module,
    ContractorsModule,
    ReportTnspModule,
    ReportCustomModule,
    IapModule,
    CostGroupsModule,
    CostAccrualsModule,
    TrainingModule,
    RouterModule.forChild(routes)
  ],
  exports: [
  ],
  declarations: [
    IncidentsViewComponent,
  ],
  entryComponents: [],
  providers: [
    IncidentGroupService,
    IncidentService,
  ],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class IncidentsModule { }
