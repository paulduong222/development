import { Component, OnInit, Output, EventEmitter, ViewChild, AfterViewInit } from '@angular/core';
import { DropdownData } from 'src/app/data/dropdowndata';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingSpecialistSettingsService } from 'src/app/service/training-specialist-settings.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { TrainingSettingsVo } from 'src/app/data/training-settings-vo';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { TrainingSettingsData } from 'src/app/data/rest/training-settings-data';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { PriorityProgramVo } from 'src/app/data/priority-program-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { PriorityProgramData } from 'src/app/data/rest/priority-program-data';
import { TrainingContactVo } from 'src/app/data/training-contact-vo';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentGroupService } from 'src/app/service/incident-group.service';
import { AddressVo } from 'src/app/data/address-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { TrainingContactData } from 'src/app/data/rest/training-contact-data';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';


@Component({
  selector: 'app-training-settings-modal',
  templateUrl: './training-settings-modal.component.html',
  styleUrls: ['./training-settings-modal.component.css']
})

export class TrainingSettingsModalComponent implements OnInit, AfterViewInit {
  @Output() closeModalEvent = new EventEmitter();
  @ViewChild('promptModalTrainingSettings') promptModalTrainingSettings: PromptModalComponent;
  @ViewChild('ddIncidents') ddIncidents: EisDropdownComponent;
  @ViewChild('ddComplexity') ddComplexity: EisDropdownComponent;
  @ViewChild('grdPriority') grdPriority: EisGridComponent;
  @ViewChild('ddState') ddState: EisDropdownComponent;
  @ViewChild('grdAllResources') grdAllResources: EisGridComponent;
  @ViewChild('grdTrainingSpecialists') grdTrainingSpecialists: EisGridComponent;

  selectedTab = 'incidentSettings';
  dialogueVo: DialogueVo;
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  promptMode = '';
  actionMode = 'add';

  // incident settings tab
  incSettingsForm: FormGroup;
  bAllIncidents: boolean = false;
  incidentDropdownData = [];
  selectedIncidentDropdownData: DropdownData;
  trainingSettingsVos: TrainingSettingsVo[];
  complexityData: DropdownData[] = [];
  complexityTypeDropdownData: DropdownData;
  fuelTypeVos: DropdownData[];

  //contact information tab
  resGridColumnDefs = [
    {headerName: 'Request #', field: 'requestNumber', width: 100},
    {headerName: 'Resource Name', field: 'resourceName', width: 180},
    {headerName: 'Item Code', field: 'itemCode', width: 100},
    {headerName: 'Item Description', field: 'itemDescription', width: 264},
    {headerName: 'Status', field: 'status', width: 90},
    {headerName: 'Unit ID', field: 'unitId', width: 90},
    {headerName: 'Unit Description', field: 'unitDescription', width: 264}
  ];

  tsGridColumnDefs = [
    {headerName: 'Request #', field: 'requestNumber', width: 100},
    {headerName: 'Resource Name', field: 'resourceName', width: 160},
    {headerName: 'Item Code', field: 'itemCode', width: 100},
    {headerName: 'Item Description', field: 'itemDescription', width: 250},
    {headerName: 'Status', field: 'status', width: 90},
    {headerName: 'Unit ID', field: 'unitId', width: 90},
    {headerName: 'Unit Description', field: 'unitDescription', width: 220},
    {headerName: 'Active', field: 'active', cellRenderer: 'checkboxRenderer', filter: false, width: 80},
  ];
  tcForm: FormGroup
  resTrainingContactVos: TrainingContactVo[] = [];
  tsTrainingContactVos: TrainingContactVo[] = [];
  gridSelectedTrainingContactVo: TrainingContactVo;
  trainingContactVo: TrainingContactVo;
  stateTypeDropdownData: DropdownData;
  resourceName: string = '';
  requestNumber: string = '';

  // priority programs tab
  priorityProgramVos: PriorityProgramVo[] = [];
  priorityProgramVo: PriorityProgramVo;
  ppForm: FormGroup
  gridSelectedPriorityProgramVo: PriorityProgramVo;
  pGridColumnDefs = [
    {headerName: 'Priority Program', field: 'code', width: 380}
  ];

