import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingSpecialistService } from 'src/app/service/training-specialist.service';
import { ResourceTrainingVo } from 'src/app/data/resource-training-vo';
import { KindVo } from 'src/app/data/kind-vo';
import { IncidentResourceGridVo } from 'src/app/data/incident-resource-grid-vo';
import { DropdownData } from 'src/app/data/dropdowndata';
import { TrainingSpecialistSettingsService } from 'src/app/service/training-specialist-settings.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { TrainingSettingsVo } from 'src/app/data/training-settings-vo';
import { TraineeTabComponent } from './trainee-tab/trainee-tab.component';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { EvaluatorTabComponent } from './evaluator-tab/evaluator-tab.component';
import { QualsModalComponent } from '../modals/quals-modal/quals-modal.component';

@Component({
  selector: 'app-training-form',
  templateUrl: './training-form.component.html',
  styleUrls: ['./training-form.component.css']
})
export class TrainingFormComponent implements OnInit {
  @Output() trainingAssignmentSelectedEvent = new EventEmitter();
  @ViewChild('evaluatorTab') evaluatorTab: EvaluatorTabComponent;
  @ViewChild('traineeTab') traineeTab: TraineeTabComponent;
  @ViewChild('ddKind') ddKind: EisDropdownComponent;
  @ViewChild('qualsModal') qualsModal: QualsModalComponent;

  incidentResourceGridVo: IncidentResourceGridVo;
  kindVos: KindVo[] = [];
  requestNumberLabel: string = '';
  resourceNameLabel: string = '';
  traineeTotal:Object = '';
  priorityTrainees:Object = '';
  itemCodeDescLabel: string = '';
  trainee: boolean = false;
  functionalLabel: string = '';
  selectedTab: string = 'trainee';
  resourceTrainingVos: ResourceTrainingVo[] = [];
  resourceTrainingVo: ResourceTrainingVo;
  selectedKindCode: string = '';
  trainingSettingsVos: TrainingSettingsVo [];
  fuelTypeVos: DropdownData[];
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  selectedKindDropdownData: DropdownData;
  
  @ViewChild('traineeAssignGrid') traineeAssignGrid: EisGridComponent;
  aGridColumnDefs = [
    {headerName: 'Trainee Assignment', field: 'kindVo.code', width: 152},
    {headerName: 'Description', field: 'kindVo.description', width: 240},
  ];

  constructor(public incidentSelectorService: IncidentSelectorService,
              private notifyService: NotificationService,
              private trainingSpecialistService: TrainingSpecialistService,
              private trainingSpecialistSettingsService:TrainingSpecialistSettingsService) { }

  ngOnInit() {
     this.initResourceTrainingVo();
  }

  addTrainingAssignment() {
    this.populateForm();
  }

  initResourceTrainingVo() {
    this.resourceTrainingVo = <ResourceTrainingVo> {
      id: 0,
      kindVo: <KindVo> {
        id: 0
      }
    }
  }

  dataTabSelect(tabname: string) {
    this.selectedTab = tabname;
  }

  getTabClass(tabname) {
    return (this.selectedTab === tabname ? '' : 'hidden');
  }

  getStyle(menuName) {
    return ( this.selectedTab === menuName ? 'btn-selected' : '' );
  }

  populateForm() {

    if (this.incidentResourceGridVo === undefined || this.incidentResourceGridVo === null) {
      //add
      this.requestNumberLabel = '';
      this.resourceNameLabel = '';
      this.itemCodeDescLabel = '';
      this.trainee = false;
      this.functionalLabel = '';
      this.selectedKindDropdownData = this.ddKind.getDropdownDataObjectById(-2);
      this.resourceTrainingVos = [];
      this.qualsModal.otherQuals = [];
      //this.otherQuals = [];
      this.traineeTab.addTraineeAssignment();
      this.traineeTab.homeUnitContact.addHomeUnitContact();
      //this.traineeTab.addHomeUnitContact();
      this.evaluatorTab.initResourceTrainingVo();
    } else {
      //edit
      this.requestNumberLabel = this.incidentResourceGridVo.requestNumber;
      this.resourceNameLabel = this.incidentResourceGridVo.resourceName;
      this.itemCodeDescLabel = this.incidentResourceGridVo.itemName;
      this.trainee = this.incidentResourceGridVo.trainee;
      this.setFunctionalLabelByCode(this.incidentResourceGridVo.itemCode);

      this.selectedKindDropdownData = this.ddKind.getDropdownDataObjectByCode(this.incidentResourceGridVo.itemCode); 

      this.getTraineeAssignmentGrid();
      this.traineeTab.homeUnitContact.incidentResourceId = this.incidentResourceGridVo.incidentResourceId;
      this.traineeTab.homeUnitContact.getHomeUnitContact();
      this.traineeTab.addTraineeAssignment();
      this.traineeTab.resourceTrainingVo.incidentResourceVo.id = this.incidentResourceGridVo.incidentResourceId;

      setTimeout(() => {
        this.traineeTab.resourceTrainingVo.kindVo.id = this.selectedKindDropdownData.id;
      });   
    }

    this.evaluatorTab.initResourceTrainingVo();
  }

