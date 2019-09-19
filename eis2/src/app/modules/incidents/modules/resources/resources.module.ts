import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
import { SharedModule } from 'src/app/shared';
import { AngularSplitModule } from 'angular-split';
import { Routes, RouterModule } from '@angular/router';
import { ResourcesViewComponent } from './resources-view/resources-view.component';
import { ResourcesFormComponent } from './resources-form/resources-form.component';
import { ResourcesTimeComponent } from './resources-time/resources-time.component';
import { ResourceGridComponent } from './resource-grid/resource-grid.component';
import { ResourcesTimeadjComponent } from './resources-timeadj/resources-timeadj.component';
import { ResourcesCostComponent } from './resources-cost/resources-cost.component';
import { ResourcesCostotherComponent } from './resources-costother/resources-costother.component';
import { IncidentResourceService } from 'src/app/service/incident-resource.service';
import { CostDataComponent } from './resources-form/comp/cost-data/cost-data.component';
import { TimeOverheadComponent } from './resources-time/comp/time-overhead/time-overhead.component';
import { TimeCrewComponent } from './resources-time/comp/time-crew/time-crew.component';
import { TimeContractorComponent } from './resources-time/comp/time-contractor/time-contractor.component';
import { TimeNoneComponent } from './resources-time/comp/time-none/time-none.component';
import { QualWindowComponent } from './modals/qual-window/qual-window.component';
import { RestOvernightWindowComponent } from './modals/rest-overnight-window/rest-overnight-window.component';
import { QuickStatsWindowComponent } from './modals/quick-stats-window/quick-stats-window.component';
import { ContractorWindowComponent } from './modals/contractor-window/contractor-window.component';
import { AgreementWindowComponent } from './modals/agreement-window/agreement-window.component';
import { AdminOfficeWindowComponent } from './modals/admin-office-window/admin-office-window.component';
import { ContractorRateWindowComponent } from './modals/contractor-rate-window/contractor-rate-window.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', outlet: 'incidents-outlet', component: ResourcesViewComponent, children: [
    { path: '', outlet: 'resources-outlet', component: ResourcesFormComponent},
  ]},
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
    ResourceGridComponent,
    ResourcesViewComponent,
    ResourcesFormComponent,
    ResourcesTimeComponent,
    ResourcesTimeadjComponent,
    ResourcesCostComponent,
    ResourcesCostotherComponent,
    CostDataComponent,
    TimeOverheadComponent,
    TimeCrewComponent,
    TimeContractorComponent,
    TimeNoneComponent,
    QualWindowComponent,
    RestOvernightWindowComponent,
    QuickStatsWindowComponent,
    ContractorWindowComponent,
    AgreementWindowComponent,
    AdminOfficeWindowComponent,
    ContractorRateWindowComponent,
  ],
  providers: [
    IncidentResourceService,
  ]
})
export class ResourcesModule { }
