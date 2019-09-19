import { Component, OnInit, Input, ViewChild, AfterViewInit } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { SystemService } from 'src/app/service/system.service';
import { NotificationService } from 'src/app/service/notification-service';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { IncViewIncidentsComponent } from '../../inc/inc-view-incidents/inc-view-incidents.component';
import { IncViewIncidentGroupComponent } from '../../inc/inc-view-incident-group/inc-view-incident-group.component';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { TransToEntVo } from 'src/app/data/trans-to-ent-vo';
import { HeightCalc } from '../../../../../height-calc';

@Component({
  selector: 'app-data-transfer-view',
  templateUrl: './data-transfer-view.component.html',
  styleUrls: ['./data-transfer-view.component.css']
})
export class DataTransferViewComponent implements OnInit, AfterViewInit {

  @ViewChild('promptModalIncView') promptModalIncView: PromptModalComponent;
  @ViewChild('incGrid') incGrid: EisGridComponent;
  @ViewChild('incViewIncidents') incViewIncident: IncViewIncidentsComponent;
  @ViewChild('incViewIncidentGroup') incViewIncidentGroup: IncViewIncidentGroupComponent;
  @Input() transToEntVo: TransToEntVo = <TransToEntVo>{id: 0, transitionFileName: ''};
  isLoading = true;
  isPageLoaded = false;
  splitAreaLeftSize = 30;
  splitAreaRightSize = 70;
  viewMode = 'INCIDENT';
  actionMode = 'edit'; // either add, edit, or delete
  incidentGridList = [];
  currentEvent = {};  // holder for events from child components
  suppressOnSelectRow = false;
  currentView: string;
  isDataSteward = false;
  transToEntForm: FormGroup;

  gridColumnDefs = [
    {headerName: 'Incident #', field: 'incidentNumber', width: 130},
    {headerName: 'Event Type', field: 'eventType', width: 100},
    {headerName: 'Start Date', field: 'startDate', width: 100},
    {headerName: 'Jurisdiction', field: 'agency', width: 110},
    {headerName: 'Default Accounting Code', field: 'defaultAccountingCode'},
    {headerName: 'Default Accounting Code Agency', field: 'defaultAccountingCodeAgency'},
  ];


  constructor(private incidentSelectorService: IncidentSelectorService,
              private systemService: SystemService,
              private notifyService: NotificationService,
              private refDataService: ReferenceDataService,
              private fb: FormBuilder) {
    this.transToEntForm = this.fb.group({
      id: new FormControl(0),
      transitionFileName: new FormControl(''),
      password: new FormControl(''),
      verify: new FormControl(''),
      generate: new FormControl('')
    });
  }

  ngOnInit() {
    this.isDataSteward = true; // this.systemService.hasAnyRole(['ROLE_DATA_STEWARD']);

    // for SITE expand tree by default
    this.incGrid.groupDefaultExpanded = -1;

    // getDataPath tells ag-grid the field to use for the data hierarrchy
    this.incGrid.getDataPath = function(data) {
      return data.hierachalGroupField;
    };
    // autoGroupColumnDef is defining the column
    this.incGrid.autoGroupColumnDef = {
      headerName: 'Incident Name',
      filter: 'agTextColumnFilter',
      sortable: true,
      sort: 'asc',
      cellRendererParams: { suppressCount: true }
    };
    this.incGrid.getRowNodeId = function( row ) {
      return (row.type + row.id);
    };
      this.currentView = 'import_ent'
      this.initNewTransToEntVo();
  }

  initNewTransToEntVo() {
    this.transToEntVo = <TransToEntVo> {
      id: 0, 
      transitionFileName: '',
      password: '',
      verify: '',
      generate: false
    };
  }

  calcHt(){
    return HeightCalc.calculateHeight('inc');
  }

  selectView(view: string){
    this.currentView = view;
  }

  buttonClass(view: string) {
    if(this.currentView === view){
      return 'w3-small btn-selected';
    } 
    else {
      return 'w3-small';
    }
  }

  ngAfterViewInit() {
    if ( this.isDataSteward === true ) {
      setTimeout(() => {
        this.getEventTypes();
      });
    } else {
      setTimeout(() => {
        this.refreshGrid();
      });
    }
  }

