import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { IncResSharedModule } from '../../../inc-res-shared/inc-res-shared.module';
import { SharedModule } from 'src/app/shared';
import { AngularSplitModule } from 'angular-split';
import { AdminOffice2ViewComponent } from './admin-office2-view/admin-office2-view.component';
import { AdminOffice2FormComponent } from './admin-office2-form/admin-office2-form.component';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'incidents-outlet', component: AdminOffice2ViewComponent },
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
    AdminOffice2ViewComponent,
    AdminOffice2FormComponent,
  ]
})
export class AdminOffice2Module { }
