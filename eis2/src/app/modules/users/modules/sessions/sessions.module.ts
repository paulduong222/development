import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { AgGridModule } from 'ag-grid-angular';
import { SessionsComponent } from './sessions/sessions.component';
import { SharedModule } from 'src/app/shared';
import { UserSessionMgmtService } from 'src/app/service/user-session-mgmt.service';
const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'users-outlet', component: SessionsComponent },
];

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    AgGridModule.withComponents(null),
    RouterModule.forChild(routes)
  ],
  declarations: [
    SessionsComponent,
  ],
  providers: [
    UserSessionMgmtService
  ]
})
export class SessionsModule { }
