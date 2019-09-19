import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { MessageBoardViewComponent } from './message-board-view/message-board-view.component';
import { MessageboardNavBarComponent } from './components/messageboard-nav-bar/messageboard-nav-bar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { QuillModule } from 'ngx-quill';

const routes: Routes = [
  { path: '', component: MessageBoardViewComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    QuillModule,
    ReactiveFormsModule,
  ],
  declarations: [
    MessageBoardViewComponent,
    MessageboardNavBarComponent
  ]
})
export class MessageboardModule { }
