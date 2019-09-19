import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { DataTransferViewComponent } from './data-transfer-view/data-transfer-view.component';
import { SharedModule } from 'src/app/shared';
import { AgGridModule } from 'ag-grid-angular';
import { ReactiveFormsModule } from '@angular/forms';
import { AngularSplitModule } from 'angular-split';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'incidents-outlet', component: DataTransferViewComponent },
];

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(routes),
    AgGridModule.withComponents(null),
    ReactiveFormsModule,
    AngularSplitModule
  ],
  declarations: [DataTransferViewComponent]
})
export class DatatransferModule { }
