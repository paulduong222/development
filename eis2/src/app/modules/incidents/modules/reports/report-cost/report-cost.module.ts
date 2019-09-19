import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ReportCostViewComponent } from './report-cost-view/report-cost-view.component';
import { GroupCategorySummaryReportComponent } from './group-category-summary-report/group-category-summary-report.component';
import { SummaryByResourceReportComponent } from './summary-by-resource-report/summary-by-resource-report.component';
import { SummaryForCurrentDayReportComponent } from './summary-for-current-day-report/summary-for-current-day-report.component';
import { DetailByResourceReportComponent } from './detail-by-resource-report/detail-by-resource-report.component';
import { GroupCategoryTotalReportComponent } from './group-category-total-report/group-category-total-report.component';
import { AircraftDetailReportComponent } from './aircraft-detail-report/aircraft-detail-report.component';
import { AnalysisReportComponent } from './analysis-report/analysis-report.component';
import { CostShareReportComponent } from './cost-share-report/cost-share-report.component';
import { SharedModule } from 'src/app/shared';
import { FormsModule } from '@angular/forms';

const routes: Routes = [
  { path: '', outlet: 'incidents-outlet', component: ReportCostViewComponent},
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    ReportCostViewComponent, 
    GroupCategorySummaryReportComponent, 
    SummaryByResourceReportComponent, 
    SummaryForCurrentDayReportComponent, 
    DetailByResourceReportComponent, 
    GroupCategoryTotalReportComponent, 
    AircraftDetailReportComponent, 
    AnalysisReportComponent, 
    CostShareReportComponent
  ]
})
export class ReportCostModule { }
