import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ReportTimeViewComponent } from './report-time-view/report-time-view.component';
import { Of286InvoiceReportComponent } from '../report-time/of286-invoice-report/of286-invoice-report.component';
import { Of288InvoiceReportComponent } from '../report-time/of288-invoice-report/of288-invoice-report.component';
import { ShiftsInExcessOfStandardHoursComponent } from '../report-time/shifts-in-excess-of-standard-hours/shifts-in-excess-of-standard-hours.component';
import { PersonnelTimeReportComponent } from '../report-time/personnel-time-report/personnel-time-report.component';
import { WorkRestRatioComponent } from '../report-time/work-rest-ratio/work-rest-ratio.component';
import { SummaryOfHoursWorkedComponent } from '../report-time/summary-of-hours-worked/summary-of-hours-worked.component';
import { MissingDaysOfPostingsComponent } from '../report-time/missing-days-of-postings/missing-days-of-postings.component';
import { CrewRosterComponent } from '../report-time/crew-roster/crew-roster.component';
import { VendorResourceSummaryComponent } from '../report-time/vendor-resource-summary/vendor-resource-summary.component';
import { SharedModule } from 'src/app/shared';
import { FormsModule } from '@angular/forms';

const routes: Routes = [
  { path: '', outlet: 'incidents-outlet', component: ReportTimeViewComponent},
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    ReportTimeViewComponent,
    Of286InvoiceReportComponent,
    Of288InvoiceReportComponent,
    ShiftsInExcessOfStandardHoursComponent,
    PersonnelTimeReportComponent,
    WorkRestRatioComponent,
    SummaryOfHoursWorkedComponent,
    MissingDaysOfPostingsComponent,
    CrewRosterComponent,
    VendorResourceSummaryComponent
  ]
})
export class ReportTimeModule { }
