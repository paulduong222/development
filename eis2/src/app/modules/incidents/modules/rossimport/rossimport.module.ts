import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgGridModule } from 'ag-grid-angular';
import { Routes, RouterModule } from '@angular/router';
import { RossImportViewComponent } from './ross-import-view/ross-import-view.component';
import { RossImportFileComponent } from './ross-import-file/ross-import-file.component';
import { RossImportStartComponent } from './ross-import-file/ross-import-start/ross-import-start.component';
import { RossImportExcludedComponent } from './ross-import-excluded/ross-import-excluded.component';
import { RossImportGridComponent } from './ross-import-file/ross-import-grid/ross-import-grid.component';
import { RossImportMatchresComponent } from './ross-import-file/ross-import-matchres/ross-import-matchres.component';
import { RossImportExlresComponent } from './ross-import-file/ross-import-exlres/ross-import-exlres.component';
import { RossImportRevcompleteComponent } from './ross-import-file/ross-import-revcomplete/ross-import-revcomplete.component';
import { MatchResourceGrid1Component } from './ross-import-file/match-resource-grid1/match-resource-grid1.component';
import { MatchResourceGrid2Component } from './ross-import-file/match-resource-grid2/match-resource-grid2.component';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'incidents-outlet', component: RossImportViewComponent },
];

@NgModule({
  imports: [
    CommonModule,
    AgGridModule.withComponents(null),
    RouterModule.forChild(routes)
  ],
  declarations: [
    RossImportViewComponent,
    RossImportFileComponent,
    RossImportExcludedComponent,
    RossImportGridComponent,
    MatchResourceGrid1Component,
    MatchResourceGrid2Component,
    RossImportStartComponent,
    RossImportMatchresComponent,
    RossImportExlresComponent,
    RossImportRevcompleteComponent,
  ]
})
export class RossimportModule { }
