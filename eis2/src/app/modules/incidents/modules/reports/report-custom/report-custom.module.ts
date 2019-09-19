import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ReportCustomViewComponent } from './report-custom-view/report-custom-view.component';

const routes: Routes = [
  { path: '', outlet: 'incidents-outlet', component: ReportCustomViewComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  declarations: [ReportCustomViewComponent]
})
export class ReportCustomModule { }
