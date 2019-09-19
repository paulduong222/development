import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { IncResSharedModule } from '../../../inc-res-shared/inc-res-shared.module';
import { SharedModule } from 'src/app/shared';
import { AngularSplitModule } from 'angular-split';
import { AdminOfficeViewComponent } from './admin-office-view/admin-office-view.component';
import { AdminOfficeFormComponent } from './admin-office-form/admin-office-form.component';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'incidents-outlet', component: AdminOfficeViewComponent },
];

@NgModule({
  imports: [
    CommonModule,
    IncResSharedModule,
    SharedModule,
    AngularSplitModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    AdminOfficeViewComponent,
    AdminOfficeFormComponent,
  ]
})
export class AdminOfficeModule { }
