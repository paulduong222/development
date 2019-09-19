import { Component, OnInit, ViewChild } from '@angular/core';
import { EvaluatorModalComponent } from 'src/app/modules/incidents/modules/training/training-form/evaluator-tab/evaluator-modal/evaluator-modal.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { RscTrainingTrainerVo } from 'src/app/data/rsc-training-trainer-vo';
import { ResourceTrainingVo } from 'src/app/data/resource-training-vo';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingSpecialistService } from 'src/app/service/training-specialist.service';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-evaluator-tab',
  templateUrl: './evaluator-tab.component.html',
  styleUrls: ['./evaluator-tab.component.css']
})
export class EvaluatorTabComponent implements OnInit {
  @ViewChild('evaluatorModal') evaluatorModal: EvaluatorModalComponent;
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  @ViewChild('grdEvaluators') grdEvaluators: EisGridComponent;

  resourceTrainingVo: ResourceTrainingVo;
  rscTrainingTrainerVo: RscTrainingTrainerVo;
  currentEventName: string = '';

  evalGridColumnDefs = [
    {headerName: 'Request #', field: 'requestNumber', width: 96},
    {headerName: 'Resource Name', field: 'resourceName', width: 180},
    {headerName: 'Item Code', field: 'kindVo.code', width: 96},
    {headerName: 'Item Description', field: 'kindVo.description', width: 197},
    {headerName: 'Status', field: 'status', width: 76},
    {headerName: 'Unit ID', field: 'unitVo.unitCode', width: 80},
    {headerName: 'Unit Description', field: 'unitVo.name', width: 197},
  ];

  constructor(private notificationService: NotificationService,
              private trainingSpecialistService: TrainingSpecialistService) { }

  ngOnInit() {
    this.initResourceTrainingVo();
  }


  initResourceTrainingVo() {
    this.resourceTrainingVo = <ResourceTrainingVo> {
      id: 0,
      rscTrainingTrainerVos: []
    } 
  }

  onSelectEvaluator(row: any) {
    if (row != undefined) {
      this.rscTrainingTrainerVo = Object.assign({}, row);
    }
  }

  addEvaluator() {
    if (this.resourceTrainingVo.id > 0) {
      this.evaluatorModal.resourceTrainingId = this.resourceTrainingVo.id;
      this.evaluatorModal.openModal('evaluator-modal', this.resourceTrainingVo.incidentResourceVo.id, 'add');
    }else {
      this.showPrompt('Training Contact',
      'Select a saved Trainee Assignment before adding an evaluator.',
      'OK',
      '');
    }
  }

  editEvaluator() {
    if (this.rscTrainingTrainerVo.id > 0) {
      this.evaluatorModal.resourceTrainingId = this.resourceTrainingVo.id;
      this.evaluatorModal.rscTrainingTrainerVo = this.rscTrainingTrainerVo;
      this.evaluatorModal.openModal('evaluator-modal', this.resourceTrainingVo.incidentResourceVo.id, 'edit');
    } else {
      this.showPrompt('Training Specialist'
            , 'Please select an Evaluator to edit.'
            , 'Ok'
            , '');  
    } 
  }

  showPrompt(title, msg, btn1, btn2) {
    this.promptModal.reset();
    this.promptModal.promptTitle = title;
    this.promptModal.promptMessage1 = msg;
    this.promptModal.button1Label = btn1;
    this.promptModal.button2Label = btn2;
    this.promptModal.openModal();
  }

  promptModalActionResult(event) {
    this.promptModal.closeModal();
    if ( this.currentEventName === 'PROMPT_DELETE_EVALUATOR' && event === 'Yes') {
      this.proceedWithDelete();
    }  
  }

  saveEvaluatorEvent(rscTrainingTrainerVo: RscTrainingTrainerVo) {
    this.grdEvaluators.updateRowById(rscTrainingTrainerVo);
    this.resourceTrainingVo.rscTrainingTrainerVos.push(rscTrainingTrainerVo);
  }

  deleteEvaluator() {
    
    if (this.rscTrainingTrainerVo.id > 0) {
      this.currentEventName = 'PROMPT_DELETE_EVALUATOR';
      this.showPrompt('Confirm Delete'
            , 'Do you really want to remove the Evaluator?'
            , 'Yes'
            , 'No');
    } else {
      this.showPrompt('Training Specialist'
            , 'Please select an Evaluator to delete.'
            , 'OK'
            , '');
    } 
  }

  proceedWithDelete() {
    this.trainingSpecialistService.deleteEvaluator(this.rscTrainingTrainerVo.id, this.resourceTrainingVo.incidentResourceVo.id)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        if (data['courseOfActionVo']['coaName'] === 'DELETE_TRAINER') {
          // remove selected rows
          this.grdEvaluators.removeSelectedRows();

          // remove from jetportvos
          const idx = this.resourceTrainingVo.rscTrainingTrainerVos.findIndex(row => row.id === this.rscTrainingTrainerVo.id,);
          if ( idx > -1 ) {
            this.resourceTrainingVo.rscTrainingTrainerVos.splice(idx, 1);
          }
        }
      });
  }

}
