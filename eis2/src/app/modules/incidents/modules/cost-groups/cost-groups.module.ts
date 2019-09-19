import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { IncResSharedModule } from '../../../inc-res-shared/inc-res-shared.module';
import { SharedModule } from 'src/app/shared';
import { AngularSplitModule } from 'angular-split';
import { CostGroupsViewComponent } from './cost-groups-view/cost-groups-view.component';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'incidents-outlet', component: CostGroupsViewComponent },
];

@NgModule({
  imports: [
    CommonModule,
    IncResSharedModule,
    SharedModule,
    AngularSplitModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    CostGroupsViewComponent,
  ]
})
export class CostGroupsModule { }
