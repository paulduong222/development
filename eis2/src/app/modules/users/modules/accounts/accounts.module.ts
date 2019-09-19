import { NgModule, Pipe, PipeTransform, Directive } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AngularSplitModule } from 'angular-split';
import { Routes, RouterModule } from '@angular/router';
import { UserAccountsComponent } from './user-accounts/user-accounts.component';
import { UserAccountFormComponent } from './user-accounts/user-account-form/user-account-form.component';
import { AgGridModule } from 'ag-grid-angular';
import { SharedModule } from 'src/app/shared';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'users-outlet', component: UserAccountsComponent },
];

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    AngularSplitModule,
    AgGridModule.withComponents(null),
    RouterModule.forChild(routes)
  ],
  declarations: [
    UserAccountsComponent,
    UserAccountFormComponent,
  ]
})
export class AccountsModule { }
