import { Component, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { ResourceTrainingVo } from 'src/app/data/resource-training-vo';
import { NotificationService } from 'src/app/service/notification-service';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { PriorityProgramVo } from 'src/app/data/priority-program-vo';
import { TrainingSpecialistService } from 'src/app/service/training-specialist.service';
import { ResourceTrainingData } from 'src/app/data/rest/resource-training-data';
import { TrainingSpecialistSettingsService } from 'src/app/service/training-specialist-settings.service';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { TrainingSettingsData } from 'src/app/data/rest/training-settings-data';
import { TrainingSettingsVo } from 'src/app/data/training-settings-vo';
import { IncidentResourceVo } from 'src/app/data/incident-resource-vo';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-assignment-closeout-modal',
  templateUrl: './assignment-closeout-modal.component.html',
  styleUrls: ['./assignment-closeout-modal.component.css']
})
export class AssignmentCloseoutModalComponent implements OnInit {
  @Output() closeModalEvent = new EventEmitter();
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  @ViewChild('ddComplexity') ddComplexity: EisDropdownComponent;
  @ViewChild('ddRecommendation') ddRecommendation: EisDropdownComponent;
  @ViewChild('promptModalAssignmentCloseout') promptModalAssignmentCloseout: PromptModalComponent;

  assignmentCloseoutForm: FormGroup;
  resourceTrainingVo: ResourceTrainingVo;
  trainingSettingsVo: TrainingSettingsVo;
  requestNumber: string = '';
  resourceName: string = '';
  itemCodeDesc: string = '';
  complexityDropdownData: DropdownData[];
  recommendationDropdownData: DropdownData[];
  fuelTypeVos: DropdownData[];

  //selected object holders for dropdown
  selectedComplexityDropdownData: DropdownData;
  selectedRecommendationDropdownData: DropdownData;

  constructor(private modalService: ModalService,
              private notificationService: NotificationService,
              private formBuilder: FormBuilder,
              private referenceDataService: ReferenceDataService,
              private trainingSpecialistService: TrainingSpecialistService,
              private trainingSpecialistSettingsService: TrainingSpecialistSettingsService) {
  }

  ngOnInit() {
    this.initAssignmentCloseoutForm();
    this.getComplexities();
    this.getRecommendations();
    this.initResourceTrainingVo();
  }

  initAssignmentCloseoutForm() {
    this.assignmentCloseoutForm = this.formBuilder.group({
      startDate: new FormControl(''),
      endDate: new FormControl(''),
      lengthOfAssign: new FormControl(''),
      ptbProgress: new FormControl(''),
      complexityVo: new FormControl({}),
      recommendationVo: new FormControl({}),
      acres: new FormControl(''),
      isBrush: new FormControl(false),
      isGrass: new FormControl(false),
      isSlash: new FormControl(false),
      isTimber: new FormControl(false),
      remarks: new FormControl('')
    });
  }

  initResourceTrainingVo() {
    this.resourceTrainingVo = <ResourceTrainingVo> {
      id: 0,
      startDateTransferVo: <DateTransferVo> {dateString: '', timeString: ''},
      endDateTransferVo: <DateTransferVo> {dateString: '', timeString: ''},
      ptbPercentage: 0,
      numberOfAcres: 0,
      tnspComments: '',
      recommendationVo: {},
      complexityVo: {},
      priorityProgramVo: <PriorityProgramVo> {},
      fuelTypeVos: [] as DropdownData[],
      lengthAssignment: ''
    }

    this.populateForm();
  }

  populateForm() {
    this.selectedRecommendationDropdownData = this.ddRecommendation.getDropdownDataObjectById(-2);
    this.selectedComplexityDropdownData = this.ddComplexity.getDropdownDataObjectById(-2);

    this.assignmentCloseoutForm.setValue({
      startDate: this.resourceTrainingVo.startDateTransferVo.dateString,
      endDate: this.resourceTrainingVo.endDateTransferVo.dateString,
      lengthOfAssign: '',
      ptbProgress: this.resourceTrainingVo.ptbPercentage,
      complexityVo: {},
      recommendationVo: {},
      acres: this.resourceTrainingVo.numberOfAcres,
      isBrush: this.fuelTypesChecked(this.resourceTrainingVo.fuelTypeVos, 'B'),
      isGrass: this.fuelTypesChecked(this.resourceTrainingVo.fuelTypeVos, 'G'),
      isSlash: this.fuelTypesChecked(this.resourceTrainingVo.fuelTypeVos, 'S'),
      isTimber: this.fuelTypesChecked(this.resourceTrainingVo.fuelTypeVos, 'T'),
      remarks: this.resourceTrainingVo.tnspComments
    });

    setTimeout(() => {
      if (this.resourceTrainingVo.recommendationVo) {
        this.selectedRecommendationDropdownData = this.ddRecommendation.getDropdownDataObjectById(this.resourceTrainingVo.recommendationVo.id);
      }
      if (this.resourceTrainingVo.complexityVo) {
        this.selectedComplexityDropdownData = this.ddComplexity.getDropdownDataObjectById(this.resourceTrainingVo.complexityVo.id);
      }
    }); 
    
    this.calculateLengthOfAssignment();
  }

