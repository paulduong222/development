import { Component, OnInit, ViewChild } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { IncidentResourceService } from 'src/app/service/incident-resource.service';
import { TrainingResourceGridComponent } from 'src/app/modules/incidents/modules/training/training-resource-grid/training-resource-grid.component';
import { AssignmentCloseoutModalComponent } from '../modals/assignment-closeout-modal/assignment-closeout-modal.component';
import { IncidentResourceGridVo } from 'src/app/data/incident-resource-grid-vo';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingFormComponent } from 'src/app/modules/incidents/modules/training/training-form/training-form.component';
import { HeightCalc } from '../../../../../height-calc';
import { IncidentResourceVo } from 'src/app/data/incident-resource-vo';
import { TrainingSpecialistService } from 'src/app/service/training-specialist.service';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { TrainingSpecialistSettingsService } from 'src/app/service/training-specialist-settings.service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { TrainingSettingsVo } from 'src/app/data/training-settings-vo';
import { KindVo } from 'src/app/data/kind-vo';
import { ResourceTrainingVo } from 'src/app/data/resource-training-vo';
import { TrainingReportsComponent } from '../training-reports/training-reports.component';

@Component({
  selector: 'app-training-view',
  templateUrl: './training-view.component.html',
  styleUrls: ['./training-view.component.css']
})
export class TrainingViewComponent implements OnInit {
  @ViewChild('trainingResourceGrid') trainingResourceGrid: TrainingResourceGridComponent;
  @ViewChild('assignmentCloseoutModal') assignmentCloseoutModal: AssignmentCloseoutModalComponent;
  @ViewChild('trainingReports') trainingReports: TrainingReportsComponent;
  @ViewChild('trainingForm') trainingForm: TrainingFormComponent;
  @ViewChild('resGrid') resGrid: EisGridComponent;
  incidentResourceGridVos = [];
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  selectedIncidentResourceGridVo: IncidentResourceGridVo;
  splitAreaLeftSize = 30;
  splitAreaRightSize = 70;
  rColumnDefs = [];
  
  constructor(public incidentSelectorService: IncidentSelectorService,
              private incidentResourceService: IncidentResourceService,
              private notificationService: NotificationService,
              public referenceDataService: ReferenceDataService,
              private trainingSpecialistService: TrainingSpecialistService,
              private trainingSpecialistSettingsService: TrainingSpecialistSettingsService) { }

