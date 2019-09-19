import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { RscTrainingTrainerVo } from 'src/app/data/rsc-training-trainer-vo';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { TrainingSpecialistReportFilter } from 'src/app/data/filter/training-specialist-report-filter';
import { TextInputComponent } from 'src/app/components/text-input/text-input.component';
import { TrainingSpecialistVo } from 'src/app/data/training-specialist-vo';

@Component({
  selector: 'app-reports-evaluation-modal',
  templateUrl: './reports-evaluation-modal.component.html',
  styleUrls: ['./reports-evaluation-modal.component.css']
})
export class ReportsEvaluationModalComponent implements OnInit {
  @Output() previewPrintEvent = new EventEmitter();
  @ViewChild('ddTrainingSpecialists') ddTrainingSpecialists: EisDropdownComponent;
  @ViewChild('ddEvaluators') ddEvaluators: EisDropdownComponent;
  @ViewChild('txEvaluationRecordNumber') txEvaluationRecordNumber: TextInputComponent;
  @ViewChild('promptModalTrainingRpts') promptModalTrainingRpts: PromptModalComponent;

  reportName = 'Data Form';
  windowTitle: string = 'Preview/Print Form';
  showEvaluatorsDD: boolean = true; 
  showTrainingSpecialistsDD: boolean = false;
  showEvaluationRecord: boolean;
  trainingSpecialistVos: DropdownData[] = [];
  selectedTrainingSpecialistVo: DropdownData;
  rscTrainingTrainerVos: DropdownData[] = [];
  selectedRscTrainingTrainerVo: DropdownData;
  tnspReportFilter: TrainingSpecialistReportFilter;
  
  constructor(private modalService: ModalService) { 
  }

  ngOnInit() {
  }

  setTrainingSpecialists(trainingSpecialistVos: TrainingSpecialistVo[]) {
    if (this.ddTrainingSpecialists != undefined) {
      this.selectedTrainingSpecialistVo = this.ddTrainingSpecialists.getDropdownDataObjectById(-2);
    }
    
    this.trainingSpecialistVos = [];

    for (let i in trainingSpecialistVos) {
      let tsVo = trainingSpecialistVos[i];
      this.trainingSpecialistVos.push({id: tsVo.id, code: tsVo.tnspName, desc: ''});
    }

    if (this.trainingSpecialistVos.length === 1) {
      setTimeout(() => {
        this.selectedTrainingSpecialistVo = this.ddTrainingSpecialists.getDropdownDataObjectById(this.trainingSpecialistVos[0].id);
      }); 
    }
  }

  setEvaluators(rscTrainingTrainerVos: RscTrainingTrainerVo[]) {
    if (this.ddEvaluators != undefined) {
      this.selectedRscTrainingTrainerVo = this.ddEvaluators.getDropdownDataObjectById(-2);
    }
    
    this.rscTrainingTrainerVos = [];

    for (let i in rscTrainingTrainerVos) {
      let rttVo = rscTrainingTrainerVos[i];
      this.rscTrainingTrainerVos.push({id: rttVo.id, code: rttVo.resourceName, desc: ''});
    }
    
    if (this.rscTrainingTrainerVos.length === 1) {
      setTimeout(() => {
        this.selectedRscTrainingTrainerVo = this.ddEvaluators.getDropdownDataObjectById(this.rscTrainingTrainerVos[0].id);
      });
    }
  } 

  printPreviewReport(id: string) {
    let msg1:string = '';
    let msg2:string = '';
    let trainingSpecialistId:number = 0;
    let trainerId:number = 0;
    let evalRecNum: string = '';

    if (this.ddTrainingSpecialists != undefined) {
      if (this.ddTrainingSpecialists.selectedValue.id < 0) {
        msg1 = 'Training Specialist is a required field.';
      } else {
        trainingSpecialistId = this.ddTrainingSpecialists.selectedValue.id;
      }
    }
    
    if (this.ddEvaluators != undefined) {
      if (this.ddEvaluators.selectedValue.id < 0) {
        msg2 = 'Evaluator is a required field.';
      } else {
        trainerId = this.ddEvaluators.selectedValue.id;
      }
    }

    if(this.txEvaluationRecordNumber != undefined) {
      evalRecNum = this.txEvaluationRecordNumber.value.toUpperCase();
    }

    if (msg1.length > 0 || msg2.length > 0) {
      this.showPrompt('Preview Print ' + this.reportName + ' Form', msg1, msg2, 'OK');
    } else {
      let tnspReportFilter = <TrainingSpecialistReportFilter> {
        resourceTrainingId: 0,
        blankForm: 'no',
        trainingSpecialistId: trainingSpecialistId,
        trainerId: trainerId,
        evaluationRecordNumber: evalRecNum
      };

     this.previewPrintEvent.emit(tnspReportFilter);
     this.modalService.close(id);
    }
  }

  showPrompt(title, msg1, msg2, btn1) {
    this.promptModalTrainingRpts.reset();
    this.promptModalTrainingRpts.promptTitle = title;
    this.promptModalTrainingRpts.promptMessage1 = msg1;
    this.promptModalTrainingRpts.promptMessage2 = msg2;
    this.promptModalTrainingRpts.button1Label = btn1;
    this.promptModalTrainingRpts.openModal();
  }

  promptActionResultTrainingRpts() {
    this.promptModalTrainingRpts.closeModal();
  }

  openModal(id: string) {
    if(this.txEvaluationRecordNumber != undefined) {
      this.txEvaluationRecordNumber.value = '';
    }
    
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
  }

}
