import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { AgGridModule } from 'ag-grid-angular';
import { ImportexportNavBarComponent } from '../../components/importexport-nav-bar/importexport-nav-bar.component';
import { ImportexportComponent } from './importexport/importexport.component';
import { ExportUserComponent } from './export-user/export-user.component';
import { ImportUserComponent } from './import-user/import-user.component';
import { ImportGridComponent } from './import-user/import-grid/import-grid.component';
import { SharedModule } from '../../../../shared';
const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'users-outlet', component: ImportexportComponent,
 },
];

@NgModule({
  imports: [
    CommonModule,
    AgGridModule.withComponents(null),
    RouterModule.forChild(routes),
    SharedModule,
    ReactiveFormsModule
  ],
  declarations: [
    ImportexportComponent,
    ExportUserComponent,
    ImportUserComponent,
    ImportexportNavBarComponent,
    ImportGridComponent,
]
})
export class ImportexportModule { }
