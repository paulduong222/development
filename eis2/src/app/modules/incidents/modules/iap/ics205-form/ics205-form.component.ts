import { Component, OnInit, AfterViewInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { IapForm205Vo } from 'src/app/data/iap-form205-vo';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { NotificationService } from 'src/app/service/notification-service';
import { Ics205BasRadChannelUseComponent } from './comp/ics205-bas-rad-channel-use/ics205-bas-rad-channel-use.component';
import { Ics205SpecInstrComponent } from './comp/ics205-spec-instr/ics205-spec-instr.component';
import { Ics205PrepByComponent } from './comp/ics205-prep-by/ics205-prep-by.component';
import { IapReorderWindowComponent } from '../modals/iap-reorder-window/iap-reorder-window.component';

@Component({
  selector: 'app-ics205-form',
  templateUrl: './ics205-form.component.html',
  styleUrls: ['./ics205-form.component.css']
})
export class Ics205FormComponent implements OnInit, AfterViewInit {
  @ViewChild('ics205BasRadChannelUse') ics205BasRadChannelUse: Ics205BasRadChannelUseComponent;
  @ViewChild('ics205SpecInstr') ics205SpecInstr: Ics205SpecInstrComponent;
  @ViewChild('ics205PrepBy') ics205PrepBy: Ics205PrepByComponent;
  @ViewChild('reorderWindow') reorderWindow: IapReorderWindowComponent;

  tabName = 'block4';
  tabNames = ['block4', 'block5', 'block6'];

  iapPlanId = 0;

  iapForm205Vo = <IapForm205Vo>{id: 0};
  isFormLocked = false;
  saveNext = false;

  incidentId = 0;
  incidentGroupId = 0;

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
      case 'block4':
        this.ics205BasRadChannelUse.reloadPage(this.iapForm205Vo);
        break;
      case 'block5':
        setTimeout(() => {
          this.ics205SpecInstr.reloadPage(this.iapForm205Vo);
        });
        break;
      case 'block6':
        this.ics205PrepBy.reloadPage(this.iapForm205Vo);
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
    if ( name === 'block4') {
      return ( this.tabName === name ? 'w3-left dv-block4' : 'hidden');
    } else {
      return ( this.tabName === name ? 'w3-left dv-block' : 'hidden');
    }
  }

  loadForm(formId, formType) {
    this.iapPlanService.getIapForm(formId, formType)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_IAP_FORM_205' ) {
        this.iapForm205Vo = data['resultObject'] as IapForm205Vo;
        this.isFormLocked = this.iapForm205Vo.isFormLocked;
        this.openTab('block4');
      }
    });
  }

  save(saveNext) {
    let iapForm205VoToSave = null;
    this.saveNext = saveNext;

    switch (this.tabName) {
      case 'block4':
          // Block 4 has its own save routine for the frequencies
          break;
      case 'block5':
          iapForm205VoToSave = this.ics205SpecInstr.getIapForm205Vo();
          this.proceedSave(iapForm205VoToSave);
          break;
      case 'block6':
          iapForm205VoToSave = this.ics205PrepBy.getIapForm205Vo();
          this.proceedSave(iapForm205VoToSave);
          break;
    }
  }

  cancel() {
    // reload tab
    this.openTab(this.tabName);
  }

  proceedSave(iapForm205VoToSave) {
    this.iapPlanService.saveIapForm205(iapForm205VoToSave)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_IAP_FORM_205' ) {
        this.iapForm205Vo = data['resultObject'] as IapForm205Vo;

        if ( this.saveNext === false) {
          // reload tab
          this.openTab(this.tabName);
        } else {
          this.openNextTab();
        }
      }
    });
  }

  frequenciesUpdateEvent() {
    this.iapForm205Vo.iapFrequencieVos = this.ics205BasRadChannelUse.frequencyList;
  }

  ics205ReorderOpenEvent(name) {
    switch (name) {
      case 'Radios':
        this.reorderWindow.showModal = true;
        this.reorderWindow.listName = 'Radios';
        this.reorderWindow.gridColumnDefs = this.ics205BasRadChannelUse.gridColumnDefs;
        this.reorderWindow.windowLabel = 'Re-Order Basic Radio Channels';
        this.reorderWindow.openModal();
        setTimeout(() => {
          this.reorderWindow.list = this.iapForm205Vo.iapFrequencieVos;
          this.reorderWindow.loadWindow();
        });
        break;
    }
  }

  saveOrderEvent(name) {
    this.reorderWindow.closeModal();
    switch (name) {
      case 'Radios':
        this.ics205BasRadChannelUse.saveNewOrder(this.reorderWindow.list);
        break;
   }
  }

}
