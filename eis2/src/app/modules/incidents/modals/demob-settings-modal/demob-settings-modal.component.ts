import { Component, OnInit, Output, EventEmitter, AfterViewInit, ViewChild } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { IncidentService } from 'src/app/service/incident.service';
import { IncidentPrefsVo } from 'src/app/data/incident-prefs-vo';
import { IncidentGroupService } from 'src/app/service/incident-group.service';
import { IncidentGroupPrefsVo } from 'src/app/data/incident-group-prefs-vo';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { IncidentQuestionVo } from 'src/app/data/incident-question-vo';
import { IncidentGroupQuestionVo } from 'src/app/data/incident-group-question-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { IncidentGroupVo } from 'src/app/data/incident-group-vo';
import { WorkPeriodQuestionValueVo } from 'src/app/data/work-period-question-value-vo';
import { QuestionVo } from 'src/app/data/question-vo';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-demob-settings-modal',
  templateUrl: './demob-settings-modal.component.html',
  styleUrls: ['./demob-settings-modal.component.css']
})
export class DemobSettingsModalComponent implements OnInit, AfterViewInit {
  @ViewChild('promptModalDemobSettings') promptModalDemobSettings: PromptModalComponent;
  @ViewChild('gridQuestions') gridQuestion: EisGridComponent;
  @Output() closeModalEvent = new EventEmitter();

