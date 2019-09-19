import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { AngularSplitModule } from 'angular-split';
import { AgGridModule } from 'ag-grid-angular';
import { FinExpViewComponent } from './fin-exp-view/fin-exp-view.component';
import { FinExpGridComponent } from './fin-exp-grid/fin-exp-grid.component';
import { FinExpFormComponent } from './fin-exp-form/fin-exp-form.component';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'incidents-outlet', component: FinExpViewComponent },
];

@NgModule({
  imports: [
    CommonModule,
    AngularSplitModule,
    AgGridModule.withComponents(null),
    RouterModule.forChild(routes)
  ],
  declarations: [FinExpViewComponent, FinExpGridComponent, FinExpFormComponent]
})
export class FinancialexportModule { }