  ngOnInit() {
    this.currentSelectedIncidentSelectorVo = this.incidentSelectorService.selectedGridRow;
    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
          this.trainingReports.incidentId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ? this.currentSelectedIncidentSelectorVo.incidentId : 0;
          this.trainingReports.incidentGroupId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0;
          this.trainingForm.evaluatorTab.evaluatorModal.incidentId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ? this.currentSelectedIncidentSelectorVo.incidentId : 0;
          this.trainingForm.evaluatorTab.evaluatorModal.incidentGroupId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0;
    });

    this.rColumnDefs = [
      {headerName: 'Request #', field: 'requestNumber', width: 100},
      {headerName: 'Resource Name', field: 'resourceName', width: 140},
      {headerName: 'Item Code', field: 'itemCode', width: 100},
      {headerName: 'Item Desc', field: 'itemName', width: 200},
      {headerName: 'Agency', field: 'agency', filter: true, width: 100},
      {headerName: 'Status', field: 'assignmentStatus', filter: true, width: 100},
      {headerName: 'Unit ID', field: 'unitId', filter: true, width: 100},
      {headerName: 'Check-In Date', field: 'ciCheckInDateVo.dateString', filter: true, width: 120},
    ];

    this.resGrid.getRowNodeId = function( row ) {
      return row.incidentResourceId;
    };

    this.refreshGrid();
    this.getTraineeTotal();
    this.getPriorityTrainees();
    this.getPriorityPrograms();
    this.getTrainingSettings();
    this.trainingForm.evaluatorTab.evaluatorModal.incidentId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ? this.currentSelectedIncidentSelectorVo.incidentId : 0;
    this.trainingForm.evaluatorTab.evaluatorModal.incidentGroupId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0;
    this.trainingReports.incidentId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ? this.currentSelectedIncidentSelectorVo.incidentId : 0;
    this.trainingReports.incidentGroupId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0;

    setTimeout(() => {
      this.loadReferenceData();
    });
  }

  ngOnDestroy() {
    this.incidentSelectorSubscription.unsubscribe();
  }

  calcHt() {
    return HeightCalc.calculateHeight('res');
  }

  /*
   * Refresh the grid and reset variables
  */
  refreshGrid() {
    this.incidentResourceGridVos = [];

    this.incidentResourceService
    .getIncidentResourceGrid(this.currentSelectedIncidentSelectorVo.incidentId, this.currentSelectedIncidentSelectorVo.incidentGroupId)
    //.getIncidentResourceGrid(10000, 0)
    .subscribe(data => {
      this.notificationService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_RESOURCE_GRID'
        && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.incidentResourceGridVos = data['recordset'] as IncidentResourceGridVo[];
      }
    });
    this.trainingForm.incidentResourceGridVo = null;
    this.trainingForm.populateForm();
  }

  customizeColumnsEvent() {
  }

  clearFilter() {
    this.resGrid.clearFilters();
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

  getPriorityTrainees() {
    let incidentId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ? this.currentSelectedIncidentSelectorVo.incidentId : 0;
    let incidentGroupId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0;

    this.trainingSpecialistService
    .getPriorityTrainees(incidentId, incidentGroupId)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_PRIORITY_TRAINEES') {
            this.trainingForm.priorityTrainees = data['resultObject'];
        }
      });
  }
  
  getTraineeTotal() {
    let incidentId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ? this.currentSelectedIncidentSelectorVo.incidentId : 0;
    let incidentGroupId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0;

    this.trainingSpecialistService
    .getTraineeTotal(incidentId, incidentGroupId)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_TRAINEE_TOTAL') {
            this.trainingForm.traineeTotal = data['resultObject'];
        }
      });  
  }

  getPriorityPrograms() {
    let incidentId = 0;
    let incidentGroupId = 0;

    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT'
          && this.currentSelectedIncidentSelectorVo.parentGroupId !== null) {
       incidentGroupId = this.currentSelectedIncidentSelectorVo.parentGroupId;
    }
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
       incidentGroupId = this.currentSelectedIncidentSelectorVo.incidentGroupId;
    }

    this.trainingSpecialistSettingsService.getPriorityProgramGrid(incidentId,  incidentGroupId)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_PRIORITY_PROGRAM_GRID') {
          this.trainingForm.traineeTab.priorityProgramDropdownData = data['recordset'] as DropdownData[];
        }
      });
  }

  loadReferenceData() {
    this.trainingForm.traineeTab.showPrompt('Processing Request', 'Loading Data...', '', '');

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
      //.getResourceReferenceData(10000, 0)
      .subscribe(data => {
          this.notificationService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'GET_RESOURCE_REF_DATA'
                && data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
                  this.incidentSelectorService.tnspKindTypeData = data['resultObject']['kindDropdownData'];
                  this.incidentSelectorService.tnspUnitTypeData = data['resultObject']['unitDropdownData'];
                  this.trainingForm.kindVos = data['resultObject']['kindVos'] as KindVo[];
                  this.trainingForm.traineeTab.promptModalTrainee.closeModal();
          }
      });
  }

  getTrainingSettings() {
    let incidentId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ? this.currentSelectedIncidentSelectorVo.incidentId : 0;
    let incidentGroupId = this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0;

    this.trainingSpecialistSettingsService
      .getTrainingSettings(incidentId, incidentGroupId)
      .subscribe(data => {
          this.notificationService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'GET_TRAINING_SETTINGS'
                && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
                  this.trainingForm.trainingSettingsVos = data['resultObject'] as TrainingSettingsVo[];
                  this.trainingForm.fuelTypeVos = data['resultObjectAlternate'] as DropdownData[];
          }
      });
  }


  onSelectGridRow(row: any) {
    if ( row !== undefined ) {
      this.selectedIncidentResourceGridVo = Object.assign({}, row);
      this.trainingForm.incidentResourceGridVo = this.selectedIncidentResourceGridVo;
      this.getIncidentResourceById();
      this.trainingReports.resourceTrainingId = 0;
    }
  }

  getIncidentResourceById() {
    this.incidentResourceService
      .getIncidentResourceById(this.selectedIncidentResourceGridVo.incidentResourceId)
      .subscribe(data => {
          this.notificationService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_RESOURCE_BY_ID'
                && data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
                  let incidentResourceVo: IncidentResourceVo = data['resultObject'] as IncidentResourceVo;
                  this.trainingForm.qualsModal.otherQuals = incidentResourceVo.resourceVo.otherQuals;
                  //this.trainingForm.otherQuals = incidentResourceVo.resourceVo.otherQuals;
                  this.trainingForm.addTrainingAssignment();
          }
      });
  }

  openAssignmentCloseoutModal() {
    if ( this.trainingForm.resourceTrainingVo.id > 0) {
      this.assignmentCloseoutModal.openModal('assignment-closeout-modal');
      this.assignmentCloseoutModal.resourceTrainingVo = this.trainingForm.resourceTrainingVo;
      this.assignmentCloseoutModal.trainingSettingsVo = this.trainingForm.trainingSettingsVos[0];
      this.assignmentCloseoutModal.fuelTypeVos = this.trainingForm.fuelTypeVos;
      this.assignmentCloseoutModal.requestNumber = this.selectedIncidentResourceGridVo.requestNumber;
      this.assignmentCloseoutModal.resourceName = this.selectedIncidentResourceGridVo.resourceName;
      this.assignmentCloseoutModal.itemCodeDesc = this.selectedIncidentResourceGridVo.itemName;
      this.assignmentCloseoutModal.populateForm();
    }else{
      this.trainingForm.traineeTab.showPrompt('Training Specialist'
          , 'Please select a saved Trainee Assignment before selecting Assignment Closeout.'
          , 'OK'
          , '');
    }
  }

  closeAssignmentCloseoutModalEvent() {
    //console.log('Assignment Closeout close event callback');
  }

  trainingAssignmentSelectedEvent(vo: ResourceTrainingVo) {
    this.trainingReports.resourceTrainingId = vo.id;
    this.trainingReports.rscTrainingTrainerVos = vo.rscTrainingTrainerVos;
  }

  promptEventTrainingReports() {
    this.trainingForm.traineeTab.showPrompt('Training Specialist'
          , 'Please select a Trainee Assignment before printing a form.'
          , 'OK'
          , '');
  }

}
