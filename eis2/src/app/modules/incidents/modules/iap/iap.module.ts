import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { IncResSharedModule } from '../../../inc-res-shared/inc-res-shared.module';
import { SharedModule } from 'src/app/shared';
import { AngularSplitModule } from 'angular-split';
import { IapViewComponent } from './iap-view/iap-view.component';
import { QuillModule } from 'ngx-quill';

import { AddAttachWindowComponent } from './modals/add-attach-window/add-attach-window.component';
import { AddEditPlanWindowComponent } from './modals/add-edit-plan-window/add-edit-plan-window.component';
import { CopyFormWindowComponent } from './modals/copy-form-window/copy-form-window.component';
import { CopyPlanWindowComponent } from './modals/copy-plan-window/copy-plan-window.component';
import { Ics202FormComponent } from './ics202-form/ics202-form.component';
import { Ics203FormComponent } from './ics203-form/ics203-form.component';
import { Ics204FormComponent } from './ics204-form/ics204-form.component';
import { Ics205FormComponent } from './ics205-form/ics205-form.component';
import { Ics206FormComponent } from './ics206-form/ics206-form.component';
import { Ics220FormComponent } from './ics220-form/ics220-form.component';
import { Ics202ObjectivesComponent } from './ics202-form/comp/ics202-objectives/ics202-objectives.component';
import { Ics202OpPerCmdEmphasisComponent } from './ics202-form/comp/ics202-op-per-cmd-emphasis/ics202-op-per-cmd-emphasis.component';
import { Ics202GenSitAwarenessComponent } from './ics202-form/comp/ics202-gen-sit-awareness/ics202-gen-sit-awareness.component';
import { Ics202SiteSafetyPlanComponent } from './ics202-form/comp/ics202-site-safety-plan/ics202-site-safety-plan.component';
import { Ics202IncActionPlanComponent } from './ics202-form/comp/ics202-inc-action-plan/ics202-inc-action-plan.component';
import { Ics202PrepApprByComponent } from './ics202-form/comp/ics202-prep-appr-by/ics202-prep-appr-by.component';
import { Ics203CommandersComponent } from './ics203-form/comp/ics203-commanders/ics203-commanders.component';
import { Ics203PlanningComponent } from './ics203-form/comp/ics203-planning/ics203-planning.component';
import { Ics203FinanceComponent } from './ics203-form/comp/ics203-finance/ics203-finance.component';
import { Ics203LogisticsComponent } from './ics203-form/comp/ics203-logistics/ics203-logistics.component';
import { Ics203OperationsComponent } from './ics203-form/comp/ics203-operations/ics203-operations.component';
import { Ics203BranchesComponent } from './ics203-form/comp/ics203-branches/ics203-branches.component';
import { Ics203AirOpBranchComponent } from './ics203-form/comp/ics203-air-op-branch/ics203-air-op-branch.component';
import { Ics203AgencyOrgRepsComponent } from './ics203-form/comp/ics203-agency-org-reps/ics203-agency-org-reps.component';
import { Ics203PreparedByComponent } from './ics203-form/comp/ics203-prepared-by/ics203-prepared-by.component';
import { Ics205BasRadChannelUseComponent } from './ics205-form/comp/ics205-bas-rad-channel-use/ics205-bas-rad-channel-use.component';
import { Ics205SpecInstrComponent } from './ics205-form/comp/ics205-spec-instr/ics205-spec-instr.component';
import { Ics205PrepByComponent } from './ics205-form/comp/ics205-prep-by/ics205-prep-by.component';
import { Ics220SunriseSunsetComponent } from './ics220-form/comp/ics220-sunrise-sunset/ics220-sunrise-sunset.component';
import { Ics220ReadyAlertComponent } from './ics220-form/comp/ics220-ready-alert/ics220-ready-alert.component';
import { Ics220PersonnelComponent } from './ics220-form/comp/ics220-personnel/ics220-personnel.component';
import { Ics220FrequenciesComponent } from './ics220-form/comp/ics220-frequencies/ics220-frequencies.component';
import { Ics220FixedWingComponent } from './ics220-form/comp/ics220-fixed-wing/ics220-fixed-wing.component';
import { Ics220HelicoptersComponent } from './ics220-form/comp/ics220-helicopters/ics220-helicopters.component';
import { Ics220PrepByComponent } from './ics220-form/comp/ics220-prep-by/ics220-prep-by.component';
import { Ics220TaskMissionComponent } from './ics220-form/comp/ics220-task-mission/ics220-task-mission.component';
import { Ics206AmbServicesComponent } from './ics206-form/comp/ics206-amb-services/ics206-amb-services.component';
import { Ics206AirAmbServicesComponent } from './ics206-form/comp/ics206-air-amb-services/ics206-air-amb-services.component';
import { Ics206HospitalsComponent } from './ics206-form/comp/ics206-hospitals/ics206-hospitals.component';
import { Ics206AreaLocCapComponent } from './ics206-form/comp/ics206-area-loc-cap/ics206-area-loc-cap.component';
import { Ics206RemoteCampLocComponent } from './ics206-form/comp/ics206-remote-camp-loc/ics206-remote-camp-loc.component';
import { Ics206PrepByRevByComponent } from './ics206-form/comp/ics206-prep-by-rev-by/ics206-prep-by-rev-by.component';
import { IapReorderWindowComponent } from './modals/iap-reorder-window/iap-reorder-window.component';
import { IapQuillComponent } from './modals/iap-quill/iap-quill.component';
import { MasterFrequencyWindowComponent } from './modals/master-frequency-window/master-frequency-window.component';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'incidents-outlet', component: IapViewComponent },
];

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    QuillModule,
    IncResSharedModule,
    SharedModule,
    AngularSplitModule,
    FormsModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    IapViewComponent,
    AddAttachWindowComponent,
    CopyPlanWindowComponent,
    CopyFormWindowComponent,
    AddEditPlanWindowComponent,
    Ics202FormComponent,
    Ics203FormComponent,
    Ics204FormComponent,
    Ics205FormComponent,
    Ics206FormComponent,
    Ics220FormComponent,
    Ics202ObjectivesComponent,
    Ics202OpPerCmdEmphasisComponent,
    Ics202GenSitAwarenessComponent,
    Ics202SiteSafetyPlanComponent,
    Ics202IncActionPlanComponent,
    Ics202PrepApprByComponent,
    Ics203CommandersComponent,
    Ics203PlanningComponent,
    Ics203FinanceComponent,
    Ics203LogisticsComponent,
    Ics203OperationsComponent,
    Ics203BranchesComponent,
    Ics203AirOpBranchComponent,
    Ics203AgencyOrgRepsComponent,
    Ics203PreparedByComponent,
    Ics205BasRadChannelUseComponent,
    Ics205SpecInstrComponent,
    Ics205PrepByComponent,
    Ics220SunriseSunsetComponent,
    Ics220ReadyAlertComponent,
    Ics220PersonnelComponent,
    Ics220FrequenciesComponent,
    Ics220FixedWingComponent,
    Ics220HelicoptersComponent,
    Ics220PrepByComponent,
    Ics220TaskMissionComponent,
    Ics206AmbServicesComponent,
    Ics206AirAmbServicesComponent,
    Ics206HospitalsComponent,
    Ics206AreaLocCapComponent,
    Ics206RemoteCampLocComponent,
    Ics206PrepByRevByComponent,
    IapReorderWindowComponent,
    IapQuillComponent,
    MasterFrequencyWindowComponent,

  ]
})
export class IapModule { }
