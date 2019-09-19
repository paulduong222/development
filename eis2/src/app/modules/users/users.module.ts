import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { AngularSplitModule } from 'angular-split';
import { UsersComponent } from './components/users/users.component';
import { UsersNavBarComponent } from './components/users-nav-bar/users-nav-bar.component';
import { UserService } from '../../service/user.service';
import { DataAuditTrackingService } from './service/data-audit-tracking.service';
import { SharedModule } from '../../shared';
const routes: Routes = [
  { path: '', component: UsersComponent, children: [
    { path: '', loadChildren: './modules/accounts/accounts.module#AccountsModule'},
  ]},
  { path: 'auditing', pathMatch: 'full', component: UsersComponent, children: [
    { path: '', loadChildren: './modules/auditing/auditing.module#AuditingModule'},
  ]},
  { path: 'sessions', pathMatch: 'full', component: UsersComponent, children: [
    { path: '', loadChildren: './modules/sessions/sessions.module#SessionsModule'},
  ]},
  { path: 'importexport', pathMatch: 'full', component: UsersComponent, children: [
    { path: '', loadChildren: './modules/importexport/importexport.module#ImportexportModule'},
  ]},
];

@NgModule({
  imports: [
    CommonModule,
    AngularSplitModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    SharedModule
  ],
  declarations: [
    UsersComponent,
    UsersNavBarComponent,
  ],
  providers: [
    UserService,
    DataAuditTrackingService
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UsersModule { }