  constructor(private modalService: ModalService,
              private fb: FormBuilder,
              public incidentSelectorService: IncidentSelectorService,
              private incidentGroupService: IncidentGroupService,
              private notifyService: NotificationService,
              private referenceDataService: ReferenceDataService,
              private trainingSpecialistSettingsService:TrainingSpecialistSettingsService ) {  
  }

  ngOnInit() {
    this.initForms();
    this.grdAllResources.getRowNodeId = function( row ) {
      return row.incidentResourceId;
    };
  }

  ngAfterViewInit() {
  }

  initForms(){
    this.incSettingsForm = this.fb.group({
      isAllIncidents: new FormControl(false),
      incidentVo: new FormControl({}),
      complexityVo: new FormControl(''),
      acres: new FormControl(''),
      isBrush: new FormControl(false),
      isGrass: new FormControl(false),
      isSlash: new FormControl(false),
      isTimber: new FormControl(false)
    });

    this.ppForm = this.fb.group({
      priorityProgramCode: new FormControl('')  
    });

    this.tcForm = this.fb.group({  
      active: new FormControl(false),
      addressLine: new FormControl(''),
      city: new FormControl(''),
      countryCodeSubdivisionVo: new FormControl({}),
      zip: new FormControl(''),
      phone: new FormControl(''),
      email: new FormControl('')
    });
    
  }

  loadPage() {
    // training settings tab
    if (this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      this.loadIncidentList();
    }
    this.bAllIncidents = false;

    this.getComplexities();
    this.getTrainingSettings();

    // training contact info tab
    this.getTrainingContactGrids();
    this.addTrainingContact();
    
    // priority program tab
    this.getPriorityPrograms();
    this.addPriorityProgram();
  }

  dataTabSelect(tabname) {
    this.selectedTab = tabname;
    if (this.selectedTab === 'trainingContact') {
    }
  }

  getTabClass(tabname) {
    return (this.selectedTab === tabname ? '' : 'hidden');
  }

