import { Component, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { RscTrainingTrainerVo } from 'src/app/data/rsc-training-trainer-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { AddressVo } from 'src/app/data/address-vo';
import { KindVo } from 'src/app/data/kind-vo';
import { OrganizationVo } from 'src/app/data/organization-vo';
import { TrainingSpecialistService } from 'src/app/service/training-specialist.service';
import { NotificationService } from 'src/app/service/notification-service';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { RscTrainingTrainerData } from 'src/app/data/rest/rsc-training-trainer-data';
import { ResourceTrainingVo } from 'src/app/data/resource-training-vo';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';


@Component({
  selector: 'app-evaluator-modal',
  templateUrl: './evaluator-modal.component.html',
  styleUrls: ['./evaluator-modal.component.css']
})
export class EvaluatorModalComponent implements OnInit {
  @Output() saveEvaluatorEvent = new EventEmitter();
  @ViewChild('grdEvaluators') grdEvaluators: EisGridComponent;
  @ViewChild('ddUnitCode') ddUnitCode: EisDropdownComponent;
  @ViewChild('ddKind') ddKind: EisDropdownComponent;
  @ViewChild('ddState') ddState: EisDropdownComponent;
  @ViewChild('promptModalEvaluator') promptModalEvaluator: PromptModalComponent;
  
  evaluatorForm: FormGroup;
  rscTrainingTrainerVos: RscTrainingTrainerVo [];
  rscTrainingTrainerVo: RscTrainingTrainerVo;
  selectedUnitDropdownData: DropdownData;
  selectedKindDropdownData: DropdownData;
  selectedStateDropdownData: DropdownData;
  resourceTrainingId: number;
  incidentId: number = 0;
  incidentGroupId: number = 0;
  
  evaluatorGridColumnDefs = [
    {headerName: 'Request Number', field: 'requestNumber', width: 140},
    {headerName: 'Resource Name', field: 'resourceName', width: 276},
    {headerName: 'Item Code', field: 'kindVo.code', width: 100},
    {headerName: 'Status', field: 'status', width: 80},
  ];

  constructor(public incidentSelectorService: IncidentSelectorService,
              private notificationService: NotificationService,
              private trainingSpecialistService: TrainingSpecialistService,
              private modalService: ModalService,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.initEvaluatorForm();

    this.grdEvaluators.getRowNodeId = function( row ) {
      return row.incidentResourceVo.id;
    };
  }

  initEvaluatorForm() {
    this.evaluatorForm = this.formBuilder.group({
      resourceName: new FormControl(''),
      unitVo: new FormControl({}),
      unitDesc: new FormControl({value: '', disabled: true}),
      kindVo: new FormControl({}),
      itemDesc: new FormControl({value: '', disabled: true}),
      addressLine1: new FormControl(''),
      city: new FormControl(''),
      countryCodeSubdivisionVo: new FormControl({}),
      zip: new FormControl(''),
      phone: new FormControl(''),
      email: new FormControl('')
    });
  }

  populateEvaluatorForm(incidentEvaluator: boolean) {
    this.selectedUnitDropdownData = this.ddUnitCode.getDropdownDataObjectById(-2);
    this.selectedKindDropdownData = this.ddKind.getDropdownDataObjectById(-2);
    this.selectedStateDropdownData = this.ddState.getDropdownDataObjectById(-2);

    this.evaluatorForm.setValue({
      resourceName: this.rscTrainingTrainerVo.resourceName,
      unitVo: {},
      unitDesc: this.rscTrainingTrainerVo.unitVo != null ? this.rscTrainingTrainerVo.unitVo.name : '',
      kindVo: {},
      itemDesc: this.rscTrainingTrainerVo.kindVo != null ? this.rscTrainingTrainerVo.kindVo.description : '',
      addressLine1: this.rscTrainingTrainerVo.addressVo.addressLine1,
      city: this.rscTrainingTrainerVo.addressVo.city,
      countryCodeSubdivisionVo: {},
      zip: this.rscTrainingTrainerVo.addressVo.postalCode,
      phone: this.rscTrainingTrainerVo.phone,
      email: this.rscTrainingTrainerVo.email
    });


    setTimeout(() => {
      if (this.rscTrainingTrainerVo.unitVo) {
        this.selectedUnitDropdownData = this.ddUnitCode.getDropdownDataObjectById(this.rscTrainingTrainerVo.unitVo.id);
      }
      if (this.rscTrainingTrainerVo.kindVo) {
        this.selectedKindDropdownData = this.ddKind.getDropdownDataObjectById(this.rscTrainingTrainerVo.kindVo.id);
      }
      if (this.rscTrainingTrainerVo.addressVo) {
        if (this.rscTrainingTrainerVo.addressVo.countrySubdivisionVo) {
          this.selectedStateDropdownData = this.ddState.getDropdownDataObjectById(this.rscTrainingTrainerVo.addressVo.countrySubdivisionVo.id);
        }
      }
    }); 

    //disable fields if incident evaluator
    if (incidentEvaluator) {
      this.evaluatorForm.get('resourceName').disable();
    } else {
      this.evaluatorForm.get('resourceName').enable(); 
    }

    this.ddUnitCode.dropdownDisabled = incidentEvaluator;
    this.ddKind.dropdownDisabled = incidentEvaluator;

  }

  getIncidentEvaluatorsGrid( incidentResourceId: number ) {
    this.showMessage('Loading Data...', 'Processing Request', '');
    this.trainingSpecialistService.getIncidentEvaluatorsGrid(incidentResourceId, this.incidentId, this.incidentGroupId) 
    .subscribe(data => {
      this.notificationService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_EVALUATORS_GRID'
        && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.rscTrainingTrainerVos = data['recordset'] as RscTrainingTrainerVo[];
          this.promptModalEvaluator.closeModal();
      }
    });
  }

  showMessage(msg, title, btn1Label) {
    this.promptModalEvaluator.reset();
    this.promptModalEvaluator.promptTitle = title;
    this.promptModalEvaluator.promptMessage1 = msg;
    if ( btn1Label !== '' ) { this.promptModalEvaluator.button1Label = btn1Label; }
    this.promptModalEvaluator.openModal();
  }

  onSelectEvaluator(row: any) {
    if (row != undefined) {
      this.rscTrainingTrainerVo = Object.assign({}, row);
      
      
      if(this.rscTrainingTrainerVo.addressVo === null) {
        this.rscTrainingTrainerVo.addressVo = <AddressVo> {
          id: 0,
          addressLine1: '',
          addressLine2: '',
          city: '',
          countrySubdivisionVo: <CountryCodeSubdivisionVo>{
            id: 0,
            countrySubAbbreviation: '',
            countrySubName: ''  
          },
          postalCode: ''
        }
      };

      this.populateEvaluatorForm(true);
    }
  }

  initRscTrainingTrainerVo() {
    this.rscTrainingTrainerVo = <RscTrainingTrainerVo> {
      id: 0,
      resourceTrainingVo: <ResourceTrainingVo> {
        id: 0
      },
      addressVo: <AddressVo>{
        id: 0,
        addressLine1: '',
        addressLine2: '',
        city: '',
        countrySubdivisionVo: <CountryCodeSubdivisionVo>{
          id: 0,
          countrySubAbbreviation: '',
          countrySubName: ''
        },
        postalCode: ''
      },
      kindVo: <KindVo> {
        id: 0,
        description: ''
      },
      requestNumber: '',
      unitVo: <OrganizationVo> {
        id: 0,
        name: ''
      },
      email: '',
      phone: '',
      resourceName: '',
      status: ''
    } 
  }

  unitSelectEvent(event) {
    if ( event.desc !== '') {
      this.evaluatorForm.get('unitDesc').patchValue(event.desc);
    } else {
      this.evaluatorForm.get('unitDesc').patchValue('');
    }
  }

  kindSelectEvent(event) {
    if ( event.desc !== '') {
      this.evaluatorForm.get('itemDesc').patchValue(event.desc);
    } else {
      this.evaluatorForm.get('itemDesc').patchValue('');
    } 
  }

  openModal(id: string, incidentResourceId: number, mode: string) {
    this.modalService.open(id);
    this.getIncidentEvaluatorsGrid(incidentResourceId);
    this.grdEvaluators.clearSelected();

    let disableIncEvalFields: boolean = false;
    
    if (mode === 'add') {
      this.initRscTrainingTrainerVo();
    } else {
      if (this.rscTrainingTrainerVo.incidentResourceVo) {
        if (this.rscTrainingTrainerVo.incidentResourceVo.id > 0) {
          disableIncEvalFields = true;
        }  
      } 
    }

    this.populateEvaluatorForm(disableIncEvalFields);
  }

  addEvaluator() {
    this.grdEvaluators.clearSelected();
    this.initRscTrainingTrainerVo();
    this.populateEvaluatorForm(false);
  }

  closeModal(id: string) {
    this.modalService.close(id);
  }

  save() {
    this.rscTrainingTrainerVo.resourceName = this.evaluatorForm.get('resourceName').value;

    if (this.ddUnitCode.selectedValue.id > 0) {
      this.rscTrainingTrainerVo.unitVo = <OrganizationVo> {
        id: this.ddUnitCode.selectedValue.id
      }
    } else {
      this.rscTrainingTrainerVo.unitVo = null;
    }

    if (this.ddKind.selectedValue.id > 0) {
      this.rscTrainingTrainerVo.kindVo = <KindVo> {
        id: this.ddKind.selectedValue.id
      }
    } else {
      this.rscTrainingTrainerVo.kindVo = null;
    }
  
    this.rscTrainingTrainerVo.addressVo.addressLine1 = this.evaluatorForm.get('addressLine1').value;
    this.rscTrainingTrainerVo.addressVo.city = this.evaluatorForm.get('city').value;
    this.rscTrainingTrainerVo.addressVo.countrySubdivisionVo = <CountryCodeSubdivisionVo> {
      id: this.ddState.selectedValue.id
    }
  
    this.rscTrainingTrainerVo.addressVo.postalCode = this.evaluatorForm.get('zip').value;
    this.rscTrainingTrainerVo.phone = this.evaluatorForm.get('phone').value;
    this.rscTrainingTrainerVo.email = this.evaluatorForm.get('email').value;

    if(this.rscTrainingTrainerVo.resourceTrainingVo === undefined || this.rscTrainingTrainerVo.resourceTrainingVo === null) {
      this.rscTrainingTrainerVo.resourceTrainingVo = <ResourceTrainingVo> {
        id: 0
      }
    };

    this.rscTrainingTrainerVo.resourceTrainingVo.id = this.resourceTrainingId;

    const rscTrainingTrainerData = <RscTrainingTrainerData> {
      rscTrainingTrainerVo: this.rscTrainingTrainerVo,
      dialogueVo: null
    }

    this.trainingSpecialistService.saveEvaluator(rscTrainingTrainerData)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_TRAINER') {
          this.rscTrainingTrainerVo = data['resultObject'] as RscTrainingTrainerVo;
          this.saveEvaluatorEvent.emit(this.rscTrainingTrainerVo);
          this.addEvaluator();
        }
      }); 
  }
}
