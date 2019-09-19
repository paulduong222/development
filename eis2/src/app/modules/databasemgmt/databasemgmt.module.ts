import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AngularSplitModule } from 'angular-split';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
import { DatabaseMgmtViewComponent } from './database-mgmt-view/database-mgmt-view.component';
import { DbAddEditComponent } from './db-add-edit/db-add-edit.component';
import { DbCopyComponent } from './db-copy/db-copy.component';
import { DbManualBackupComponent } from './db-manual-backup/db-manual-backup.component';
import { DbRecoverPasswordComponent } from './db-recover-password/db-recover-password.component';
import { DbRemoveComponent } from './db-remove/db-remove.component';
import { DbRestoreComponent } from './db-restore/db-restore.component';
import { SharedModule } from 'src/app/shared';

const routes: Routes = [
  { path: '', component: DatabaseMgmtViewComponent},
];

@NgModule({
  imports: [
    CommonModule,
    AngularSplitModule,
    ReactiveFormsModule,
    SharedModule,
    AgGridModule.withComponents(null),
    RouterModule.forChild(routes)
  ],
  declarations: [
    DatabaseMgmtViewComponent,
    DbAddEditComponent,
    DbCopyComponent,
    DbManualBackupComponent,
    DbRecoverPasswordComponent,
    DbRemoveComponent,
    DbRestoreComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DatabasemgmtModule { }
