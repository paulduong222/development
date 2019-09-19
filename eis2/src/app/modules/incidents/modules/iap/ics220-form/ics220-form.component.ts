import { Component, OnInit, AfterViewInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { IapForm220Vo } from 'src/app/data/iap-form220-vo';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { NotificationService } from 'src/app/service/notification-service';
import { Ics220SunriseSunsetComponent } from './comp/ics220-sunrise-sunset/ics220-sunrise-sunset.component';
import { Ics220FixedWingComponent } from './comp/ics220-fixed-wing/ics220-fixed-wing.component';
import { Ics220FrequenciesComponent } from './comp/ics220-frequencies/ics220-frequencies.component';
import { Ics220HelicoptersComponent } from './comp/ics220-helicopters/ics220-helicopters.component';
import { Ics220PersonnelComponent } from './comp/ics220-personnel/ics220-personnel.component';
import { Ics220PrepByComponent } from './comp/ics220-prep-by/ics220-prep-by.component';
import { Ics220ReadyAlertComponent } from './comp/ics220-ready-alert/ics220-ready-alert.component';
import { Ics220TaskMissionComponent } from './comp/ics220-task-mission/ics220-task-mission.component';
import { IapReorderWindowComponent } from '../modals/iap-reorder-window/iap-reorder-window.component';

@Component({
  selector: 'app-ics220-form',
  templateUrl: './ics220-form.component.html',
  styleUrls: ['./ics220-form.component.css']
})
export class Ics220FormComponent implements OnInit, AfterViewInit {
  @ViewChild('ics220SunriseSunset') ics220SunriseSunset: Ics220SunriseSunsetComponent;
  @ViewChild('ics220FixedWing') ics220FixedWing: Ics220FixedWingComponent;
  @ViewChild('ics220Frequencies') ics220Frequencies: Ics220FrequenciesComponent;
  @ViewChild('ics220Helicopters') ics220Helicopters: Ics220HelicoptersComponent;
  @ViewChild('ics220Personnel') ics220Personnel: Ics220PersonnelComponent;
  @ViewChild('ics220PrepBy') ics220PrepBy: Ics220PrepByComponent;
  @ViewChild('ics220ReadyAlert') ics220ReadyAlert: Ics220ReadyAlertComponent;
  @ViewChild('ics220TaskMission') ics220TaskMission: Ics220TaskMissionComponent;
  @ViewChild('reorderWindow') reorderWindow: IapReorderWindowComponent;

  tabName = 'block3-4';
  tabNames = ['block3-4', 'block5-6', 'block7', 'block8', 'block9', 'block10', 'block11', 'block12'];
  showActionBar = true;
  iapPlanId = 0;

  iapForm220Vo = <IapForm220Vo>{id: 0};
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
    this.showActionBar = (this.tabName === 'block10' || this.tabName === 'block12' ? false : true );
    switch (name) {
      case 'block3-4':
        this.ics220SunriseSunset.reloadPage(this.iapForm220Vo);
        break;
      case 'block5-6':
        this.ics220ReadyAlert.reloadPage(this.iapForm220Vo);
        break;
      case 'block7':
        this.ics220Personnel.reloadPage(this.iapForm220Vo);
        break;
      case 'block8':
        this.ics220Frequencies.reloadPage(this.iapForm220Vo);
        break;
      case 'block9':
        this.ics220FixedWing.reloadPage(this.iapForm220Vo);
        break;
      case 'block10':
        this.ics220Helicopters.reloadPage(this.iapForm220Vo);
        break;
      case 'block11':
        this.ics220PrepBy.reloadPage(this.iapForm220Vo);
        break;
      case 'block12':
        this.ics220TaskMission.reloadPage(this.iapForm220Vo);
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
    if ( name === 'block10' || name === 'block12') {
      return ( this.tabName === name ? 'w3-left dv-block-10-12' : 'hidden');
    } else {
      return ( this.tabName === name ? 'w3-left dv-block' : 'hidden');
    }
  }

  loadForm(formId, formType) {
    this.iapPlanService.getIapForm(formId, formType)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_IAP_FORM_220' ) {
        this.iapForm220Vo = data['resultObject'] as IapForm220Vo;
        this.isFormLocked = this.iapForm220Vo.isFormLocked;
        this.openTab('block3-4');
      }
    });
  }

  save(saveNext) {
    let iapForm220VoToSave = null;
    this.saveNext = saveNext;

    switch (this.tabName) {
      case 'block3-4':
        iapForm220VoToSave = this.ics220SunriseSunset.getIapForm220Vo();
        break;
      case 'block5-6':
          iapForm220VoToSave = this.ics220ReadyAlert.getIapForm220Vo();
          break;
      case 'block7':
          iapForm220VoToSave = this.ics220Personnel.getIapForm220Vo();
          break;
      case 'block8':
          iapForm220VoToSave = this.ics220Frequencies.getIapForm220Vo();
          break;
      case 'block9':
          iapForm220VoToSave = this.ics220FixedWing.getIapForm220Vo();
          break;
      case 'block10':
          // Block 10 has its own save routine
          break;
      case 'block11':
          iapForm220VoToSave = this.ics220PrepBy.getIapForm220Vo();
          break;
      case 'block12':
          // Block 12 has its own save routine
          break;
    }

    if ( iapForm220VoToSave !== null ) {
      this.proceedSave(iapForm220VoToSave);
    }
  }

  cancel() {
    // reload tab
    this.openTab(this.tabName);
  }

  proceedSave(iapForm220VoToSave) {
    this.iapPlanService.updateIapForm220(iapForm220VoToSave)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_IAP_FORM_220' ) {
        this.iapForm220Vo = data['resultObject'] as IapForm220Vo;

        setTimeout(() => {
          if ( this.saveNext === false) {
            // reload tab
            this.openTab(this.tabName);
          } else {
            this.openNextTab();
          }
        });
      }
    });
  }

  helicopterUpdateEvent() {
    this.iapForm220Vo.iapHelicopterVos = this.ics220Helicopters.helicopterList;
  }

  taskUpdateEvent() {
    this.iapForm220Vo.iapAircraftTaskVos = this.ics220TaskMission.taskList;
  }

  ics220ReorderOpenEvent(name) {
    switch (name) {
      case 'Helicopters':
        this.reorderWindow.showModal = true;
        this.reorderWindow.listName = 'Helicopters';
        this.reorderWindow.gridColumnDefs = this.ics220Helicopters.gridColumnDefs;
        this.reorderWindow.windowLabel = 'Re-Order Helicopters';
        this.reorderWindow.openModal();
        setTimeout(() => {
          this.reorderWindow.list = this.iapForm220Vo.iapHelicopterVos;
          this.reorderWindow.loadWindow();
        });
        break;
      case 'Tasks':
        this.reorderWindow.showModal = true;
        this.reorderWindow.listName = 'Tasks';
        this.reorderWindow.gridColumnDefs = this.ics220TaskMission.gridColumnDefs;
        this.reorderWindow.windowLabel = 'Re-Order Task Missions';
        this.reorderWindow.openModal();
        setTimeout(() => {
          this.reorderWindow.list = this.iapForm220Vo.iapAircraftTaskVos;
          this.reorderWindow.loadWindow();
        });
        break;
    }
  }

  saveOrderEvent(name) {
    this.reorderWindow.closeModal();
    switch (name) {
      case 'Helicopters':
        this.ics220Helicopters.saveNewOrder(this.reorderWindow.list);
        break;
      case 'Tasks':
          this.ics220TaskMission.saveNewOrder(this.reorderWindow.list);
          break;
     }
  }

}