  calculateLengthOfAssignment() {
    var diffDays;

    if (this.dtStart.getFormattedDate() != null && this.dtEnd.getFormattedDate() != null) {
      var startD = new Date(this.dtStart.getFormattedDate());
      var endD = new Date(this.dtEnd.getFormattedDate());
      var duration = endD.valueOf() - startD.valueOf();
      diffDays = Math.ceil(duration / (1000 * 3600 * 24) + 1);
    }

    this.assignmentCloseoutForm.get('lengthOfAssign').patchValue(diffDays);
  }

  fuelTypesChecked(fuelTypeVos: DropdownData[], code: string) {
    for ( const fuelTypeVo of fuelTypeVos) {
      if (fuelTypeVo.code === code) {
        return true;
      } 
    }
    return false;
  }

  getComplexities() {
    this.referenceDataService.getComplexityList()
      .subscribe(data => {
        if ( data['courseOfActionVo']['coaName'] === 'GET_COMPLEXITIES') {
          this.complexityDropdownData = data['recordset'] as DropdownData[];
        }
      });
  }

  getRecommendations() {
    this.referenceDataService.getRecommendationList()
      .subscribe(data => {
        if ( data['courseOfActionVo']['coaName'] === 'GET_RECOMMENDATIONS') {
          this.recommendationDropdownData = data['recordset'] as DropdownData[];
        }
      });
  }

  updateAcres() {
    this.trainingSettingsVo.numberOfAcres = this.assignmentCloseoutForm.get('acres').value;

    const trainingsettingsData = <TrainingSettingsData> {
      trainingSettingsVo: this.trainingSettingsVo
    }

    this.trainingSpecialistSettingsService.saveAcres(trainingsettingsData)
    .subscribe(data => {
      this.notificationService.inspectResult(data);
      
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_ACRES') {
        this.trainingSettingsVo = data['resultObject'] as TrainingSettingsVo;
      }
    }); 
  }

  save() {
    if (this.assignmentCloseoutForm.get('ptbProgress').value > 100) {
      this.showPrompt('Assignment Closeout',
            'PTB Progress cannot be higher than 100.',
            'OK',
            '');
    } else {
      this.resourceTrainingVo.startDateTransferVo.dateString = this.dtStart.getFormattedDate();
      this.resourceTrainingVo.endDateTransferVo.dateString = this.dtEnd.getFormattedDate();
      this.resourceTrainingVo.ptbPercentage = this.assignmentCloseoutForm.get('ptbProgress').value;
      this.resourceTrainingVo.recommendationVo = <DropdownData> {id:0};
      this.resourceTrainingVo.recommendationVo.id = this.ddRecommendation.selectedValue.id;
      this.resourceTrainingVo.complexityVo = <DropdownData> {id:0};
      this.resourceTrainingVo.complexityVo.id = this.ddComplexity.selectedValue.id;
      this.resourceTrainingVo.numberOfAcres = this.assignmentCloseoutForm.get('acres').value;
      this.resourceTrainingVo.tnspComments = this.assignmentCloseoutForm.get('remarks').value;
      this.resourceTrainingVo.incidentResourceVo = <IncidentResourceVo> {id: this.resourceTrainingVo.id};
      this.resourceTrainingVo.rscTrainingTrainerVos =  [];

      const ftVos: DropdownData[] = [];
      
      if(this.assignmentCloseoutForm.get('isBrush').value === true) {
        ftVos.push(this.populateFuelTypeVo('B'));
      }
      if(this.assignmentCloseoutForm.get('isGrass').value === true) {
        ftVos.push(this.populateFuelTypeVo('G'));
      }
      if(this.assignmentCloseoutForm.get('isSlash').value === true) {
        ftVos.push(this.populateFuelTypeVo('S'));
      }
      if(this.assignmentCloseoutForm.get('isTimber').value === true) {
        ftVos.push(this.populateFuelTypeVo('T'));
      }

      this.resourceTrainingVo.fuelTypeVos = ftVos;


      const resourceTrainingData = <ResourceTrainingData> {
      resourceTrainingVo: this.resourceTrainingVo,
      dialogueVo: null
      }

      this.trainingSpecialistService.saveResourceTraining(resourceTrainingData)
        .subscribe(data => {
          this.notificationService.inspectResult(data);
          
          if ( data['courseOfActionVo']['coaName'] === 'SAVE_RESOURCE_TRAINING') {
            this.resourceTrainingVo = data['resultObject'] as ResourceTrainingVo;
          }
        }); 
    }
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

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
    this.closeModalEvent.emit();
  }

  showPrompt(title, msg, btn1, btn2) {
    this.promptModalAssignmentCloseout.reset();
    this.promptModalAssignmentCloseout.promptTitle = title;
    this.promptModalAssignmentCloseout.promptMessage1 = msg;
    this.promptModalAssignmentCloseout.button1Label = btn1;
    this.promptModalAssignmentCloseout.button2Label = btn2;
    this.promptModalAssignmentCloseout.openModal();
  }

  promptActionResultAssignmentCloseout(event) {
    this.promptModalAssignmentCloseout.closeModal();
  }

}
