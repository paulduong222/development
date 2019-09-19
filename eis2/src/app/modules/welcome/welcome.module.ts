import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { DisclaimerComponent } from './components/disclaimer/disclaimer.component';
import { LoginComponent } from './components/login/login.component';
import { RecoverAccountComponent } from './components/recover-account/recover-account.component';
import { InitialLoginComponent } from './components/initial-login/initial-login.component';
import { SystemService } from '../../service/system.service';
import { AuthService } from '../../service/auth.service';
import { NotificationService } from '../../service/notification-service';
import { ReactiveFormsModule } from '@angular/forms';
import { IncidentsNavBarComponent } from '../../components/incidents-nav-bar/incidents-nav-bar.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { RobComponent } from './components/rob/rob.component';
import { RobAdminComponent } from './components/rob/rob-admin/rob-admin.component';
import { RobFsComponent } from './components/rob/rob-fs/rob-fs.component';
import { RobNonFsComponent } from './components/rob/rob-non-fs/rob-non-fs.component';

import { SharedModule } from '../../shared';
import { UserService } from '../../service/user.service';

const routes: Routes = [
  { path: '', component: DisclaimerComponent },
  { path: 'login', component: LoginComponent },
  { path: 'recover-account', component: RecoverAccountComponent },
  { path: 'initial-login', component: InitialLoginComponent },
  { path: 'change-password', component: ChangePasswordComponent },
  { path: 'rob', component: RobComponent },
];

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SharedModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    DisclaimerComponent,
    LoginComponent,
    RecoverAccountComponent,
    InitialLoginComponent,
    ChangePasswordComponent,
    RobComponent,
    RobAdminComponent,
    RobFsComponent,
    RobNonFsComponent,
    IncidentsNavBarComponent
  ],
  providers: [
    SystemService
    , UserService
    , AuthService
    , NotificationService
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WelcomeModule { }
