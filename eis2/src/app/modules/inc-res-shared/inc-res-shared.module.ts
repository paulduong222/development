import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/shared';
import { AgGridModule } from 'ag-grid-angular';
import { CheckInSettingsModalComponent } from '../incidents/modals/check-in-settings-modal/check-in-settings-modal.component';
import { DemobSettingsModalComponent } from '../incidents/modals/demob-settings-modal/demob-settings-modal.component';
import { IapSettingsModalComponent } from '../incidents/modals/iap-settings-modal/iap-settings-modal.component';
import { TrainingSettingsModalComponent } from '../incidents/modals/training-settings-modal/training-settings-modal.component';
import { CostSettingsModalComponent } from '../incidents/modals/cost-settings-modal/cost-settings-modal.component';
import { IncidentsNavBarComponent } from '../incidents/incidents-nav-bar/incidents-nav-bar.component';
import { ResourceGridComponent } from './resource-grid/resource-grid.component';


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SharedModule,
    AgGridModule.withComponents(null),
  ],
  declarations: [
    CheckInSettingsModalComponent,
    DemobSettingsModalComponent,
    IapSettingsModalComponent,
    CostSettingsModalComponent,
    TrainingSettingsModalComponent,
    IncidentsNavBarComponent,
    ResourceGridComponent,
  ],
  exports: [
    CheckInSettingsModalComponent,
    DemobSettingsModalComponent,
    IapSettingsModalComponent,
    CostSettingsModalComponent,
    TrainingSettingsModalComponent,
    IncidentsNavBarComponent,
    ResourceGridComponent,
  ]
})
export class IncResSharedModule { }
