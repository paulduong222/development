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

@Component({
  selector: 'app-ics220-helicopters',
  templateUrl: './ics220-helicopters.component.html',
  styleUrls: ['./ics220-helicopters.component.css']
})
export class Ics220HelicoptersComponent implements OnInit, AfterViewInit {
  @Output() helicopterUpdateEvent = new EventEmitter();
  @Output() ics220ReorderOpenEvent = new EventEmitter();
  @ViewChild('icsForm220HeliPromptModal') icsForm220HeliPromptModal: PromptModalComponent;
  @ViewChild('gridHelicopter') gridHelicopter: EisGridComponent;

  splitAreaTopSize = 50;
  splitAreaBottomSize = 50;


  iapForm220Vo = <IapForm220Vo>{};
  selectedAircraftVo = null;
  iapAircraftVo = <IapAircraftVo>{};

  iapFormHelper = new IapFormHelper();

  isFormLocked = false;
  compForm: FormGroup;


  // grid vars
  helicopterList = [];
  gridColumnDefs = [
    {headerName: '', field: 'positionNum', width: 40, pinned: 'left', sort: 'asc'},
    {headerName: 'FAA #', field: 'faaNbr', width: 120, pinned: 'left'},
    {headerName: 'Category/Kind/Type', field: 'type', width: 150},
    {headerName: 'Make/Model', field: 'makeModel', width: 140},
    {headerName: 'Base', field: 'base', width: 120},
    {headerName: 'Available', field: 'available', width: 120},
    {headerName: 'Start', field: 'startTime', width: 120},
    {headerName: 'Remarks', field: 'remarks', width: 120},
  ];

  constructor(private formBuilder: FormBuilder,
                private notifyService: NotificationService,
                private iapPlanService: IapPlanService) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      faaNbr: new FormControl({value: '', disabled: false})
      , type: new FormControl({value: '', disabled: false})
      , makeModel: new FormControl({value: '', disabled: false})
      , base: new FormControl({value: '', disabled: false})
      , available: new FormControl({value: '', disabled: false})
      , startTime: new FormControl({value: '', disabled: false})
      , remarks: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  initAircraftVo() {
    this.iapAircraftVo = this.iapFormHelper.initNewIapAircraftVo(this.iapForm220Vo.id, 'HELI');
  }

  reloadPage(vo: IapForm220Vo) {
    this.gridHelicopter.clearSelected();
    this.helicopterList = [];
    this.iapForm220Vo = Object.assign({}, vo);
    vo.iapHelicopterVos.forEach(f => {
      if ( f.isBlankLine === true ) {
        f.faaNbr = '[Blank Line]';
      }
      this.helicopterList.push(f);
    });

    this.isFormLocked = this.iapForm220Vo.isFormLocked;
    this.initAircraftVo();
    this.resetForm();
  }

  resetForm() {
    // reset Form
    setTimeout(() => {

      this.compForm.setValue({
        faaNbr: (this.iapAircraftVo.isBlankLine === true ? '' : this.iapAircraftVo.faaNbr),
        type: this.iapAircraftVo.type,
        makeModel: this.iapAircraftVo.makeModel,
        base: this.iapAircraftVo.base,
        available: this.iapAircraftVo.available,
        startTime: this.iapAircraftVo.startTime,
        remarks: this.iapAircraftVo.remarks,
      });
    });

    setTimeout(() => {
      this.enableDisablePage();
    });

  }