  getApplyToAllIncidentsClass() {
    return ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? '' : 'hidden' );
  }

  setAllIncidents(event) {
    this.bAllIncidents = event.target.checked;
  }

  getIncidentSelectorClass() {
    if (this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      return (this.bAllIncidents ? 'hidden' : '');
    } else {
      return 'hidden';
    }
  }

  populateForm() {
    this.complexityTypeDropdownData = this.ddComplexity.getDropdownDataObjectById(-2);
    this.selectedIncidentDropdownData = this.ddIncidents.getDropdownDataObjectById(-2);

    this.incSettingsForm.setValue({
      isAllIncidents: this.bAllIncidents,
      incidentVo: {},
      complexityVo: {},
      acres: this.trainingSettingsVos[0].numberOfAcres,
      isBrush: this.fuelTypesChecked(this.trainingSettingsVos[0].fuelTypeVos, 'B'),
      isGrass: this.fuelTypesChecked(this.trainingSettingsVos[0].fuelTypeVos, 'G'),
      isSlash: this.fuelTypesChecked(this.trainingSettingsVos[0].fuelTypeVos, 'S'),
      isTimber: this.fuelTypesChecked(this.trainingSettingsVos[0].fuelTypeVos, 'T'),
    });

    setTimeout(() => {
      if (this.trainingSettingsVos[0].complexityVo) {
        this.complexityTypeDropdownData = this.ddComplexity.getDropdownDataObjectById(this.trainingSettingsVos[0].complexityVo.id);  
      }
      if (this.trainingSettingsVos[0].incidentVo) {
        this.selectedIncidentDropdownData = this.ddIncidents.getDropdownDataObjectById(this.trainingSettingsVos[0].incidentVo.id);
      }  
    });   
  }

  fuelTypesChecked(fuelTypeVos: DropdownData[], code: string) {
    for ( const fuelTypeVo of fuelTypeVos) {
      if (fuelTypeVo.code === code) {
        return true;
      } 
    }
    return false;
  }

  loadIncidentList() {
    this.incidentGroupService.getIncidentDropdownList(
      this.currentSelectedIncidentSelectorVo.incidentGroupId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_DROPDOWN_LIST') {
        this.incidentDropdownData = data['recordset'] as DropdownData[];
      }
    });
  }

  getComplexities() {
    this.referenceDataService.getComplexityList()
    .subscribe(data => {
      if ( data['courseOfActionVo']['coaName'] === 'GET_COMPLEXITIES') {
        this.complexityData = data['recordset'] as DropdownData[];
      }
    });
  }

  getTrainingSettings() {
    this.trainingSpecialistSettingsService
      .getTrainingSettings(this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ? this.currentSelectedIncidentSelectorVo.incidentId : 0, this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0)
      .subscribe(data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'GET_TRAINING_SETTINGS'
                && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
                  this.trainingSettingsVos = data['resultObject'] as TrainingSettingsVo[];
                  this.fuelTypeVos = data['resultObjectAlternate'] as DropdownData[];
                  this.populateForm();
          }
      });
  }


  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
    this.closeModalEvent.emit();
  }

  showPrompt(title, msg, btn1, btn2) {
    this.promptModalTrainingSettings.reset();
    this.promptModalTrainingSettings.promptTitle = title;
    this.promptModalTrainingSettings.promptMessage1 = msg;
    this.promptModalTrainingSettings.button1Label = btn1;
    this.promptModalTrainingSettings.button2Label = btn2;
    this.promptModalTrainingSettings.openModal();
  }

  promptActionResultTrainingSettings(event) {
    this.promptModalTrainingSettings.closeModal();
    if ( event === 'Yes' ) {
      switch (this.promptMode) {
        case 'PROMPT_DELETE_PRIORITY_PROGRAM':
          this.proceedWithDeletePriorityProgram();
          break;
        case 'PROMPT_DELETE_TRAINING_CONTACT':
          this.proceedWithDeleteTrainingContact();
          break;
        case 'PROMPT_EDIT_PRIORITY_PROGRAM':
            this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 4;
            this.savePriorityProgram(true);
          break;;
      }
    }
  }

  getStyle(menuName) {
    return ( this.selectedTab === menuName ? 'btn-selected' : '' );
  }

  populateFuelTypeVo(code: string):DropdownData {
    const fuelTypeVo: DropdownData = <DropdownData>{};
    for (const ftVo of this.fuelTypeVos) {
      if (ftVo.code === code) {
        fuelTypeVo.id = ftVo.id;
        fuelTypeVo.code = ftVo.code;
      }
    }
    return fuelTypeVo;
  }

  save() {
    var bPrompt:boolean = false;

    if (this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      if(this.bAllIncidents === false) {
        if(this.ddIncidents.selectedValue.id < 0) {
          bPrompt = true;
        }  
      }
    }

    if (bPrompt) {
      this.showPrompt('Training Specialist Incident Settings',
          'Incident is a required field.',
          'OK',
          '');     
    } else {
      this.saveTrainingSettings();
    }
  }

  saveTrainingSettings() {
    this.trainingSettingsVos[0].numberOfAcres = this.incSettingsForm.get('acres').value;
    
    if(this.ddComplexity.selectedValue.id > 0) {
      this.trainingSettingsVos[0].complexityVo = <DropdownData>{};
      this.trainingSettingsVos[0].complexityVo.id = this.ddComplexity.selectedValue.id;
    } else {
      this.trainingSettingsVos[0].complexityVo = null;
    }

    this.trainingSettingsVos[0].incidentVo.id = this.ddIncidents.selectedValue.id;

    const ftVos: DropdownData[] = [];
    
    if(this.incSettingsForm.get('isBrush').value === true) {
      ftVos.push(this.populateFuelTypeVo('B'));
    }
    if(this.incSettingsForm.get('isGrass').value === true) {
      ftVos.push(this.populateFuelTypeVo('G'));
    }
    if(this.incSettingsForm.get('isSlash').value === true) {
      ftVos.push(this.populateFuelTypeVo('S'));
    }
    if(this.incSettingsForm.get('isTimber').value === true) {
      ftVos.push(this.populateFuelTypeVo('T'));
    }

    this.trainingSettingsVos[0].fuelTypeVos = ftVos;

    const trainingsettingsData = <TrainingSettingsData> {
      trainingSettingsVo: this.trainingSettingsVos[0]
    }

    const incidentIds: number[] = [];
    if (this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      if (this.bAllIncidents) {
        for (let entry of this.currentSelectedIncidentSelectorVo.children) {
          incidentIds.push(entry.id);
        }
      } else {
        incidentIds.push(this.ddIncidents.selectedValue.id);
      }
    } else {
      incidentIds.push(this.currentSelectedIncidentSelectorVo.incidentId);
    }

    this.trainingSpecialistSettingsService.saveTrainingSettings(trainingsettingsData, this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0, incidentIds)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_TRAINING_SETTINGS') {
          this.trainingSettingsVos[0] = data['resultObject'] as TrainingSettingsVo;
          this.populateForm();
      }
    });
  }

  cancel() {
    this.getTrainingSettings();
  }

  // training contact info tab
  getTrainingContactGrids() {
    this.trainingSpecialistSettingsService.getTrainingContactGrids(this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ? this.currentSelectedIncidentSelectorVo.incidentId : 0, this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0)
      .subscribe(data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'GET_TRAINING_CONTACT_GRIDS'
                && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
                  this.tsTrainingContactVos = data['resultObjectAlternate'] as TrainingContactVo[];
                  this.resTrainingContactVos = data['resultObject'] as TrainingContactVo[];
          }
      }); 
  }

  onSelectAllResourcesGrid(row: TrainingContactVo) {
    if (row !== undefined) {
      this.grdTrainingSpecialists.clearSelected();
      this.gridSelectedTrainingContactVo = Object.assign({}, row);
      this.trainingContactVo = Object.assign({}, row);
      
      this.populateTrainingContactForm();
    }
  }

  onSelectTrainingSpecialistsGrid(row: TrainingContactVo) {
    if (row !== undefined) {
      this.grdAllResources.clearSelected();
      this.gridSelectedTrainingContactVo = Object.assign({}, row);
      this.trainingContactVo = Object.assign({}, row);
      
      this.populateTrainingContactForm();
    }
  }

  populateTrainingContactForm() {
    this.resourceName = this.trainingContactVo.resourceName;
    this.requestNumber = this.trainingContactVo.requestNumber;

    this.stateTypeDropdownData = this.ddState.getDropdownDataObjectById(-2);
        
    this.tcForm.setValue({
      active: this.trainingContactVo.active,
      addressLine:  this.trainingContactVo.addressVo === null ? '' : this.trainingContactVo.addressVo.addressLine1,
      city: this.trainingContactVo.addressVo === null ? '' : this.trainingContactVo.addressVo.city,
      countryCodeSubdivisionVo: {},
      zip: this.trainingContactVo.addressVo === null ? '' : this.trainingContactVo.addressVo.postalCode,
      phone: this.trainingContactVo.phone,
      email: this.trainingContactVo.email
    });

    setTimeout(() => {
      if (this.trainingContactVo.addressVo) {
        if (this.trainingContactVo.addressVo.countrySubdivisionVo) {
          this.stateTypeDropdownData = this.ddState.getDropdownDataObjectById(this.trainingContactVo.addressVo.countrySubdivisionVo.id);
        }
      }
  
    });
  } 

  saveTrainingContact() {
    if (this.trainingContactVo.incidentResourceId < 1) {
      this.showPrompt('Training Contact',
            'Please select a Training Contact to save.',
            'OK',
            '');
    } else {
      this.proceedSaveTrainingContact();
    }
  }


  proceedSaveTrainingContact() {
    this.actionMode = (this.trainingContactVo.id > 0 ?  this.actionMode = 'edit' :  this.actionMode = 'add');

    this.trainingContactVo.active = this.tcForm.get('active').value;
    this.trainingContactVo.addressVo = <AddressVo>{};
    this.trainingContactVo.addressVo.addressLine1 = this.tcForm.get('addressLine').value;
    this.trainingContactVo.addressVo.city = this.tcForm.get('city').value;
    this.trainingContactVo.addressVo.postalCode = this.tcForm.get('zip').value;
    this.trainingContactVo.addressVo.countrySubdivisionVo = <CountryCodeSubdivisionVo>{};
    this.trainingContactVo.addressVo.countrySubdivisionVo.id = this.ddState.selectedValue.id;
    this.trainingContactVo.phone = this.tcForm.get('phone').value;
    this.trainingContactVo.email = this.tcForm.get('email').value;
    
    const trainingContactData = <TrainingContactData> {
      trainingContactVo: this.trainingContactVo
    };

    this.trainingSpecialistSettingsService.saveTrainingContact(trainingContactData)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if (  data['courseOfActionVo']['coaName'] === 'SAVE_TRAINING_CONTACT') {
          this.trainingContactVo = data['resultObject'] as TrainingContactVo;
           
          // update grids and vos
          this.getTrainingContactGrids();

          if(this.actionMode === 'add') {
            this.addTrainingContact();
          }          
        }
    });  
  }

  cancelTrainingContact() {
    if ( this.trainingContactVo.incidentResourceId > 0 ) {
      this.initTrainingContactVo();
      this.trainingContactVo = Object.assign({}, this.gridSelectedTrainingContactVo);
      this.populateTrainingContactForm();  
    } else {
      this.addTrainingContact();   
    }
  }

  addTrainingContact() {
    if ( this.grdAllResources ) {
      this.grdAllResources.clearSelected();
    }

    if ( this.grdTrainingSpecialists ) {
      this.grdTrainingSpecialists.clearSelected();
    }
    
    this.initTrainingContactVo();
  }

  initTrainingContactVo() {
    this.trainingContactVo = <TrainingContactVo> {
      id: 0
      , phone: ''
      , email: ''
      , addressVo: <AddressVo>{
        id: 0
        , addressLine1: ''
        , addressLine2: ''
        , city: ''
        , countrySubdivisionVo: <CountryCodeSubdivisionVo>{
          id: 0
        }
        , postalCode: ''
      }
      , incidentResourceId: 0
      , requestNumber: ''
      , itemCode: ''
      , itemDescription: ''
      , status: ''
      , active: true
      , unitId: ''
      , unitDescription: ''
    };

    this.populateTrainingContactForm();
  }

  deleteTrainingContact() {
    if (this.trainingContactVo.id < 1) {
      this.showPrompt('Training Contact',
            'Please select a Training Contact to delete from the lower grid.',
            'OK',
            '');
    } else {
      this.promptMode = 'PROMPT_DELETE_TRAINING_CONTACT'
      this.showPrompt('Confirm Delete'
      , 'Do you really want to remove the Training Contact?'
      , 'Yes'
      , 'No');
    }
  }

  proceedWithDeleteTrainingContact() {
    this.trainingSpecialistSettingsService.deleteTrainingContact(this.trainingContactVo.id, this.trainingContactVo.incidentResourceId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if (data['courseOfActionVo']['coaName'] === 'DELETE_TRAINING_CONTACT') {
        
        // add to all resources grid and resTrainingContactVos
        this.getTrainingContactGrids();
        this.addTrainingContact();
      }
    });
  }

  //Priority Program Tab
  getPriorityPrograms() {
    this.trainingSpecialistSettingsService.getPriorityProgramGrid(0,  this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : this.currentSelectedIncidentSelectorVo.parentGroupId)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_PRIORITY_PROGRAM_GRID') {
          this.priorityProgramVos = data['recordset'] as PriorityProgramVo[];
        }
      });
  }

  onSelectPriorityProgram(row: PriorityProgramVo) {
    if (row !== undefined) {
      this.gridSelectedPriorityProgramVo = Object.assign({}, row);
      this.priorityProgramVo = Object.assign({}, row);
      this.populatePPForm();
    }
  }

  populatePPForm() {
    this.ppForm.setValue({
      priorityProgramCode: this.priorityProgramVo.code
    });
  } 

  addPriorityProgram() {
    if (this.grdPriority !== undefined) {
      this.grdPriority.clearSelected();
    }
    
    this.initPriorityProgramVo();
  }

  savePriorityProgram(resubmit: boolean) {
    if (resubmit === false ) {
      this.dialogueVo = <DialogueVo>{};
    } 

    this.priorityProgramVo.code = this.ppForm.get('priorityProgramCode').value;
    this.priorityProgramVo.incidentGroupId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId: this.currentSelectedIncidentSelectorVo.parentGroupId;
    this.priorityProgramVo.incidentId = 0;

    const priorityProgramData = <PriorityProgramData> {
      priorityProgramVo: this.priorityProgramVo,
      dialogueVo: this.dialogueVo
    };

    this.trainingSpecialistSettingsService.savePriorityProgram(priorityProgramData, 0, this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId: this.currentSelectedIncidentSelectorVo.parentGroupId)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'EDIT_PRIORITY_PROGRAM' ) {
          this.dialogueVo = data as DialogueVo;
        }

        if ( data['courseOfActionVo']['coaName'] === 'SAVE_PRIORITY_PROGRAM') {
          this.priorityProgramVo = data['resultObject'] as PriorityProgramVo;

          // update grid
          this.grdPriority.updateRowById(this.priorityProgramVo);

          // update vos
          const idx = this.priorityProgramVos.findIndex(row => row.id === this.priorityProgramVo.id);
          if ( idx > -1 ) {
            this.priorityProgramVos[idx] = Object.assign({}, this.priorityProgramVo);
          } else {
            this.priorityProgramVos.push(this.priorityProgramVo);
          }

          this.addPriorityProgram();
        }
    });
  }

  editPriorityProgramPromptHandler(data) {
    this.promptMode = 'PROMPT_EDIT_PRIORITY_PROGRAM';
    this.showPrompt('Priority Program'
                  , data['courseOfActionVo']['promptVo']['messageProperty']
                  , 'Yes'
                  , 'No');

  }

  deletePriorityProgram() {
    if (this.priorityProgramVo.id < 1) {
      this.showPrompt('Priority Program',
            'Please select a Priority Program to delete.',
            'OK',
            '');
    } else {
      this.promptMode = 'PROMPT_DELETE_PRIORITY_PROGRAM'
      this.showPrompt('Confirm Delete'
      , 'Do you really want to remove the Priority Program?'
      , 'Yes'
      , 'No');
    }
  }

  proceedWithDeletePriorityProgram() {
    this.trainingSpecialistSettingsService.deletePriorityProgram(this.priorityProgramVo.id, 0, this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId: this.currentSelectedIncidentSelectorVo.parentGroupId, this.priorityProgramVo.code)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if (data['courseOfActionVo']['coaName'] === 'DELETE_PRIORITY_PROGRAM') {
        // remove selected rows
        this.grdPriority.removeSelectedRows();

        // remove from priorityProgramVos
        const idx = this.priorityProgramVos.findIndex(row => row.id === this.priorityProgramVo.id);
        if ( idx > -1 ) {
          this.priorityProgramVos.splice(idx, 1);
        }

        this.addPriorityProgram();
      }
    });
  }

  initPriorityProgramVo() {
    this.priorityProgramVo = <PriorityProgramVo> {
      id: 0
      , code: ''
      , description: null
      , incidentId: 0
      , incidentGroupId: this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId: this.currentSelectedIncidentSelectorVo.parentGroupId
      , incidentVo: <IncidentVo> {
        id: 0
      }
    };

    this.populatePPForm();
  }

  cancelPriorityProgram() {
    if ( this.priorityProgramVo.id > 0 ) {
      this.priorityProgramVo = Object.assign({}, this.gridSelectedPriorityProgramVo);
      this.populatePPForm();
    } else {
      this.addPriorityProgram();
    }
  }
}
