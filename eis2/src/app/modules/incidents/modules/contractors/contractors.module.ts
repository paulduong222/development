import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { IncResSharedModule } from '../../../inc-res-shared/inc-res-shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/shared';
import { AngularSplitModule } from 'angular-split';
import { ContractorsViewComponent } from './contractors-view/contractors-view.component';
import { ContractorsFormComponent } from './contractors-form/contractors-form.component';
import {  ContractorsAgreementFormComponent } from './contractors-agreement-form/contractors-agreement-form.component';
import { AgreementReasonWindowComponent } from './modals/agreement-reason-window/agreement-reason-window.component';
import { AdminOfficeWindowComponent } from './modals/admin-office-window/admin-office-window.component';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'incidents-outlet', component: ContractorsViewComponent },
];

@NgModule({
  imports: [
    CommonModule,
    IncResSharedModule,
    ReactiveFormsModule,
    SharedModule,
    AngularSplitModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    ContractorsViewComponent,
    ContractorsFormComponent,
    ContractorsAgreementFormComponent,
    AgreementReasonWindowComponent,
    AdminOfficeWindowComponent,
  ]
})
export class ContractorsModule { }