  enableDisablePage() {
    const controlList = ['faaNbr', 'type', 'makeModel', 'base', 'available'
      , 'startTime', 'remarks'];

    if ( this.isFormLocked === true ) {
      controlList.forEach(name => {
        this.compForm.controls[name].disable();
      });
    } else {
      if ( this.iapAircraftVo.isBlankLine === true ) {
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

  onSelectHelicopterRow(row) {
    if ( row !== undefined && row !== null && row.id > 0 ) {
      this.selectedAircraftVo = Object.assign({}, row as IapAircraftVo);
      this.iapAircraftVo = Object.assign({}, row as IapAircraftVo);
      this.resetForm();
    } else {
      this.selectedAircraftVo = null;
    }
  }

  add() {
    this.gridHelicopter.clearSelected();
    this.selectedAircraftVo = null;
    this.initAircraftVo();

    setTimeout(() => {
      this.resetForm();
    });
  }

  preAddBlankLine() {
    if ( this.selectedAircraftVo === null || this.selectedAircraftVo.id === 0) {
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
    let vo = this.iapFormHelper.initNewIapAircraftVo(this.iapForm220Vo.id, 'HELI');

    vo.isBlankLine = true;
    if (location === 'BOTTOM') {
      vo.positionNum = ( this.helicopterList.length + 1 );
    } else {
      const curPosition = this.selectedAircraftVo.positionNum;

      if ( location === 'ABOVE' ) {
        vo.positionNum = curPosition;
      } else {
        // BELOW
        vo.positionNum = (curPosition + 1);
      }
    }

    this.iapPlanService.saveIapForm220Helicopter(this.iapForm220Vo.id, vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_220_HELICOPTER' ) {
        this.helicopterList = [];
        const vos = data['recordset'] as IapAircraftVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.faaNbr = '[Blank Line]';
          }
          this.helicopterList.push(f);
        });
        this.gridHelicopter.gridOptions.rowData = this.helicopterList;
        this.add();
        this.helicopterUpdateEvent.emit();
      }
    });

  }

  save() {
    const isNew = ( this.iapAircraftVo.id > 0 ? false : true );

    if ( isNew === false && this.iapAircraftVo.isBlankLine === true ) {
      return;
    }

    this.iapAircraftVo.faaNbr = this.compForm.get('faaNbr').value;
    this.iapAircraftVo.type = this.compForm.get('type').value;
    this.iapAircraftVo.makeModel = this.compForm.get('makeModel').value;
    this.iapAircraftVo.base = this.compForm.get('base').value;
    this.iapAircraftVo.available = this.compForm.get('available').value;
    this.iapAircraftVo.startTime = this.compForm.get('startTime').value;
    this.iapAircraftVo.remarks = this.compForm.get('remarks').value;

    if ( isNew === true ) {
      this.iapAircraftVo.positionNum = (this.helicopterList.length + 1 );
    }

    this.iapPlanService.saveIapForm220Helicopter(this.iapForm220Vo.id, this.iapAircraftVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_220_HELICOPTER' ) {
        this.helicopterList = [];
        const vos = data['recordset'] as IapAircraftVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.faaNbr = '[Blank Line]';
          }
          this.helicopterList.push(f);
        });
        this.gridHelicopter.gridOptions.rowData = this.helicopterList;
        this.add();
        this.helicopterUpdateEvent.emit();
      }
    });
  }

  cancel() {
    if ( this.iapAircraftVo !== undefined && this.iapAircraftVo.id > 0 ) {
      this.iapAircraftVo = Object.assign({}, this.selectedAircraftVo);
      setTimeout(() => {
        this.resetForm();
      });
    } else {
      this.initAircraftVo();
      setTimeout(() => {
        this.resetForm();
      });
    }
  }

  delete() {
    if ( this.selectedAircraftVo === null || this.selectedAircraftVo.id < 1 ) {
      this.showPrompt('', 'Delete Helicopter', 'Please select a Helicopter to delete.' , 'Ok', '', '');
    } else {
      this.iapPlanService.deleteIapForm220Helicopter(this.selectedAircraftVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_FORM_220_HELICOPTER' ) {
          this.gridHelicopter.clearSelected();
          this.add();
          this.helicopterList = [];
          const vos = data['recordset'] as IapAircraftVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.faaNbr = '[Blank Line]';
            }
            this.helicopterList.push(f);
          });

          this.gridHelicopter.gridOptions.rowData = this.helicopterList;

          this.helicopterUpdateEvent.emit();
        }
      });
    }
  }

  reorder() {
    if ( this.helicopterList.length < 2) {
      this.showPrompt('', 'Re-Order Helicopters', 'There must be at least 2 Helicopters to Re-Order.' , 'Ok', '', '');
    } else {
      this.ics220ReorderOpenEvent.emit('Helicopters');
    }
  }

  saveNewOrder(newVos) {
    this.iapPlanService.saveIapForm220HelicopterPositions(this.iapForm220Vo.id, newVos)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_220_AIRCRAFT_POSITIONS' ) {
          this.gridHelicopter.clearSelected();
          this.helicopterList = [];
          const vos = data['recordset'] as IapAircraftVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.faaNbr = '[Blank Line]';
            }
            this.helicopterList.push(f);
          });
          this.gridHelicopter.gridOptions.rowData = this.helicopterList;
          this.add();
          this.helicopterUpdateEvent.emit();
          }
      });
  }

  showPrompt(mode, title, msg, btn1, btn2, btn3) {
    this.icsForm220HeliPromptModal.reset();
    this.icsForm220HeliPromptModal.promptMode = mode;
    this.icsForm220HeliPromptModal.promptTitle = title;
    this.icsForm220HeliPromptModal.promptMessage1 = msg;
    this.icsForm220HeliPromptModal.button1Label = btn1;
    this.icsForm220HeliPromptModal.button2Label = btn2;
    this.icsForm220HeliPromptModal.button3Label = btn3;
    this.icsForm220HeliPromptModal.openModal();
  }

  promptActionResult(evt: any) {
    this.icsForm220HeliPromptModal.closeModal();
    if ( this.icsForm220HeliPromptModal.promptMode === 'AddBlankLine') {
      this.addBlankLine(evt);
     }
  }

}