  windowLabel = 'Edit Incident Demob Settings';
  incidentTypeLabel = 'Incident: ';
  incidentNameLabel = '';
  selectedTab = 'checkoutform';
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};

  selectedItem = <IncidentPrefsVo>{};
  selectedQuestion = {};

  prefVos = [];
  logisticsPrefs = [];
  financePrefs = [];
  planningPrefs = [];
  otherPrefs = [];

  gridColumnDefs = [
    {headerName: 'Pos', field: 'position', width: 20, sort: 'asc'},
    {headerName: 'Question', field: 'questionVo.question', width: 330},
    {headerName: 'Display', field: 'visible', width: 90, cellRenderer: 'checkboxRenderer'},
    {headerName: 'Standard', field: 'questionVo.standard', width: 95, cellRenderer: 'checkboxRenderer'},
  ];

  airTravelQuestionVos = [];
  questionsForm: FormGroup;
  questionMode = 'add';

  constructor(private modalService: ModalService,
              public incidentSelectorService: IncidentSelectorService,
              private incidentService: IncidentService,
              private incidentGroupService: IncidentGroupService,
              private notifyService: NotificationService,
              private formBuilder: FormBuilder ) {}

  ngOnInit() {
    this.questionsForm = this.formBuilder.group({
      question: new FormControl({value: '', disabled: false})
      , visible: new FormControl(false)
    });
  }

  ngAfterViewInit() {
  }

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
    this.closeModalEvent.emit();
  }

  loadPage() {
    // reset arrays
    this.logisticsPrefs = [];
    this.financePrefs = [];
    this.planningPrefs = [];
    this.otherPrefs = [];

    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      this.windowLabel = 'Edit Incident Demob Settings';
      this.incidentTypeLabel = 'Incident: ';
      this.incidentNameLabel = this.currentSelectedIncidentSelectorVo.name;
      this.loadIncidentPrefsData();
    }
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      this.windowLabel = 'Edit Incident Group Demob Settings';
      this.incidentTypeLabel = 'Incident Group: ';
      this.incidentNameLabel = this.currentSelectedIncidentSelectorVo.name;
      this.loadIncidentGroupPrefsData();
    }
  }

  loadIncidentPrefsData() {
    this.incidentService.getIncidentPrefs(this.currentSelectedIncidentSelectorVo.incidentId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if (data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_PREFS') {
        this.prefVos = data['recordset'] as IncidentPrefsVo[];
        this.prefVos.forEach( vo => {
          if ( vo.sectionName === 'LOGISTICS') {
            this.logisticsPrefs.push(vo);
          } else if (vo.sectionName === 'PLANNING') {
            this.planningPrefs.push(vo);
          } else if (vo.sectionName === 'FINANCE') {
            this.financePrefs.push(vo);
          } else if (vo.sectionName === 'OTHER_LABEL') {
            this.otherPrefs.push(vo);
          }

        });
      }
    });
  }

  loadIncidentGroupPrefsData() {
    this.incidentGroupService.getIncidentGroupCheckoutQuestions(this.currentSelectedIncidentSelectorVo.incidentGroupId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if (data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_GROUP_PREFS') {
        this.prefVos = data['recordset'] as IncidentGroupPrefsVo[];
        this.prefVos.forEach( vo => {
          if ( vo.sectionName === 'LOGISTICS') {
            this.logisticsPrefs.push(vo);
          } else if (vo.sectionName === 'PLANNING') {
            this.planningPrefs.push(vo);
          } else if (vo.sectionName === 'FINANCE') {
            this.financePrefs.push(vo);
          } else if (vo.sectionName === 'OTHER_LABEL') {
            this.otherPrefs.push(vo);
          }

        });
      }
    });
  }

  dataTabSelect(tabname) {
    this.selectedTab = tabname;
    if ( this.selectedTab === 'checkoutform') {
      if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      } else {
      }
    }
    if ( this.selectedTab === 'airtravelquestions') {
      this.addQuestion();
 
       if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
        this.loadIncidentAirTravelQuestions();
      } else {
        this.loadIncidentGroupAirTravelQuestions();
      }
    }
  }

  getStyle(menuName) {
    return ( this.selectedTab === menuName ? 'btn-selected' : '' );
  }

  getActiveTab(tabName) {
    if ( this.selectedTab === tabName) {
      return 'dv-demob-tab eis';
    } else {
      return 'hidden';
    }
  }

  onSelectItem(item) {
    this.selectedItem = item;
  }

  moveUp(sectionName) {
    if ( sectionName === 'LOGISTICS' && this.selectedItem !== undefined && this.selectedItem.sectionName === sectionName) {
      if (this.selectedItem.position > 0) {
        this.swap(this.logisticsPrefs, this.selectedItem.position, this.selectedItem.position - 1);
      }
    }
    if ( sectionName === 'FINANCE' && this.selectedItem !== undefined && this.selectedItem.sectionName === sectionName) {
      if (this.selectedItem.position < this.financePrefs.length) {
        this.swap(this.financePrefs, this.selectedItem.position, this.selectedItem.position - 1);
      }
    }
    if ( sectionName === 'PLANNING' && this.selectedItem !== undefined && this.selectedItem.sectionName === sectionName) {
      if (this.selectedItem.position < this.planningPrefs.length) {
        this.swap(this.planningPrefs, this.selectedItem.position, this.selectedItem.position - 1);
      }
    }
    if ( sectionName === 'OTHER_LABEL' && this.selectedItem !== undefined && this.selectedItem.sectionName === sectionName) {
      if (this.selectedItem.position < this.otherPrefs.length) {
        this.swap(this.otherPrefs, this.selectedItem.position, this.selectedItem.position - 1);
      }
    }
  }

  moveDown(sectionName) {
    if ( sectionName === 'LOGISTICS' && this.selectedItem !== undefined && this.selectedItem.sectionName === sectionName) {
      if (this.selectedItem.position < (this.logisticsPrefs.length - 1 )) {
        this.swap(this.logisticsPrefs, this.selectedItem.position, this.selectedItem.position + 1);
      }
    }
    if ( sectionName === 'FINANCE' && this.selectedItem !== undefined && this.selectedItem.sectionName === sectionName) {
      if (this.selectedItem.position < (this.financePrefs.length - 1 )) {
        this.swap(this.financePrefs, this.selectedItem.position, this.selectedItem.position + 1);
      }
    }
    if ( sectionName === 'PLANNING' && this.selectedItem !== undefined && this.selectedItem.sectionName === sectionName) {
      if (this.selectedItem.position < (this.planningPrefs.length - 1 )) {
        this.swap(this.planningPrefs, this.selectedItem.position, this.selectedItem.position + 1);
      }
    }
    if ( sectionName === 'OTHER_LABEL' && this.selectedItem !== undefined && this.selectedItem.sectionName === sectionName) {
      if (this.selectedItem.position < (this.otherPrefs.length - 1)) {
        this.swap(this.otherPrefs, this.selectedItem.position, this.selectedItem.position + 1);
      }
    }
  }

  swap(array:any[], xPositionToMove: any, yPositionTarget: any) {
    // target object to replace
    const b = array[yPositionTarget];
    b['position'] = xPositionToMove;

    // move selectedItem to yTarget
    this.selectedItem.position = yPositionTarget;
    array[yPositionTarget] = this.selectedItem;

    array[xPositionToMove] = b;
  }

  setItemChecked(sectionName, idx, event) {
    if ( sectionName === 'LOGISTICS' ) {
      this.logisticsPrefs[idx].selected = event.target.checked;
    }
    if ( sectionName === 'FINANCE' ) {
      this.financePrefs[idx].selected = event.target.checked;
    }
    if ( sectionName === 'PLANNING' ) {
      this.planningPrefs[idx].selected = event.target.checked;
    }
    if ( sectionName === 'OTHER_LABEL' ) {
      this.otherPrefs[idx].selected = event.target.checked;
    }
  }

  setItemValue(sectionName, idx, event) {
    if ( sectionName === 'OTHER_LABEL' ) {
      this.otherPrefs[idx].fieldLabel = event.target.value;
    }
  }

  loadIncidentAirTravelQuestions() {
    this.airTravelQuestionVos = [];
    this.gridQuestion.clearSelected();

    this.incidentService.getIncidentAirTravelQuestions(
      this.currentSelectedIncidentSelectorVo.incidentId)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_AIR_TRAVEL_QUESTIONS') {
          this.airTravelQuestionVos = data['recordset'] as IncidentQuestionVo[];
          this.gridQuestion.gridOptions.api.setRowData(this.airTravelQuestionVos);
          this.addQuestion();
        }
      });
  }

  loadIncidentGroupAirTravelQuestions() {
    this.airTravelQuestionVos = [];
    this.gridQuestion.clearSelected();

    this.incidentGroupService.getIncidentGroupAirTravelQuestions(
      this.currentSelectedIncidentSelectorVo.incidentGroupId)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_GROUP_AIR_TRAVEL_QUESTIONS') {
          this.airTravelQuestionVos = data['recordset'] as IncidentGroupQuestionVo[];
          this.gridQuestion.gridOptions.api.setRowData(this.airTravelQuestionVos);
          this.addQuestion();
        }
      });
  }

  getQuestionBtnStyle(btnName) {
    return ( this.questionMode === btnName ? 'btn-selected' : '' );
  }

  onSelectQuestion(item) {
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      this.selectedQuestion = item as IncidentQuestionVo;
    } else {
      this.selectedQuestion = item as IncidentGroupQuestionVo;
    }

    if ( this.selectedQuestion !== undefined && this.selectedQuestion['id'] > 0 ) {
      this.questionMode = 'edit';
      this.resetQuestionForm();
    }
  }

  addQuestion() {
    this.questionMode = 'add';
    this.gridQuestion.clearSelected();

    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      this.selectedQuestion = <IncidentQuestionVo>{
        id: 0
        , visible: false
        , position: 200
        , incidentVo: <IncidentVo>{
          id: this.currentSelectedIncidentSelectorVo.incidentId
        }
        // , workPeriodQuestionValueVo: <WorkPeriodQuestionValueVo>{}
        , questionVo: <QuestionVo>{
          id: 0
          , question: ''
          , standard: false
          , questionType: 'AIRTRAVEL'
        }
      };

      this.resetQuestionForm();
    } else {
      this.selectedQuestion = <IncidentGroupQuestionVo>{
        id: 0
        , visible: false
        , position: 200
        , incidentGroupId: this.currentSelectedIncidentSelectorVo.incidentGroupId
        // , workPeriodQuestionValueVo: <WorkPeriodQuestionValueVo>{}
        , questionVo: <QuestionVo>{
          id: 0
          , question: ''
          , standard: false
          , questionType: 'AIRTRAVEL'
        }
      };
      this.resetQuestionForm();
    }
  }

  deleteQuestion() {
    if ( this.selectedQuestion === undefined || this.selectedQuestion['id'] === 0 ) {
      this.showPrompt('Air Travel Question', 'Please select a question to delete', 'Ok', '');
      return;
    }

    if ( this.selectedQuestion['questionVo'].standard === true ) {
      this.showPrompt('Air Travel Question', 'You cannot delete standard questions.', 'Ok', '');
      return;
    }

    this.showPrompt('Air Travel Question', 'Do you really want to remove the Air Travel Question?', 'Yes', 'No');
  }

  resetQuestionForm() {
    this.questionsForm.setValue({
      question: this.selectedQuestion['questionVo'].question
      , visible: this.selectedQuestion['visible']
    });
    if ( this.selectedQuestion['questionVo'].standard === true ) {
      this.questionsForm.controls['question'].disable();
    } else {
      this.questionsForm.controls['question'].enable();
    }

  }

  moveQuestionUp() {
    // list is ordered as 1 base and not 0 base
    if ( this.selectedQuestion !== undefined && this.selectedQuestion['position'] > 1 ) {
      // get current position ( 1 base )
      const pos = this.selectedQuestion['position'];

      // get the row that will be swapped
      let swapObject = {};
      this.airTravelQuestionVos.forEach(row => {
        if (row.position === (pos - 1)) {
          swapObject = row;
        }
      });
      swapObject['position'] = 200;  // set to 200 temporary
      this.gridQuestion.updateRowById(swapObject); // move to end of list as temporary

      this.selectedQuestion['position'] = (pos - 1);

      this.gridQuestion.updateRowById(this.selectedQuestion);
      // update in airtavelquestionvos
      this.airTravelQuestionVos.forEach(row => {
        if ( row.id === this.selectedQuestion['id']) {
          row.position = (pos - 1 );
        }
      });

      swapObject['position'] = pos;
      this.gridQuestion.updateRowById(swapObject);
      // update in airtavelquestionvos
      this.airTravelQuestionVos.forEach(row => {
        if ( row.id === swapObject['id']) {
          row.position = pos;
        }
      });

    }
  }

  moveQuestionDown() {
    // list is ordered as 1 base and not 0 base
    if ( this.selectedQuestion !== undefined && this.selectedQuestion['position'] < (this.airTravelQuestionVos.length ) ) {

      // get current position ( 1 base )
      const pos = this.selectedQuestion['position'];

      // get the row that will be swapped
      // since airtravelquestions is 0 based, just use 'const pos' value
      let swapObject = {};
      this.airTravelQuestionVos.forEach(row => {
        if (row.position === (pos + 1)) {
          swapObject = row;
        }
      });
      swapObject['position'] = 200;  // set to 200 temporary
      this.gridQuestion.updateRowById(swapObject); // move to end of list as temporary

      // move selected question position + 1
      this.selectedQuestion['position'] = (pos + 1);
      this.gridQuestion.updateRowById(this.selectedQuestion);

      // update in airtavelquestionvos
      this.airTravelQuestionVos.forEach(row => {
        if ( row.id === this.selectedQuestion['id']) {
          row.position = (pos + 1 );
        }
      });

      // move swap object back to the original selectedQuestions position
      swapObject['position'] = pos;
      this.gridQuestion.updateRowById(swapObject);
      // update in airtavelquestionvos
      this.airTravelQuestionVos.forEach(row => {
        if ( row.id === swapObject['id']) {
          row.position = pos;
        }
      });

    }
  }

  save() {
    if ( this.selectedTab === 'checkoutform') {

      if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
        this.saveIncidentPrefs();
      } else {
        this.saveIncidentGroupPrefs();
      }
    }

    if ( this.selectedTab === 'airtravelquestions') {

      if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
        this.saveIncidentQuestion();
      }
      if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
        this.saveIncidentGroupQuestion();
      }
    }

  }

  saveIncidentPrefs() {
    this.saveIncidentPrefs();
    let savePrefVos: IncidentPrefsVo[] = [];
    this.logisticsPrefs.forEach(row => {
      savePrefVos.push(row);
    });
    this.financePrefs.forEach(row => {
      savePrefVos.push(row);
    });
    this.planningPrefs.forEach(row => {
      savePrefVos.push(row);
    });
    this.otherPrefs.forEach(row => {
      savePrefVos.push(row);
    });

    this.incidentService.saveIncidentPrefs(
      this.currentSelectedIncidentSelectorVo.incidentId, savePrefVos)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
    });
  }

  saveIncidentGroupPrefs() {
    let savePrefVos: IncidentGroupPrefsVo[] = [];
    this.logisticsPrefs.forEach(row => {
      savePrefVos.push(row);
    });
    this.financePrefs.forEach(row => {
      savePrefVos.push(row);
    });
    this.planningPrefs.forEach(row => {
      savePrefVos.push(row);
    });
    this.otherPrefs.forEach(row => {
      savePrefVos.push(row);
    });

    this.incidentGroupService.saveIncidentGroupCheckoutQuestions(
      this.currentSelectedIncidentSelectorVo.incidentGroupId, savePrefVos)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
    });
  }

  saveIncidentQuestion() {
    let isNew = false;
    if ( this.selectedQuestion['id'] < 1 ) {
      // set position to last
      this.selectedQuestion['position'] = this.airTravelQuestionVos.length + 1;
      isNew = true;
    }

    this.selectedQuestion['questionVo'].question = this.questionsForm.get('question').value;
    this.selectedQuestion['visible'] = this.questionsForm.get('visible').value;

    let vosToSave = [];

    if ( this.selectedQuestion['questionVo'].standard === true ) {
      this.airTravelQuestionVos.forEach(row => {
        if ( row.id !== this.selectedQuestion['id']) {
          vosToSave.push(row as IncidentQuestionVo);
        }
      });
      vosToSave.push(this.selectedQuestion as IncidentQuestionVo);
    } else {
      this.airTravelQuestionVos.forEach(row => {
          vosToSave.push(row as IncidentQuestionVo);
      });

      // if new record, only add to save list if question is not empty
      if ( isNew === true && this.selectedQuestion['questionVo'].question !== '') {
        vosToSave.push(this.selectedQuestion as IncidentQuestionVo);
      }
    }

    this.showMessage('Air Travel Question', 'Saving questions');
    this.incidentService.saveIncidentAirTravelQuestions(
      this.currentSelectedIncidentSelectorVo.incidentId, vosToSave)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_INCIDENT_QUESTIONS') {
          const incidentVo: IncidentVo = data['resultObject'] as IncidentVo;

          this.airTravelQuestionVos = incidentVo.airTravelQuestions;
          this.gridQuestion.gridOptions.api.setRowData(this.airTravelQuestionVos);
          this.addQuestion();
        }
        this.promptModalDemobSettings.closeModal();
      });
  }

  saveIncidentGroupQuestion() {
    let isNew = false;
    if ( this.selectedQuestion['id'] < 1 ) {
      // set position to last
      this.selectedQuestion['position'] = this.airTravelQuestionVos.length + 1;
      isNew = true;
    }

    this.selectedQuestion['questionVo'].question = this.questionsForm.get('question').value;
    this.selectedQuestion['visible'] = this.questionsForm.get('visible').value;

    let vosToSave = [];

    if ( this.selectedQuestion['questionVo'].standard === true ) {
      this.airTravelQuestionVos.forEach(row => {
        if ( row.id !== this.selectedQuestion['id']) {
          vosToSave.push(row as IncidentGroupQuestionVo);
        }
      });
      vosToSave.push(this.selectedQuestion as IncidentGroupQuestionVo);
    } else {
      this.airTravelQuestionVos.forEach(row => {
          vosToSave.push(row as IncidentGroupQuestionVo);
      });

      // if new record, only add to save list if question is not empty
      if ( isNew === true && this.selectedQuestion['questionVo'].question !== '') {
        vosToSave.push(this.selectedQuestion as IncidentGroupQuestionVo);
      }
    }

    this.showMessage('Air Travel Question', 'Saving questions');
    this.incidentGroupService.saveIncidentGroupAirTravelQuestions(
      this.currentSelectedIncidentSelectorVo.incidentGroupId, vosToSave)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_INCIDENT_GROUP_QUESTIONS') {
          const incidentGroupVo: IncidentGroupVo = data['resultObject'] as IncidentGroupVo;

          this.airTravelQuestionVos = incidentGroupVo.airTravelQuestions;
          this.gridQuestion.gridOptions.api.setRowData(this.airTravelQuestionVos);
          this.addQuestion();
        }
        this.promptModalDemobSettings.closeModal();
      });
  }

  cancel() {
    if ( this.selectedTab === 'checkoutform') {
      this.loadPage();
    } else {
      this.dataTabSelect('airtravelquestions');
    }
  }

  showMessage(title, msg) {
    this.promptModalDemobSettings.reset();
    this.promptModalDemobSettings.promptTitle = title;
    this.promptModalDemobSettings.promptMessage1 = msg;
    this.promptModalDemobSettings.openModal();
  }

  showPrompt(title, msg, btn1, btn2) {
    this.promptModalDemobSettings.reset();
    this.promptModalDemobSettings.promptTitle = title;
    this.promptModalDemobSettings.promptMessage1 = msg;
    this.promptModalDemobSettings.button1Label = btn1;
    this.promptModalDemobSettings.button2Label = btn2;
    this.promptModalDemobSettings.openModal();
  }

  promptActionResult(btnEvent ) {
    this.promptModalDemobSettings.closeModal();
    if ( this.promptModalDemobSettings.promptMessage1 = 'Do you really want to remove the Air Travel Question?' ) {
      if ( btnEvent === 'Yes') {
        this.proceedWithDelete();
      }
    }
  }

  proceedWithDelete() {
    let newAirTravelQuestionVos = [];

    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      // remove from airTravelQuestions and re-order list
      const pos = this.selectedQuestion['position'];
      this.airTravelQuestionVos.forEach(row => {
        if (row.position < pos ) {
          newAirTravelQuestionVos.push(row as IncidentQuestionVo);
        }

        if (row.position > pos ) {
          let newRow = Object.assign({}, row) as IncidentQuestionVo;
          const afterPos = row.position;
          console.log('after pos = ' + afterPos);
          newRow.position = (afterPos - 1);
          console.log('after pos 2 = ' + newRow.position);
          newAirTravelQuestionVos.push(newRow as IncidentQuestionVo);
        }
      });

      this.airTravelQuestionVos = newAirTravelQuestionVos;
      this.gridQuestion.gridOptions.api.setRowData(this.airTravelQuestionVos);
      setTimeout(() => {
        this.addQuestion();
      });
    }
  }
}
