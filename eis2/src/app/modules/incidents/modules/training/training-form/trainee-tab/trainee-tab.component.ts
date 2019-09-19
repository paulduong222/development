import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { DropdownData } from 'src/app/data/dropdowndata';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { ResourceTrainingVo } from 'src/app/data/resource-training-vo';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { TrainingSpecialistService } from 'src/app/service/training-specialist.service';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentResourceVo } from 'src/app/data/incident-resource-vo';
import { ResourceTrainingData } from 'src/app/data/rest/resource-training-data';
import { RscTrainingObjectiveVo } from 'src/app/data/rsc-training-objective-vo';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { PriorityProgramVo } from 'src/app/data/priority-program-vo';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { KindVo } from 'src/app/data/kind-vo';
import { HomeUnitContactComponent } from './home-unit-contact/home-unit-contact.component';

@Component({
  selector: 'app-trainee-tab',
  templateUrl: './trainee-tab.component.html',
  styleUrls: ['./trainee-tab.component.css']
})
export class TraineeTabComponent implements OnInit {

  @Output() deleteResourceTrainingEvent = new EventEmitter();
  @Output() cancelResourceTrainingEvent = new EventEmitter();
  @Output() saveResourceTrainingEvent = new EventEmitter();
  @Output() addResourceTrainingEvent = new EventEmitter();
  @ViewChild('homeUnitContact') homeUnitContact: HomeUnitContactComponent;
  @ViewChild('ddPriorityProgram') ddPriorityProgram: EisDropdownComponent;
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  @ViewChild('promptModalTrainee') promptModalTrainee: PromptModalComponent;
  
  resourceTrainingVo: ResourceTrainingVo;
  traineeForm: FormGroup;
  priorityProgramDropdownData: DropdownData[] = [];
  selectedPriorityProgramDropdownData: DropdownData;
  obj1: RscTrainingObjectiveVo;
  obj2: RscTrainingObjectiveVo;
  obj3: RscTrainingObjectiveVo;
  currentEventName = '';
  
  constructor(public incidentSelectorService: IncidentSelectorService,
              private notificationService: NotificationService,
              private trainingSpecialistService: TrainingSpecialistService,
              private formBuilder: FormBuilder) { 
  }

  ngOnInit() {
    this.initTraineeForm();
    this.addTraineeAssignment();
  }

  initTraineeForm() {
    this.traineeForm = this.formBuilder.group({
      initialAssignment: false,
      startDate: new FormControl(''),
      endDate: new FormControl(''),
      validCard: new FormControl(false),
      priorityProgram: new FormControl(false),
      priorityProgramVo: new FormControl({}),
      taskbook: new FormControl(''),
      obj1: new FormControl(''),
      obj2: new FormControl(''),
      obj3: new FormControl('')
    });
  }

  populateTraineeForm() {
    this.selectedPriorityProgramDropdownData = this.ddPriorityProgram.getDropdownDataObjectById(-2);

    for (let rscTrainingObjectiveVo of this.resourceTrainingVo.rscTrainingObjectiveVos) {
      if(rscTrainingObjectiveVo.positionNum === 1) {
        this.obj1 = rscTrainingObjectiveVo;
      }
      if(rscTrainingObjectiveVo.positionNum === 2) {
        this.obj2 = rscTrainingObjectiveVo;
      }
      if(rscTrainingObjectiveVo.positionNum === 3) {
        this.obj3 = rscTrainingObjectiveVo;
      }
    }

    if(this.traineeForm != undefined) {
      this.traineeForm.setValue({
        initialAssignment: this.resourceTrainingVo.initialAssignment,
        startDate: this.resourceTrainingVo.startDateTransferVo.dateString,
        endDate: this.resourceTrainingVo.endDateTransferVo.dateString,
        validCard: this.resourceTrainingVo.validRedCard,
        priorityProgram: this.resourceTrainingVo.fsPriorityTrainee,
        priorityProgramVo: {},
        taskbook: (this.resourceTrainingVo.incidentTaskBook === true ? 'incident' : 'homeUnit'),
        obj1: this.obj1.objective,
        obj2: this.obj2.objective,
        obj3: this.obj3.objective
      });
    }
    
    this.ddPriorityProgram.dropdownDisabled = this.resourceTrainingVo.fsPriorityTrainee ? false : true;

    setTimeout(() => {
      if(this.resourceTrainingVo.priorityProgramVo) {
        this.selectedPriorityProgramDropdownData = this.ddPriorityProgram.getDropdownDataObjectById(this.resourceTrainingVo.priorityProgramVo.id);
      }
    });
  }

