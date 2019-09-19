import { Component, Output, EventEmitter, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { Observable } from 'rxjs';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { CheckInSettingsModalComponent } from '../modals/check-in-settings-modal/check-in-settings-modal.component';
import { DemobSettingsModalComponent } from '../modals/demob-settings-modal/demob-settings-modal.component';
import { IapSettingsModalComponent } from '../modals/iap-settings-modal/iap-settings-modal.component';
import { CostSettingsModalComponent } from '../modals/cost-settings-modal/cost-settings-modal.component';
import { TrainingSettingsModalComponent } from '../modals/training-settings-modal/training-settings-modal.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { SystemService } from 'src/app/service/system.service';
@Component({
  selector: 'app-incidents-nav-bar',
  templateUrl: './incidents-nav-bar.component.html',
  styleUrls: ['./incidents-nav-bar.component.css']
})
export class IncidentsNavBarComponent implements OnInit {
  @Output() menuSelectEvent = new EventEmitter<string>();
  showIncidentDropdown = false;
  frm: FormGroup;
  showReportsNavBar = false;
  _name = '';
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  checkinDemobRole = false;
  timeRole = false;
  costRole = false;
  tnspRole = false;
  iapRole = false;
  @ViewChild('promptModalNavBar') promptModal: PromptModalComponent;
  @ViewChild('checkInSettingsModal') checkInSettingsModal: CheckInSettingsModalComponent;
  @ViewChild('demobSettingsModal') demobSettingsModal: DemobSettingsModalComponent;
  @ViewChild('iapSettingsModal') iapSettingsModal: IapSettingsModalComponent;
  @ViewChild('costSettingsModal') costSettingsModal: CostSettingsModalComponent;
  @ViewChild('trainingSettingsModal') trainingSettingsModal: TrainingSettingsModalComponent;

  constructor(private router: Router
                , private fb: FormBuilder
                , private systemService: SystemService
                , public incidentSelectorService: IncidentSelectorService) { }

  ngOnInit() {
    this.initForm();
    this.checkinDemobRole = true; // this.systemService.hasAnyRole(['ROLE_CHECKIN_DEMOB']);
    this.timeRole = true; // this.systemService.hasAnyRole(['ROLE_TIME']);
    this.costRole = true; // this.systemService.hasAnyRole(['ROLE_COST']);
    this.iapRole = true; // this.systemService.hasAnyRole(['ROLE_IAP']);
    this.tnspRole = true; // this.systemService.hasAnyRole(['ROLE_TRAINING_SPECIALIST']);
    this.showIncidentDropdown = (this.incidentSelectorService.isManagedAsGroup === true ? false : true );
//    this.incidentSelectorService.selectedSubTab = '';

    this.currentSelectedIncidentSelectorVo = this.incidentSelectorService.selectedGridRow;
    this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
      this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
    });

  }

  initForm() {
    this.frm = this.fb.group({
      manageAsGroup: new FormControl(this.incidentSelectorService.isManagedAsGroup)
      , selectedIncident: new FormControl(this.incidentSelectorService.selectedDropdownIncidentId)
    });
  }

  tabClick(tabName, subTabName, resmode) {
    if ( this.hasValidSelector() === false ) {
      this.showPromptMessage('e-ISuite', 'You must first select an Incident/Incident Group');
      return;
    }

    // set selected top-level tab
    this.incidentSelectorService.selectedTab = tabName;
    // set selected sub-level tab
    console.log('nav-bar selectedSubTab ' + subTabName);
    this.incidentSelectorService.selectedSubTab = subTabName;
    // set resource mode
    this.incidentSelectorService.resourcesMode = resmode;

    const restabs = 'checkin,demob,time,cost';
    if ( restabs.indexOf(subTabName) > -1 ) {
      this.incidentSelectorService.setCurrentResourceTab(subTabName);
    }
//    this.incidentSelectorService.setCurrentNavigationPath(tabName + '/' + subTabName + '/' + resmode);

    this._name = tabName;
    if ( tabName.indexOf('reports') > 0) {
      this.showReportsNavBar = true;
    } else {
      this.showReportsNavBar = false;
    }

    setTimeout(() => {
      this.router.navigate([tabName]);
    });
  }

  menuSelect(menuName: string) {
    this._name = menuName;
    this.showReportsNavBar = menuName.startsWith('/incidents/reports');
    this.menuSelectEvent.emit(menuName);
  }

  getStyle(area, subTab) {
    if (this.router.url === area || this.router.url.startsWith(area)) {
      if ( this.incidentSelectorService.selectedSubTab === subTab) {
        return 'btn-selected';
      } else {
        return 'btn-normal';
      }
    } else {
      return 'btn-normal';
    }
  }

  getActiveReportsTab(rpttab) {
    return 'btn-selected';
  }

  /*
   * Event Handler for when manage as group checkbox is changed
   */
  onChangeManageAsGroup( event: any ) {
    this.incidentSelectorService.setIsManagedAsGroup(event.target.checked);
    this.showIncidentDropdown = (this.incidentSelectorService.isManagedAsGroup === true ? false : true );
    if (this.incidentSelectorService.isManagedAsGroup === false ) {
      // default incident dropdown to first item
      if ( this.incidentSelectorService.selectedGridRow.children.length > 0 ) {
        // set selected dropdown incident id
        this.incidentSelectorService.setSelectedDropdownIncidentId(
          this.incidentSelectorService.selectedGridRow.children[0].incidentId);

        // set the selected vo
        this.incidentSelectorService.setSelectedIncidentSelectorVo(
          this.incidentSelectorService.selectedGridRow.children[0]);

        this.initForm();
      }
    } else {
        // set the selected vo
        this.incidentSelectorService.setSelectedIncidentSelectorVo(
          this.incidentSelectorService.selectedGridRow);
    }
  }

  /*
   * Event Handler for when manage as group dropdown row is selected
   */
  onChangeSelectIncident( event: any ) {
    // set selected dropdown incident id
    this.incidentSelectorService.setSelectedDropdownIncidentId(
       this.incidentSelectorService.selectedGridRow.children[event.target.selectedIndex].incidentId);

    // set the selected vo
    this.incidentSelectorService.setSelectedIncidentSelectorVo(
       this.incidentSelectorService.selectedGridRow.children[event.target.selectedIndex]);
  }

  showIncidentInfo(): boolean {
    if ( this.incidentSelectorService.selectedGridRow !== undefined &&
        this.incidentSelectorService.selectedGridRow.incidentId > 0 ) {
      return true;
    } else {
      return false;
    }
  }

  showIncidentGroupInfo(): boolean {
    if ( this.incidentSelectorService.selectedGridRow !== undefined &&
        this.incidentSelectorService.selectedGridRow.incidentGroupId > 0 ) {
      return true;
    } else {
      return false;
    }
  }

  hasValidSelector(): boolean {
//    console.log(JSON.stringify(this.currentSelectedIncidentSelectorVo));
    if ( this.currentSelectedIncidentSelectorVo !== undefined
        && ( this.currentSelectedIncidentSelectorVo.incidentGroupId > 0 || this.currentSelectedIncidentSelectorVo.incidentId > 0 )) {
          return true;
    }
    return true;  // always return true until we send to qa
  }

  openCheckInSettingsModal() {
    if ( this.hasValidSelector() === true ) {
      this.checkInSettingsModal.openModal('check-in-settings-modal');
      this.checkInSettingsModal.currentSelectedIncidentSelectorVo = Object.assign({}, this.currentSelectedIncidentSelectorVo);
      this.checkInSettingsModal.selectedTab = 'quickstats';
      this.checkInSettingsModal.loadPage();
    } else {
      this.showPromptMessage('e-ISuite', 'Please select an Incident/Incident Group before accessing the Check-In Settings area.');
    }
  }

  closeCheckInSettingsModalEvent() {
    console.log('check in settings window close event callback');
  }

  openDemobSettingsModal() {
    if ( this.hasValidSelector() === true ) {
      this.demobSettingsModal.openModal('demob-settings-modal');
      this.demobSettingsModal.currentSelectedIncidentSelectorVo = Object.assign({}, this.currentSelectedIncidentSelectorVo);
      this.demobSettingsModal.selectedTab = 'checkoutform';
      this.demobSettingsModal.loadPage();
    } else {
      this.showPromptMessage('e-ISuite', 'Please select an Incident/Incident Group before accessing the Demob Settings area.');
    }
  }

  closeDemobSettingsModalEvent() {
    console.log('demob settings window close event callback');
  }

  openIapSettingsModal() {
    if ( this.hasValidSelector() === true ) {
      this.iapSettingsModal.openModal('iap-settings-modal');
    } else {
      this.showPromptMessage('e-ISuite', 'Please select an Incident/Incident Group before accessing the IAP Settings area.');
    }
  }

  closeIapSettingsModalEvent() {
    console.log('iap settings window close event callback');
  }

  openCostSettingsModal() {
    if ( this.hasValidSelector() === true ) {
      this.costSettingsModal.openModal('cost-settings-modal');
      this.costSettingsModal.currentSelectedIncidentSelectorVo = Object.assign({}, this.currentSelectedIncidentSelectorVo);
      this.costSettingsModal.loadPage();
    } else {
      this.showPromptMessage('e-ISuite', 'Please select an Incident/Incident Group before accessing the Cost Settings area.');
    }
  }

  closeCostSettingsModalEvent() {
    console.log('cost settings window close event callback');
  }

  openTrainingSettingsModal() {
    if ( this.hasValidSelector() === true ) {
      this.trainingSettingsModal.openModal('training-settings-modal');
      this.trainingSettingsModal.currentSelectedIncidentSelectorVo = Object.assign({}, this.currentSelectedIncidentSelectorVo);
      this.trainingSettingsModal.selectedTab = 'incidentSettings';
      this.trainingSettingsModal.loadPage();
    } else {
       this.showPromptMessage('e-ISuite',
       'Please select an Incident/Incident Group before accessing the Training Specialist Settings area.');
    }
  }

  closeTrainingSettingsModalEvent() {
    console.log('training settings window close event callback');
  }

  showPromptMessage(title, msg) {
    this.promptModal.reset();
    this.promptModal.promptTitle = title;
    this.promptModal.promptMessage1 = msg;
    this.promptModal.button1Label = 'Ok';
    this.promptModal.openModal();
  }

  promptActionResult(evt: any) {
    this.promptModal.closeModal();
   }

}
