import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ReportPlansViewComponent } from './report-plans-view/report-plans-view.component';
import { AllResourcesReportComponent } from './all-resources-report/all-resources-report.component';
import { IcsReportComponent } from './ics-report/ics-report.component';
import { QualReportComponent } from './qual-report/qual-report.component';
import { StTfReportComponent } from './st-tf-report/st-tf-report.component';
import { CheckOutFormReportComponent } from './check-out-form-report/check-out-form-report.component';
import { DemobPlanningReportComponent } from './demob-planning-report/demob-planning-report.component';
import { TentativePosterReportComponent } from './tentative-poster-report/tentative-poster-report.component';
import { AvailForReleaseReportComponent } from './avail-for-release-report/avail-for-release-report.component';
import { AirTravelReqReportComponent } from './air-travel-req-report/air-travel-req-report.component';
import { LastWorkDayReportComponent } from './last-work-day-report/last-work-day-report.component';
import { ActualDemobReportComponent } from './actual-demob-report/actual-demob-report.component';
import { GroundSupportReportComponent } from './ground-support-report/ground-support-report.component';
import { GlidePathReportComponent } from './glide-path-report/glide-path-report.component';
import { SharedModule } from 'src/app/shared';
import { FormsModule } from '@angular/forms';

const routes: Routes = [
  { path: '', outlet: 'incidents-outlet', component: ReportPlansViewComponent},
  /*
  { path: '', outlet: 'incidents-outlet', component: ReportsViewComponent, children: [
    { path: '', outlet: 'reports-outlet', component: ReportPlansViewComponent},
   ]},
   */
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    ReportPlansViewComponent,
    AllResourcesReportComponent,
    IcsReportComponent,
    QualReportComponent,
    StTfReportComponent,
    CheckOutFormReportComponent,
    DemobPlanningReportComponent,
    TentativePosterReportComponent,
    AvailForReleaseReportComponent,
    AirTravelReqReportComponent,
    LastWorkDayReportComponent,
    ActualDemobReportComponent,
    GroundSupportReportComponent,
    GlidePathReportComponent,
  ]
})
export class ReportPlansModule { }