  initResourceTrainingVo() {
    this.resourceTrainingVo = <ResourceTrainingVo> {
      id: 0,
      kindVo: <KindVo> {id: 0},
      incidentResourceVo: <IncidentResourceVo > {id:0},
      initialAssignment: false,
      startDateTransferVo: <DateTransferVo> {dateString: '', timeString: ''},
      endDateTransferVo: <DateTransferVo> {dateString: '', timeString: ''},
      fsPriorityTrainee: false,
      validRedCard: false,
      objectiveIssuer: '',
      // rscTrainingTrainerVos: [] as RscTrainingTrainerVo[],
      rscTrainingObjectiveVos: [] as RscTrainingObjectiveVo[],
      ptbPercentage: 0,
      numberOfAcres: 0,
      tnspComments: '',
      incidentTaskBook: false,
      //complexityVo: <DropdownData> {},
      priorityProgramVo: <PriorityProgramVo> {},
      //recommendationVo: <DropdownData> {},
      //fuelTypeVos: [] as DropdownData[],
      //lengthAssignment: ''
    };

    this.obj1 = <RscTrainingObjectiveVo> {
      id: 0, objective: ''
    }

    this.obj2 = <RscTrainingObjectiveVo> {
      id: 0, objective: ''
    }

    this.obj3 = <RscTrainingObjectiveVo> {
      id: 0, objective: ''
    }

    this.populateTraineeForm();
  }

  saveTraineeAssignment() {

    this.resourceTrainingVo.initialAssignment = this.traineeForm.get('initialAssignment').value;
    this.resourceTrainingVo.startDateTransferVo.dateString = this.dtStart.getFormattedDate();
    this.resourceTrainingVo.endDateTransferVo.dateString = this.dtEnd.getFormattedDate();
    this.resourceTrainingVo.validRedCard = this.traineeForm.get('validCard').value;
    this.resourceTrainingVo.fsPriorityTrainee = this.traineeForm.get('priorityProgram').value;
    if(this.ddPriorityProgram.selectedValue.id > 0) {
      this.resourceTrainingVo.priorityProgramVo = <PriorityProgramVo> {id: this.ddPriorityProgram.selectedValue.id}  
    } else {
      this.resourceTrainingVo.priorityProgramVo = null;
    }
  
    this.resourceTrainingVo.incidentTaskBook = this.traineeForm.get('taskbook').value === 'incident' ? true : false;

    this.obj1.objective = this.traineeForm.get('obj1').value;
    this.obj1.positionNum = 1;
    this.obj2.objective = this.traineeForm.get('obj2').value;
    this.obj2.positionNum = 2;
    this.obj3.objective = this.traineeForm.get('obj3').value;
    this.obj3.positionNum = 3;

    let rscTrainingObjectiveVos: RscTrainingObjectiveVo[] = [];
    rscTrainingObjectiveVos.push(this.obj1);
    rscTrainingObjectiveVos.push(this.obj2);
    rscTrainingObjectiveVos.push(this.obj3);

    this.resourceTrainingVo.rscTrainingObjectiveVos = rscTrainingObjectiveVos;

    const resourceTrainingData = <ResourceTrainingData> {
      resourceTrainingVo: this.resourceTrainingVo,
      dialogueVo: null
    }

    this.trainingSpecialistService.saveResourceTraining(resourceTrainingData)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_RESOURCE_TRAINING') {
          this.resourceTrainingVo = data['resultObject'] as ResourceTrainingVo;
          this.saveResourceTrainingEvent.emit(this.resourceTrainingVo);
        }
      });   
  }

  cancelTraineeAssignment() {
    this.cancelResourceTrainingEvent.emit(this.resourceTrainingVo);
  }

  addTraineeAssignment() {
    this.initResourceTrainingVo();
  }

  addResourceTrainingAssigment() {
    this.addResourceTrainingEvent.emit();
  }

  deleteTraineeAssignment() {
    if (this.resourceTrainingVo.id > 0) {
      this.currentEventName = 'PROMPT_DELETE_TRAINEE_ASSIGNMENT';
      this.showPrompt('Confirm Delete'
            , 'Do you really want to remove the Trainee Assignment?'
            , 'Yes'
            , 'No');
    } else {
      this.showPrompt('Training Specialist'
            , 'Please select a Trainee Assignment to delete.'
            , 'OK'
            , '');
    }
  }

  showPrompt(title, msg, btn1, btn2) {
    this.promptModalTrainee.reset();
    this.promptModalTrainee.promptTitle = title;
    this.promptModalTrainee.promptMessage1 = msg;
    this.promptModalTrainee.button1Label = btn1;
    this.promptModalTrainee.button2Label = btn2;
    this.promptModalTrainee.openModal();
  }

  promptModalActionResult(event) {
    this.promptModalTrainee.closeModal();
    if ( this.currentEventName === 'PROMPT_DELETE_TRAINEE_ASSIGNMENT' && event === 'Yes') {
      this.proceedWithDelete();
    }  
  }

  getStyle() {
    return ( this.traineeForm.get('priorityProgram').value ? 'ast' : 'hidden' );
  }

  isPriorityProgramDisabled() {
    return ( this.traineeForm.get('priorityProgram').value ? false : true );
  }

  proceedWithDelete() {
    this.trainingSpecialistService.deleteResourceTraining(this.resourceTrainingVo.id, this.resourceTrainingVo.incidentResourceVo.id)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        if (data['courseOfActionVo']['coaName'] === 'DELETE_RESOURCE_TRAINING') {
          this.deleteResourceTrainingEvent.emit();
        }
      });
  }

}
