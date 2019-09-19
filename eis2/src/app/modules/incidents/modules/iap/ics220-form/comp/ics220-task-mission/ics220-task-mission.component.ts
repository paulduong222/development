import { Component, OnInit, AfterViewInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { IapForm220Vo } from 'src/app/data/iap-form220-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IapAircraftVo } from 'src/app/data/iap-Aircraft-vo';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { IapFormHelper } from '../../../helpers/iap-form-helper';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { NotificationService } from 'src/app/service/notification-service';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { IapAircraftTaskVo } from 'src/app/data/iap-aircraft-task-vo';

@Component({
  selector: 'app-ics220-task-mission',
  templateUrl: './ics220-task-mission.component.html',
  styleUrls: ['./ics220-task-mission.component.css']
})
export class Ics220TaskMissionComponent implements OnInit, AfterViewInit {
  @Output() ics220ReorderOpenEvent = new EventEmitter();
  @Output() taskUpdateEvent = new EventEmitter();
  @ViewChild('icsForm220TaskPromptModal') icsForm220TaskPromptModal: PromptModalComponent;
  @ViewChild('gridTask') gridTask: EisGridComponent;

  splitAreaTopSize = 50;
  splitAreaBottomSize = 50;


  iapForm220Vo = <IapForm220Vo>{};
  selectedAircraftTaskVo = null;
  iapAircraftTaskVo = <IapAircraftTaskVo>{};

  iapFormHelper = new IapFormHelper();

  isFormLocked = false;
  compForm: FormGroup;


  // grid vars
  taskList = [];
  gridColumnDefs = [
    {headerName: '', field: 'positionNum', width: 40, pinned: 'left', sort: 'asc'},
    {headerName: 'Category/Kind/Type/Function', field: 'type', width: 250, pinned: 'left'},
    {headerName: 'Name of Personnel or Cargo or Instructions for Tactical Aircraft', field: 'name', width: 250},
    {headerName: 'Mission Start Time', field: 'startTime', width: 140},
    {headerName: 'Fly From', field: 'flyFrom', width: 200},
    {headerName: 'Fly To', field: 'flyTo', width: 200},
  ];

  constructor(private formBuilder: FormBuilder,
                private notifyService: NotificationService,
                private iapPlanService: IapPlanService) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      type: new FormControl({value: '', disabled: false})
      , name: new FormControl({value: '', disabled: false})
      , startTime: new FormControl({value: '', disabled: false})
      , flyFrom: new FormControl({value: '', disabled: false})
      , flyTo: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  initAircraftTaskVo() {
    this.iapAircraftTaskVo = this.iapFormHelper.initNewIapAircraftTaskVo(this.iapForm220Vo.id);

  }

  reloadPage(vo: IapForm220Vo) {
    this.gridTask.clearSelected();
    this.taskList = [];
    this.iapForm220Vo = Object.assign({}, vo);
    vo.iapAircraftTaskVos.forEach(f => {
      if ( f.isBlankLine === true ) {
        f.type = '[Blank Line]';
      }
      this.taskList.push(f);
    });

    this.isFormLocked = this.iapForm220Vo.isFormLocked;
    this.initAircraftTaskVo();
    setTimeout(() => {
      this.resetForm();
    });
  }

  resetForm() {
    // reset Form
    setTimeout(() => {
      this.compForm.setValue({
        type: (this.iapAircraftTaskVo.isBlankLine === true ? '' : this.iapAircraftTaskVo.type),
        name: this.iapAircraftTaskVo.name,
        startTime: this.iapAircraftTaskVo.startTime,
        flyFrom: this.iapAircraftTaskVo.flyFrom,
        flyTo: this.iapAircraftTaskVo.flyTo,
      });
    });

    setTimeout(() => {
      this.enableDisablePage();
    });

  }

  enableDisablePage() {
    const controlList = ['type', 'name', 'startTime', 'flyFrom'
      , 'flyTo'];

    if ( this.isFormLocked === true ) {
      controlList.forEach(name => {
        this.compForm.controls[name].disable();
      });
    } else {
      if ( this.iapAircraftTaskVo.isBlankLine === true ) {
        controlList.forEach(name => {
          this.compForm.controls[name].disable();
        });
     } else {
        controlList.forEach(name => {
          this.compForm.controls[name].enable();
        });
        }
    }
  }

  getIapForm220Vo() {
    return this.iapForm220Vo;
  }

  onSelectTaskRow(row) {
    if ( row !== undefined && row !== null && row.id > 0 ) {
      this.selectedAircraftTaskVo = Object.assign({}, row as IapAircraftTaskVo);
      this.iapAircraftTaskVo = Object.assign({}, row as IapAircraftTaskVo);
      this.resetForm();
    } else {
      this.selectedAircraftTaskVo = null;
    }
  }

  add() {
    this.gridTask.clearSelected();
    this.selectedAircraftTaskVo = null;
    this.initAircraftTaskVo();

    setTimeout(() => {
      this.resetForm();
    });
  }

  preAddBlankLine() {
    if ( this.selectedAircraftTaskVo === null || this.selectedAircraftTaskVo.id === 0) {
      // if no row is selected, auto to add to bottom of list
      this.addBlankLine('BOTTOM');
    } else {
      // prompt where to add blank line
      this.showPrompt('AddBlankLine', 'Add Blank Line',
       'Would you like to add the blank line above the row, below the row or at the bottom of the grid?',
        'ABOVE', 'BELOW', 'BOTTOM');
    }
  }

  addBlankLine(location) {
    let vo  = this.iapFormHelper.initNewIapAircraftTaskVo(this.iapForm220Vo.id);

    vo.isBlankLine = true;
    if (location === 'BOTTOM') {
      vo.positionNum = ( this.taskList.length + 1 );
    } else {
      const curPosition = this.selectedAircraftTaskVo.positionNum;

      if ( location === 'ABOVE' ) {
        vo.positionNum = curPosition;
      } else {
        // BELOW
        vo.positionNum = (curPosition + 1);
      }
    }

    this.iapPlanService.saveIapForm220Task(this.iapForm220Vo.id, vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_220_TASK' ) {
        this.taskList = [];
        const vos = data['recordset'] as IapAircraftTaskVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.type = '[Blank Line]';
          }
          this.taskList.push(f);
        });
        this.gridTask.gridOptions.rowData = this.taskList;
        this.add();
        this.taskUpdateEvent.emit();
      }
    });
  }

  save() {
    const isNew = ( this.iapAircraftTaskVo.id > 0 ? false : true );

    if ( isNew === false && this.iapAircraftTaskVo.isBlankLine === true ) {
      return;
    }

    this.iapAircraftTaskVo.type = this.compForm.get('type').value;
    this.iapAircraftTaskVo.name = this.compForm.get('name').value;
    this.iapAircraftTaskVo.startTime = this.compForm.get('startTime').value;
    this.iapAircraftTaskVo.flyFrom = this.compForm.get('flyFrom').value;
    this.iapAircraftTaskVo.flyTo = this.compForm.get('flyTo').value;

    if ( isNew === true ) {
      this.iapAircraftTaskVo.positionNum = (this.taskList.length + 1 );
    }

    this.iapPlanService.saveIapForm220Task(this.iapForm220Vo.id, this.iapAircraftTaskVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_220_TASK' ) {
        this.taskList = [];
        const vos = data['recordset'] as IapAircraftTaskVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.type = '[Blank Line]';
          }
          this.taskList.push(f);
        });
        this.gridTask.gridOptions.rowData = this.taskList;
        this.add();
        this.taskUpdateEvent.emit();
      }
    });
  }

  cancel() {
    if ( this.iapAircraftTaskVo !== undefined && this.iapAircraftTaskVo.id > 0 ) {
      this.iapAircraftTaskVo = Object.assign({}, this.selectedAircraftTaskVo);
      setTimeout(() => {
        this.resetForm();
      });
    } else {
      this.initAircraftTaskVo();
      setTimeout(() => {
        this.resetForm();
      });
    }
  }

  delete() {
    if ( this.selectedAircraftTaskVo === null || this.selectedAircraftTaskVo.id < 1 ) {
      this.showPrompt('', 'Delete Task', 'Please select a Task to delete.' , 'Ok', '', '');
    } else {
      this.iapPlanService.deleteIapForm220Task(this.selectedAircraftTaskVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_FORM_220_TASK' ) {
          this.gridTask.clearSelected();
          this.add();
          this.taskList = [];
          const vos = data['recordset'] as IapAircraftTaskVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.type = '[Blank Line]';
            }
            this.taskList.push(f);
          });

          this.gridTask.gridOptions.rowData = this.taskList;

          this.taskUpdateEvent.emit();
        }
      });
    }
  }

  reorder() {
    if ( this.taskList.length < 2) {
      this.showPrompt('', 'Re-Order Task Missions', 'There must be at least 2 Task Missions to Re-Order.' , 'Ok', '', '');
    } else {
      this.ics220ReorderOpenEvent.emit('Tasks');
    }
  }

  saveNewOrder(newVos) {
    this.iapPlanService.saveIapForm220TaskPositions(this.iapForm220Vo.id, newVos)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_220_TASK_POSITIONS' ) {
          this.gridTask.clearSelected();
          this.taskList = [];
          const vos = data['recordset'] as IapAircraftTaskVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.type = '[Blank Line]';
            }
            this.taskList.push(f);
          });
          this.gridTask.gridOptions.rowData = this.taskList;
          this.add();
          this.taskUpdateEvent.emit();
          }
      });
  }

  showPrompt(mode, title, msg, btn1, btn2, btn3) {
    this.icsForm220TaskPromptModal.reset();
    this.icsForm220TaskPromptModal.promptMode = mode;
    this.icsForm220TaskPromptModal.promptTitle = title;
    this.icsForm220TaskPromptModal.promptMessage1 = msg;
    this.icsForm220TaskPromptModal.button1Label = btn1;
    this.icsForm220TaskPromptModal.button2Label = btn2;
    this.icsForm220TaskPromptModal.button3Label = btn3;
    this.icsForm220TaskPromptModal.openModal();
  }

  promptActionResult(evt: any) {
    this.icsForm220TaskPromptModal.closeModal();
    if ( this.icsForm220TaskPromptModal.promptMode === 'AddBlankLine') {
      console.log('evt ' + evt);
      this.addBlankLine(evt);
     }
  }

}
