import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { IncResSharedModule } from '../../../inc-res-shared/inc-res-shared.module';
import { SharedModule } from 'src/app/shared';
import { AngularSplitModule } from 'angular-split';
import { CostAccrualsViewComponent } from './cost-accruals-view/cost-accruals-view.component';
import {CostAccrualsService} from '../../../../service/cost-accruals.service';

const routes: Routes = [
  { path: '', pathMatch: 'full',  outlet: 'incidents-outlet', component: CostAccrualsViewComponent },
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
    CostAccrualsViewComponent
  ],
  providers: [
    CostAccrualsService
  ]
})
export class CostAccrualsModule { }
