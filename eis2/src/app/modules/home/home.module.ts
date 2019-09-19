import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { HomeViewComponent } from './components/home-view/home-view.component';
import { MessageBoardViewComponent } from './components/message-board-view/message-board-view.component';
import { QuillModule } from 'ngx-quill';
import { FormsModule } from '@angular/forms';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { SharedModule } from 'src/app/shared';
import { ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../service/user.service';

const routes: Routes = [
  { path: '', component: HomeViewComponent },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    FormsModule,
    QuillModule,
    SharedModule,
  ],
  declarations: [
    HomeViewComponent,
    MessageBoardViewComponent,
    ChangePasswordComponent],
  providers: [
    UserService
  ]
})
export class HomeModule { }
