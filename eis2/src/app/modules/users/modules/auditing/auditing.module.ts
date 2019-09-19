import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { AgGridModule } from 'ag-grid-angular';
import { SharedModule } from 'src/app/shared';
import { ReactiveFormsModule } from '@angular/forms';
import { AuditingComponent } from './auditing/auditing.component';
import { AuditingGridComponent } from './auditing-grid/auditing-grid.component';
import { AuditingFilterComponent } from './auditing-filter/auditing-filter.component';
import { AuditingNavBarComponent } from '../../components/auditing-nav-bar/auditing-nav-bar.component';
import { LoginHistoryComponent } from './login-history/login-history.component';
import { BackupHistoryComponent } from './backup-history/backup-history.component';
import { RobHistoryComponent } from './rob-history/rob-history.component';
import { PasswordResetHistoryComponent } from './password-reset-history/password-reset-history.component';
import { UserAccountsHistoryComponent } from './user-accounts-history/user-accounts-history.component';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'users-outlet', component: AuditingComponent },
];

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    AgGridModule.withComponents(null),
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
  AuditingComponent,
  AuditingNavBarComponent,
  AuditingGridComponent,
  AuditingFilterComponent,
  LoginHistoryComponent,
  BackupHistoryComponent,
  RobHistoryComponent,
  PasswordResetHistoryComponent,
  UserAccountsHistoryComponent,
  ],
})
export class AuditingModule { }