  getTraineeAssignmentGrid() {
    this.traineeTab.showPrompt('Processing Request', 'Loading Data...', '', '');
    this.resourceTrainingVos = [];

    this.trainingSpecialistService
    .getResourceTraining(this.incidentResourceGridVo.incidentResourceId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_RESOURCE_TRAININGS'
        && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.resourceTrainingVos = data['recordset'] as ResourceTrainingVo[];
          this.traineeTab.promptModalTrainee.closeModal();
      }
    });
  }

  onSelectTrainingAssign(row: any) {
    if ( row != undefined ) {
      this.resourceTrainingVo = Object.assign({}, row);
      this.selectedKindCode = this.resourceTrainingVo.kindVo.code;
      this.traineeTab.resourceTrainingVo = this.resourceTrainingVo;
      this.traineeTab.populateTraineeForm();
      this.evaluatorTab.resourceTrainingVo = this.resourceTrainingVo;
      this.trainingAssignmentSelectedEvent.emit(this.resourceTrainingVo);
    }
  }

  kindSelectEvent(event) {
    this.traineeTab.resourceTrainingVo.kindVo.id = event.id;
    if (this.incidentResourceGridVo != null) {
      this.traineeTab.resourceTrainingVo.incidentResourceVo.id = this.incidentResourceGridVo.incidentResourceId;
    }

    this.setFunctionalLabelByCode(event.code);  
  }

  setFunctionalLabelByCode(kindVoCode) {
    if ( kindVoCode !== '') {
      const idx = this.kindVos.findIndex(f => f.code === kindVoCode);
      if ( idx > -1 ) {
        this.functionalLabel = this.kindVos[idx].sectionCodeVo.description;
      } else {
        this.functionalLabel = '';
      }
    } else {
      this.functionalLabel = '';
    } 
  }

  deleteResourceTrainingEvent() {
    // remove selected rows
    this.traineeAssignGrid.removeSelectedRows();

    const idx = this.resourceTrainingVos.findIndex(row => row.id === this.resourceTrainingVo.id);
    if ( idx > -1 ) {
      this.resourceTrainingVos.splice(idx, 1);
    }

    this.initResourceTrainingVo();
    this.traineeTab.addTraineeAssignment();
    this.evaluatorTab.initResourceTrainingVo();
  }

  saveResourceTrainingEvent(resourceTrainingVo: ResourceTrainingVo) {

    this.traineeAssignGrid.updateRowById(resourceTrainingVo);

    if (this.traineeAssignGrid.gridOptions.api.getSelectedRows().length < 1) {
      this.initResourceTrainingVo();
      this.traineeTab.addTraineeAssignment();
      this.evaluatorTab.initResourceTrainingVo();
    }
  }

  addResourceTrainingEvent() {
    this.selectedKindDropdownData = this.ddKind.getDropdownDataObjectById(-2);
    this.traineeAssignGrid.clearSelected();
    this.initResourceTrainingVo();
    this.traineeTab.addTraineeAssignment();
    this.evaluatorTab.initResourceTrainingVo();
  }

  cancelResourceTrainingEvent(resourceTrainingVo: ResourceTrainingVo) {
    
    this.selectedKindDropdownData = this.ddKind.getDropdownDataObjectById(-2);

    setTimeout(() => {
      this.selectedKindDropdownData = this.ddKind.getDropdownDataObjectByCode(this.incidentResourceGridVo.itemCode);
    });
 
    this.traineeTab.populateTraineeForm();   
  }

  openQualsModal() {
    this.qualsModal.openModal('quals-modal');
  }

}