  getEventTypes() {
    this.showMessage('Loading Event Types...', 'Processing Request', true, '');
    this.refDataService.getEventTypeList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_EVENT_TYPES'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET' ) {
                this.incidentSelectorService.eventTypeData = data['recordset'] as DropdownData[];
                this.getStateTypes();
        }
      });
  }

  getStateTypes() {
    this.showMessage('Loading States...', 'Processing Request', true, '');
    this.refDataService.getStateList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_STATES'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET' ) {
                this.incidentSelectorService.stateTypeData = data['recordset'] as DropdownData[];
                this.getAgencyTypes();
        }
      });
  }

  getAgencyTypes() {
    this.showMessage('Loading Agencies...', 'Processing Request', true, '');
    this.refDataService.getAgencyList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_AGENCY_TYPES'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET' ) {
                this.incidentSelectorService.agencyTypeData = data['recordset'] as DropdownData[];
                this.getOrgTypes();
        }
      });
  }

  getOrgTypes() {
    this.showMessage('Loading Unit Codes...', 'Processing Request', true, '');
    this.refDataService.getOrgTypeList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_ORG_TYPES'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET' ) {
                this.incidentSelectorService.organizationTypeData = data['recordset'] as DropdownData[];
                this.refreshGrid();
        }
      });
  }

  customizeColumnsEvent() {
    console.log('customColumnEvent');
  }

    /*
   * Event Handler for when refresh grid icon is clicked from <app-grid-icon-bar>
   */
  refreshGridEvent() {
    // clear selected grid row and filters
    this.incGrid.clearFilters();
    this.incGrid.clearSelected();

    this.refreshGrid();
    // this.addIncident();
  }

  clearFilterEvent() {
    this.incGrid.clearFilters();
 }

  /*
   * Refresh the grid and reset variables
  */
 refreshGrid() {
  this.showMessage('Loading Grid ...', 'Processing Request', true, '');

  // reset some vars if page is already loaded, user manually is refreshing grid
  if ( this.isPageLoaded === true ) {
    const incSelectorVo = <IncidentSelector2Vo>{id: 0};
    this.incidentSelectorService.setSelectedIncidentSelectorVo(incSelectorVo);
    this.incidentSelectorService.selectedGridRow = incSelectorVo;
  }

  this.incidentSelectorService.getGrid(this.systemService.userSessionVo['userId'])
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_SELECTOR_VOS' ) {
        this.incidentGridList = data['recordset'] as IncidentSelector2Vo[];
        // console.log(JSON.stringify(this.incidentGridList));
        // this.promptModalIncView.closeModal();
        this.isPageLoaded = true;
        this.onGridReadyEvent();
        this.promptModalIncView.closeModal();
      }
    });

  }

  showMessage(msg, title, updateMsgOnly, btn1Label) {
    if ( updateMsgOnly === true && this.promptModalIncView.isOpen === true) {
      this.promptModalIncView.promptMessage1 = msg;
    } else {
      this.promptModalIncView.reset();
      this.promptModalIncView.promptTitle = title;
      this.promptModalIncView.promptMessage1 = msg;
      if ( btn1Label !== '' ) { this.promptModalIncView.button1Label = btn1Label; }
      this.promptModalIncView.openModal();
    }
  }

  onGridReadyEvent() {
    console.log('onGridREadyEvent ' + this.incidentSelectorService.selectedGridRow);
    setTimeout(() => {
      if ( this.incidentSelectorService.selectedGridRow !== undefined
            && this.incidentSelectorService.selectedGridRow.id > 0 ) {

         this.suppressOnSelectRow = true;

         // row.type is from the IncidentSelector2Vo.type field (either INCIDENT or INCIDENTGROUP)
         this.viewMode = this.incidentSelectorService.selectedGridRow.type;

         this.incGrid.setSelectedRow(this.incidentSelectorService.selectedGridRow.type
            , this.incidentSelectorService.selectedGridRow.id);

         if ( this.incidentSelectorService.selectedGridRow.type === 'INCIDENTGROUP' ) {
          }

          this.actionMode = 'edit';
      }
    });
  }

  expandRetractEvent() {
    if ( this.isDataSteward === true ) {
      if ( this.splitAreaLeftSize > 30 ) {
        this.splitAreaLeftSize = 30;
        this.splitAreaRightSize = 70;
      } else {
        this.splitAreaLeftSize = 100;
        this.splitAreaRightSize = 0;
      }
    }
  }

      /*
   * Event Handler for when a row is selected from <app-eis-grid>
   */
  onSelectIncidentRow(row: any) {
    console.log('onSelectedIncidentRow()');
    if ( row !== undefined && this.suppressOnSelectRow === false) {
      // set defaults when new selection is made
      this.incidentSelectorService.isManagedAsGroup = true;
      this.incidentSelectorService.selectedGridRow = row as IncidentSelector2Vo;
      this.incidentSelectorService.setSelectedIncidentSelectorVo(row as IncidentSelector2Vo);

      // for SITE, edit inc and edit inc group share same button on top action bar
      // for ENT, TODO check if there is an edit incident group btn
      this.actionMode = 'edit';

      // row.type is from the IncidentSelector2Vo.type field (either INCIDENT or INCIDENTGROUP)
      this.viewMode = row.type;

      if ( this.incidentSelectorService.selectedGridRow.type === 'INCIDENTGROUP' ) {
        if ( this.incViewIncidentGroup !== undefined ) {
          this.incViewIncidentGroup.getIncidentGroupById(this.incidentSelectorService.selectedGridRow.incidentGroupId);
        }
      }
      if ( this.incidentSelectorService.selectedGridRow.type === 'INCIDENT' ) {
        if ( this.incViewIncident !== undefined ) {
          this.incViewIncident.getIncidentById(this.incidentSelectorService.selectedGridRow.incidentId);
        }
      }

    }
    this.transToEntForm.setValue({
      id : 0, 
      transitionFileName : row.name,
      password : '',
      verify : '',
      generate: false
    });
    this.suppressOnSelectRow = false;
  }
}
