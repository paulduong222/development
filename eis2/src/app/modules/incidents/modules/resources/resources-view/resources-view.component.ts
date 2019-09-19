import { Component, OnInit, AfterViewInit, ViewChild, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { IncidentResourceGridVo } from 'src/app/data/incident-resource-grid-vo';
import { ResourceGridComponent } from 'src/app/modules/incidents/modules/resources/resource-grid/resource-grid.component';
import { IncidentResourceService } from 'src/app/service/incident-resource.service';
import { NotificationService } from 'src/app/service/notification-service';
import { HeightCalc } from '../../../../../height-calc';
import { IncidentResourceVo } from 'src/app/data/incident-resource-vo';
import { IncidentResourceHelper } from '../helpers/incident-resource-helper';
import { ResourcesFormComponent } from '../resources-form/resources-form.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { IncidentGroupService } from 'src/app/service/incident-group.service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { ResourcesTimeComponent } from '../resources-time/resources-time.component';
import { QuickStatsWindowComponent } from '../modals/quick-stats-window/quick-stats-window.component';
import { ResourcesTimeadjComponent } from '../resources-timeadj/resources-timeadj.component';
import { ResourcesCostComponent } from '../resources-cost/resources-cost.component';
import { SpecialPayVo } from 'src/app/data/special-pay-vo';
import { RateClassRateVo } from 'src/app/data/rate-class-rate-vo';
import { SystemService } from 'src/app/service/system.service';
import { ContractorVo } from 'src/app/data/contractor-vo';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';

@Component({
  selector: 'app-resources-view',
  templateUrl: './resources-view.component.html',
  styleUrls: ['./resources-view.component.css']
})
export class ResourcesViewComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('promptModalResView') promptModalResView: PromptModalComponent;
  @ViewChild('quickStatsWindow') quickStatsWindow: QuickStatsWindowComponent;
  @ViewChild('resourceGrid') resourceGrid: ResourceGridComponent;
  @ViewChild('appResourceForm') resourceForm: ResourcesFormComponent;
  @ViewChild('appResourceTime') resourceTime: ResourcesTimeComponent;
  @ViewChild('appResourceTimeAdj') resourceTimeAdj: ResourcesTimeadjComponent;
  @ViewChild('appResourceCost') resourceCost: ResourcesCostComponent;

  splitAreaLeftSize = 30;
  splitAreaRightSize = 70;
  resourceFilter: FormGroup;

  // holders for ad and time ref data
  specialPayVos = [];
  rateClassRateVos = [];
  rateClassRateData = [];
  contractorVos = [];
  contractorData = [];

  incidentResourceGridVos = [];

  incidentResourceHelper: IncidentResourceHelper = new IncidentResourceHelper();

  // selected resource from resource grid
  selectedIncidentResourceGridVo = null;

  // selected resource from getById
  selectedIncidentResourceVo = null;

  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;

  checkinDemobRole = false;
  timeRole = false;
  costRole = false;

  dialogueVo;

  constructor( private formBuilder: FormBuilder,
                public incidentSelectorService: IncidentSelectorService,
                private incidentGroupService: IncidentGroupService,
                public referenceDataService: ReferenceDataService,
                private systemService: SystemService,
                private notifyService: NotificationService,
                private incidentResourceService: IncidentResourceService) { }

  ngOnInit() {
    this.checkinDemobRole = true; // this.systemService.hasAnyRole(['ROLE_CHECKIN_DEMOB']);
    this.timeRole = true; // this.systemService.hasAnyRole(['ROLE_TIME']);
    this.costRole = true; // this.systemService.hasAnyRole(['ROLE_COST']);
    this.resourceFilter = this.formBuilder.group({
      filterName: 'All',
    });

    this.currentSelectedIncidentSelectorVo = this.incidentSelectorService.selectedGridRow;
    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        this.refreshGrid();
    });

    // when navigating from admin office or contractors,
    // selectedSubTab is not getting set correctly for some reason
    if (this.incidentSelectorService.resourcesMode === 'timeview') {
      this.incidentSelectorService.selectedSubTab = 'time';
    }

    if ( this.incidentSelectorService.resourcesMode === 'resview') {
      this.addResource();
    }

    this.resourceGrid.buildColumnDefs(this.incidentSelectorService.selectedSubTab);
    setTimeout(() => {
      this.loadReferenceData();
    });
    // this.refreshGrid();
  }

  ngAfterViewInit() {
 }

  ngOnDestroy() {
    this.incidentSelectorSubscription.unsubscribe();
  }

  calcHt() {
    return HeightCalc.calculateHeight('res');
  }

  getAddResourceButtonClass() {
    if ( this.incidentSelectorService.resourcesMode === 'resview'
          && this.selectedIncidentResourceGridVo === null) {
      return 'w3-small btn-selected';
    } else {
      return 'w3-small';
    }
  }

  getEditResourceButtonClass() {
    if ( this.incidentSelectorService.resourcesMode === 'resview'
          && this.selectedIncidentResourceGridVo !== null) {
      return 'w3-small btn-selected';
    } else {
      return 'w3-small';
    }
  }

  getPostTimeButtonClass() {
    if ( this.incidentSelectorService.resourcesMode === 'timeview') {
      return 'w3-small btn-selected';
    } else {
      return 'w3-small';
    }
  }

  getPostTimeAdjButtonClass() {
    if ( this.incidentSelectorService.resourcesMode === 'timeadjview') {
      return 'w3-small btn-selected';
    } else {
      return 'w3-small';
    }
  }

  getViewCostsButtonClass() {
    if ( this.incidentSelectorService.resourcesMode === 'costview') {
      return 'w3-small btn-selected';
    } else {
      return 'w3-small';
    }
  }

  getResourceFormClass() {
    if ( this.incidentSelectorService.selectedSubTab === 'checkin'
            || this.incidentSelectorService.selectedSubTab === 'demob') {
      return 'dv-res-checkin-demob';
    }
    if ( this.incidentSelectorService.selectedSubTab === 'time'
            || this.incidentSelectorService.selectedSubTab === 'timeadj'
            || this.incidentSelectorService.selectedSubTab === 'cost') {
      return 'dv-res-time-cost';
    }
  }

  loadReferenceData() {
    this.showMessage('Loading Reference Data...', 'Processing Request', false, '');

    let incidentId = 0;
    let incidentGroupId = 0;

    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT'
          && this.currentSelectedIncidentSelectorVo.parentGroupId === null) {
       incidentId = this.currentSelectedIncidentSelectorVo.incidentId;
    }
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT'
          && this.currentSelectedIncidentSelectorVo.parentGroupId !== null) {
       incidentGroupId = this.currentSelectedIncidentSelectorVo.parentGroupId;
    }
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
       incidentGroupId = this.currentSelectedIncidentSelectorVo.incidentGroupId;
    }

    // get data from service
    this.referenceDataService
      .getResourceReferenceData(incidentId, incidentGroupId)
      .subscribe(data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'GET_RESOURCE_REF_DATA'
                && data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
            this.incidentSelectorService.resourceAgencyTypeData = data['resultObject']['agencyDropdownData'];
            this.incidentSelectorService.resourceJetportTypeData = data['resultObject']['jetportDropdownData'];
            this.incidentSelectorService.resourceKindTypeData = data['resultObject']['kindDropdownData'];
            this.incidentSelectorService.resourceUnitTypeData = data['resultObject']['unitDropdownData'];
            this.incidentSelectorService.resourceIncidentTypeData = data['resultObject']['incidentData'];
            this.incidentSelectorService.resourceIncAcctCodeTypeData = data['resultObject']['incidentAccountCodeData'];
            this.incidentSelectorService.resourceAccrualCodeTypeData = data['resultObject']['accrualCodeData'];
            this.contractorVos = data['resultObject']['contractorVos'] as ContractorVo[];
            this.contractorVos.forEach(cvo => {
              this.contractorData.push(
                <DropdownData>{
                  id: cvo.id
                  , code: cvo.name
                  , desc: ''
                }
              );
            });
            this.specialPayVos = data['resultObject']['specialPayVos'] as SpecialPayVo[];
            this.rateClassRateVos = data['resultObject']['rateClassRateVos'] as RateClassRateVo[];
            this.rateClassRateVos.forEach(vo => {
              this.rateClassRateData.push(
                <DropdownData>{
                  id: vo.id
                  , code: vo.rateClassName
                  , desc: ''
                }
              );
            });

            this.refreshGrid();
          }
      });
  }

  refreshGrid() {
    this.showMessage('Loading Resource Grid...', 'Processing Request', true, '');

    // reset grid info
    this.incidentResourceGridVos = [];
    this.resourceGrid.resetGrid();

    // get data from service
    this.incidentResourceService
      .getIncidentResourceGrid(this.currentSelectedIncidentSelectorVo.incidentId, this.currentSelectedIncidentSelectorVo.incidentGroupId)
      .subscribe(data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_RESOURCE_GRID'
                && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
                  this.incidentResourceGridVos = data['recordset'] as IncidentResourceGridVo[];
                  this.promptModalResView.closeModal();
                  // this.resourceGrid.rowData = this.incidentResourceGridVos;
                  setTimeout(() => {
                    this.onFilterGrid(this.resourceFilter.get('filterName').value);
                    this.resourceGrid.suppressSelectionChanged = false;
                  });
                  /*
                  this.incidentResourceGridVos.forEach(row => {
                    if ( row.gridRowId === 'A-1.4-10003') {
                      console.log('found row ' + JSON.stringify(row));
                    }
                  });
                  */
          }
      });
  }

  /*
   * Filter gridData based on filter type
   */
  onFilterGrid(filter) {
    let vos: IncidentResourceGridVo[];

    switch (filter) {
      case 'All':
        vos = this.incidentResourceGridVos;
        break;
      case 'Aircraft':
        vos = this.incidentResourceGridVos.filter(row => row.requestCategory === 'A');
        break;
      case 'Crews':
        break;
      case 'Overhead':
        break;
      case 'All Personnel':
        break;
      case 'All Non-Personnel':
        break;
    }

    this.resourceGrid.setRowData(vos);
  }


  customizeColumnsEvent() {

  }

  expandRetractEvent() {
    if ( this.splitAreaLeftSize > 30 ) {
      this.splitAreaLeftSize = 30;
      this.splitAreaRightSize = 70;
    } else {
      this.splitAreaLeftSize = 100;
      this.splitAreaRightSize = 0;
    }
  }

  refreshGridEvent() {
    this.refreshGrid();
  }

  clearFilterEvent() {

  }

  resourceSelectedEvent(irGridVo) {
    this.selectedIncidentResourceGridVo = irGridVo;
    this.getIncidentResourceById();
  }

  getIncidentResourceById() {
//    console.log('getIncidentResourceById() ' + this.selectedIncidentResourceGridVo.incidentResourceId);
    this.showMessage('Loading Resource ...', 'Processing Request', true, '');
    this.incidentResourceService
      .getIncidentResourceById(this.selectedIncidentResourceGridVo.incidentResourceId)
      .subscribe(data => {
          this.notifyService.inspectResult(data);
          this.promptModalResView.closeModal();
          if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_RESOURCE_BY_ID'
                && data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
            this.selectedIncidentResourceVo = data['resultObject'] as IncidentResourceVo;
            this.selectedIncidentResourceVo.workPeriodVo.airTravelQuestions.forEach(q => {
              if ( q.questionValue === null ) {
                q.questionValue = false;
              }
            });
            setTimeout(() => {
              if ( this.incidentSelectorService.resourcesMode === 'resview' ) {
                this.resourceForm.incidentResourceVo = Object.assign({}, this.selectedIncidentResourceVo);
                this.resourceForm.populateForm();
              }
              if ( this.incidentSelectorService.resourcesMode === 'timeview' ) {
                this.resourceTime.incidentResourceVo = Object.assign({}, this.selectedIncidentResourceVo);
                this.resourceTime.reset();
              }


            });
//            console.log(JSON.stringify(this.selectedIncidentResourceVo));
          }
      });
  }

  showMessage(msg, title, updateMsgOnly, btn1Label) {
    if ( updateMsgOnly === true && this.promptModalResView.isOpen === true) {
      this.promptModalResView.promptMessage1 = msg;
    } else {
      this.promptModalResView.reset();
      this.promptModalResView.promptTitle = title;
      this.promptModalResView.promptMessage1 = msg;
      if ( btn1Label !== '' ) { this.promptModalResView.button1Label = btn1Label; }
      this.promptModalResView.openModal();
    }
  }

  postTimeView() {
    this.incidentSelectorService.resourcesMode = 'timeview';
    setTimeout(() => {
      if ( this.incidentSelectorService.resourcesMode === 'timeview' ) {
        this.resourceTime.incidentResourceVo = Object.assign({}, this.selectedIncidentResourceVo);
        this.resourceTime.reset();
      }
    });
  }

  postTimeAdjView() {
    this.incidentSelectorService.resourcesMode = 'timeadjview';
    setTimeout(() => {
      if ( this.incidentSelectorService.resourcesMode === 'timeadjview' ) {
        // this.resourceTimeAdj.incidentResourceVo = Object.assign({}, this.selectedIncidentResourceVo);
        // this.resourceTimeAdj.reset();
      }
    });
  }

  costView() {
    this.incidentSelectorService.resourcesMode = 'costview';
    setTimeout(() => {
      if ( this.incidentSelectorService.resourcesMode === 'costview' ) {
        //  this.resourceTime.incidentResourceVo = Object.assign({}, this.selectedIncidentResourceVo);
        // this.resourceTime.reset();
      }
    });
  }

  addResource() {
    this.incidentSelectorService.resourcesMode = 'resview';
    this.resourceGrid.clearSelected();
    this.selectedIncidentResourceGridVo = null;

    setTimeout(() => {
      this.selectedIncidentResourceVo = this.incidentResourceHelper.initNewIncidentResourceVo();
      this.resourceForm.incidentResourceVo = Object.assign({}, this.selectedIncidentResourceVo);
      this.resourceForm.populateForm();
    });
  }

  editResource() {
    if ( this.selectedIncidentResourceGridVo !== null) {
      this.incidentSelectorService.resourcesMode = 'resview';
      setTimeout(() => {
        if ( this.incidentSelectorService.resourcesMode === 'resview' ) {
          this.resourceForm.incidentResourceVo = Object.assign({}, this.selectedIncidentResourceVo);
          this.resourceForm.populateForm();
        }
      });
}
  }

  deleteResource(resubmit: boolean) {
    if ( this.selectedIncidentResourceGridVo !== null && this.selectedIncidentResourceVo !== null) {
//      this.resourceGrid.removeRowByIncidentResourceId(this.selectedIncidentResourceGridVo.id);

        if (resubmit === false ) {
          this.dialogueVo = <DialogueVo>{};
        }

       this.incidentResourceService.deleteIncidentResource(
          this.selectedIncidentResourceVo.id, this.selectedIncidentResourceVo.resourceVo.id
          , this.dialogueVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        this.dialogueVo = data as DialogueVo;
        this.inspectCoa(data);
      });
    } else {
      // show messsage
    }
  }

  inspectCoa(data) {
    const coaName = data['courseOfActionVo']['coaName'];
    switch (coaName) {
      case 'CHECK_TIME_COST_RECORDS':
        this.showPrompt( coaName, 'Resource', data['courseOfActionVo']['promptVo']['messageProperty'], 'Yes', 'No', '');
        break;
      case 'DELETE_INCIDENT_RESOURCE_COMPLETE':
        break;
    }
  }

  showPrompt(mode, title, msg, btn1, btn2, btn3) {
    this.promptModalResView.reset();
    this.promptModalResView.promptMode = mode;
    this.promptModalResView.promptTitle = title;
    this.promptModalResView.promptMessage1 = msg;
    this.promptModalResView.button1Label = btn1;
    this.promptModalResView.button2Label = btn2;
    this.promptModalResView.button3Label = btn3;
    this.promptModalResView.openModal();
  }


  saveResource() {
    this.resourceForm.saveResource(false);
  }

  saveResourceEvent(gridVo: IncidentResourceGridVo) {
    this.resourceGrid.updateRowByIncidentResourceId(gridVo);
  }

  cancelResource() {

  }

  promptActionResult(btnEvent) {
    this.promptModalResView.closeModal();
    const coaName = this.promptModalResView.promptMode;
    switch (coaName) {
      case 'CHECK_TIME_COST_RECORDS':
          if ( btnEvent === 'Yes') {
            // PromptResult.Yes == 1
            this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 1;
            this.deleteResource(true);
          }
          break;
    }
  }

  openQuickStats() {
    this.quickStatsWindow.openModal();
    this.quickStatsWindow.incidentId = this.currentSelectedIncidentSelectorVo.incidentId;
    this.quickStatsWindow.incidentGroupId = this.currentSelectedIncidentSelectorVo.incidentGroupId;
    this.quickStatsWindow.loadWindow();
  }
}
