import { Component, OnInit, AfterViewInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { IapForm202Vo } from 'src/app/data/iap-form202-vo';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { NotificationService } from 'src/app/service/notification-service';
import { Ics202ObjectivesComponent } from './comp/ics202-objectives/ics202-objectives.component';
import { Ics202SiteSafetyPlanComponent } from './comp/ics202-site-safety-plan/ics202-site-safety-plan.component';
import { Ics202IncActionPlanComponent } from './comp/ics202-inc-action-plan/ics202-inc-action-plan.component';
import { Ics202PrepApprByComponent } from './comp/ics202-prep-appr-by/ics202-prep-appr-by.component';
import { Ics202OpPerCmdEmphasisComponent } from './comp/ics202-op-per-cmd-emphasis/ics202-op-per-cmd-emphasis.component';
import { Ics202GenSitAwarenessComponent } from './comp/ics202-gen-sit-awareness/ics202-gen-sit-awareness.component';

@Component({
  selector: 'app-ics202-form',
  templateUrl: './ics202-form.component.html',
  styleUrls: ['./ics202-form.component.css']
})
export class Ics202FormComponent implements OnInit, AfterViewInit {
  @ViewChild('ics202Objectives') ics202Objectives: Ics202ObjectivesComponent;
  @ViewChild('ics202SiteSafetyPlan') ics202SiteSafetyPlan: Ics202SiteSafetyPlanComponent;
  @ViewChild('ics202IncActionPlan') ics202IncActionPlan: Ics202IncActionPlanComponent;
  @ViewChild('ics202OpPerCmdEmphasis') ics202OpPerCmdEmphasis: Ics202OpPerCmdEmphasisComponent;
  @ViewChild('ics202GenSitAwareness') ics202GenSitAwareness: Ics202GenSitAwarenessComponent;
  @ViewChild('ics202PrepApprBy') ics202PrepApprBy: Ics202PrepApprByComponent;

  tabName = 'block3';
  tabNames = ['block3', 'block4', 'block4gsa', 'block5', 'block6', 'block78'];

  iapPlanId = 0;

  iapForm202Vo = <IapForm202Vo>{id: 0};
  isFormLocked = false;
  saveNext = false;

  constructor(private iapPlanService: IapPlanService
                , private notifyService: NotificationService) { }

  ngOnInit() {
  }

  ngAfterViewInit() {

  }

  getBtnClass(name) {
    return ( this.tabName === name ? 'btn-selected w3-small' : 'w3-small');
  }

  openTab(name) {
    this.tabName = name;
    switch (name) {
      case 'block3':
        this.ics202Objectives.reloadPage(this.iapForm202Vo);
        break;
      case 'block4':
        this.ics202OpPerCmdEmphasis.reloadPage(this.iapForm202Vo);
        break;
      case 'block4gsa':
        this.ics202GenSitAwareness.reloadPage(this.iapForm202Vo);
        break;
      case 'block5':
        this.ics202SiteSafetyPlan.reloadPage(this.iapForm202Vo);
        break;
      case 'block6':
        this.ics202IncActionPlan.reloadPage(this.iapForm202Vo);
        break;
      case 'block78':
        this.ics202PrepApprBy.reloadPage(this.iapForm202Vo);
        break;
    }
  }

  openNextTab() {
    const idx = this.tabNames.indexOf(this.tabName);
    if ( idx < (this.tabNames.length - 1))  {
      this.openTab(this.tabNames[idx + 1]);
    } else {
      this.openTab(this.tabNames[0]);
    }
  }

  getDivBlockClass(name) {
    return ( this.tabName === name ? 'dv-block w3-left' : 'hidden');
  }

  loadForm(formId, formType) {
    this.iapPlanService.getIapForm(formId, formType)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_IAP_FORM_202' ) {
        this.iapForm202Vo = data['resultObject'] as IapForm202Vo;
        this.isFormLocked = this.iapForm202Vo.isFormLocked;
        this.openTab('block3');
      }
    });
  }

  save(saveNext) {
    let iapForm202VoToSave = null;
    this.saveNext = saveNext;

    switch (this.tabName) {
      case 'block3':
          iapForm202VoToSave = this.ics202Objectives.getIapForm202Vo();
          this.proceedSave(iapForm202VoToSave);
          break;
      case 'block4':
          iapForm202VoToSave = this.ics202OpPerCmdEmphasis.getIapForm202Vo();
          this.proceedSave(iapForm202VoToSave);
          break;
      case 'block4gsa':
          iapForm202VoToSave = this.ics202GenSitAwareness.getIapForm202Vo();
          this.proceedSave(iapForm202VoToSave);
          break;
      case 'block5':
          iapForm202VoToSave = this.ics202SiteSafetyPlan.getIapForm202Vo();
          this.proceedSave(iapForm202VoToSave);
          break;
      case 'block6':
          iapForm202VoToSave = this.ics202IncActionPlan.getIapForm202Vo();
          this.proceedSave(iapForm202VoToSave);
          break;
      case 'block78':
          iapForm202VoToSave = this.ics202PrepApprBy.getIapForm202Vo();
          this.proceedSave(iapForm202VoToSave);
          break;
    }
  }

  cancel() {
    // reload tab
    this.openTab(this.tabName);
  }

  proceedSave(iapForm202VoToSave) {
    this.iapPlanService.saveIapForm202(iapForm202VoToSave)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_IAP_FORM_202' ) {
        this.iapForm202Vo = data['resultObject'] as IapForm202Vo;

        if ( this.saveNext === false) {
          // reload tab
          this.openTab(this.tabName);
        } else {
          this.openNextTab();
        }
      }
    });
  }

}
