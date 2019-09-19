import { Component, OnInit, AfterViewInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { IapForm205Vo } from 'src/app/data/iap-form205-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IapFrequencyVo } from 'src/app/data/iap-frequency-vo';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { IapFormHelper } from '../../../helpers/iap-form-helper';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { NotificationService } from 'src/app/service/notification-service';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { IapMasterService } from 'src/app/service/iap-master.service';
import { IapMasterFrequencyVo } from 'src/app/data/iap-master-frequency-vo';
import { MasterFrequencyWindowComponent } from '../../../modals/master-frequency-window/master-frequency-window.component';
 
@Component({
  selector: 'app-ics205-bas-rad-channel-use',
  templateUrl: './ics205-bas-rad-channel-use.component.html',
  styleUrls: ['./ics205-bas-rad-channel-use.component.css']
})
export class Ics205BasRadChannelUseComponent implements OnInit, AfterViewInit {
  @Input() incidentId;
  @Input() incidentGroupId;
  @Output() frequenciesUpdateEvent = new EventEmitter();
  @Output() ics205ReorderOpenEvent = new EventEmitter();
  @ViewChild('icsForm205PromptModal') icsForm205PromptModal: PromptModalComponent;
  @ViewChild('ddFunctionType') ddFunctionType: EisDropdownComponent;
  @ViewChild('ddModeType') ddModeType: EisDropdownComponent;
  @ViewChild('gridFrequency') gridFrequency: EisGridComponent;
  @ViewChild('masterFrequencyWindow') masterFrequencyWindow: MasterFrequencyWindowComponent;

  splitAreaTopSize = 50;
  splitAreaBottomSize = 50;


  iapForm205Vo = <IapForm205Vo>{};
  selectedFrequencyVo = null;
  iapFrequencyVo = <IapFrequencyVo>{};

  iapFormHelper = new IapFormHelper();

  isFormLocked = false;
  compForm: FormGroup;

  // Dropdown lists
  functionTypeData: DropdownData[] = [
    {id: 0, code: 'COMMAND', desc: ''}
    , {id: 1, code: 'TACTICAL', desc: ''}
    , {id: 2, code: 'LOGISTICS', desc: ''}
    , {id: 3, code: 'AIR TO GROUND', desc: ''}
    , {id: 4, code: 'AIR GUARD', desc: ''}
  ];

  modeTypeData: DropdownData[] = [
    {id: 0, code: 'A', desc: ''}
    , {id: 1, code: 'D', desc: ''}
    , {id: 2, code: 'M', desc: ''}
  ];

  // selected object holders for dropdown
  functionTypeDropdownData: DropdownData;
  modeTypeDropdownData: DropdownData;

  // grid vars
  frequencyList = []; // as IapFrequencyVo[];
  gridColumnDefs = [
    {headerName: '', field: 'positionNum', width: 40, pinned: 'left', sort: 'asc'},
    {headerName: 'Zone Group', field: 'zoneGroup', width: 120, pinned: 'left'},
    {headerName: 'Channel #', field: 'channel', width: 120},
    {headerName: 'Channel Name', field: 'channelName', width: 140},
    {headerName: 'Function', field: 'sfunction', width: 120},
    {headerName: 'Assignment', field: 'assignment', width: 120},
    {headerName: 'RX Freq N or W', field: 'frequencyRx', width: 140},
    {headerName: 'RX Tone/NAC', field: 'toneRx', width: 120},
    {headerName: 'TX Freq N or W', field: 'frequencyTx', width: 140},
    {headerName: 'TX Tone/NAC', field: 'toneTx', width: 120},
    {headerName: 'Mode (A, D, or M)', field: 'modeType', width: 140},
    {headerName: 'Remarks', field: 'remarks', width: 120},
  ];

  constructor(private formBuilder: FormBuilder,
                private notifyService: NotificationService,
                private iapMasterService: IapMasterService,
                private iapPlanService: IapPlanService) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      zoneGroup: new FormControl({value: '', disabled: false})
      , sfunction: {}
      , modeType: {}
      , radioType: new FormControl({value: '', disabled: false})
      , channel: new FormControl({value: '', disabled: false})
      , frequencyRx: new FormControl({value: '', disabled: false})
      , toneRx: new FormControl({value: '', disabled: false})
      , frequencyTx: new FormControl({value: '', disabled: false})
      , toneTx: new FormControl({value: '', disabled: false})
      , assignment: new FormControl({value: '', disabled: false})
      , remarks: new FormControl({value: '', disabled: false})
      , preparedDate: new FormControl({value: {}, disabled: false})
      , channelName: new FormControl({value: '', disabled: false})
      , masterFreqId: new FormControl({value: 0, disabled: false})
      , isBlankLine: new FormControl({value: false, disabled: false})
      , positionNum: new FormControl({value: 0, disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  initFrequencyVo() {
    this.iapFrequencyVo = this.iapFormHelper.initNew205FrequencyVo(this.iapForm205Vo.id);
  }

  reloadPage(vo: IapForm205Vo) {
    this.gridFrequency.clearSelected();
    this.frequencyList = [];
    this.iapForm205Vo = Object.assign({}, vo);
    vo.iapFrequencieVos.forEach(f => {
      if ( f.isBlankLine === true ) {
        f.zoneGroup = '[Blank Line]';
      }
      this.frequencyList.push(f);
    });

    this.isFormLocked = this.iapForm205Vo.isFormLocked;
    this.initFrequencyVo();
    this.resetForm();
  }

  resetForm() {
    // reset Form
    setTimeout(() => {
      this.functionTypeDropdownData = this.ddFunctionType.getDropdownDataObjectById(-1);
      this.modeTypeDropdownData = this.ddModeType.getDropdownDataObjectById(-1);

      this.compForm.setValue({
        zoneGroup: (this.iapFrequencyVo.isBlankLine === true ? '' : this.iapFrequencyVo.zoneGroup),
        radioType: this.iapFrequencyVo.radioType,
        channel: this.iapFrequencyVo.channel,
        sfunction: {},
        frequencyRx: this.iapFrequencyVo.frequencyRx,
        toneRx: this.iapFrequencyVo.toneRx,
        frequencyTx: this.iapFrequencyVo.frequencyTx,
        toneTx: this.iapFrequencyVo.toneTx,
        assignment: this.iapFrequencyVo.assignment,
        remarks: this.iapFrequencyVo.remarks,
        preparedDate: null,
        channelName: this.iapFrequencyVo.channelName,
        modeType: {},
        masterFreqId: this.iapFrequencyVo.masterFreqId,
        isBlankLine: this.iapFrequencyVo.isBlankLine,
        positionNum: this.iapFrequencyVo.positionNum,
      });
    });

    setTimeout(() => {
      this.functionTypeDropdownData = this.ddFunctionType.getDropdownDataObjectByCode(this.iapFrequencyVo.sfunction);
      this.modeTypeDropdownData = this.ddModeType.getDropdownDataObjectByCode(this.iapFrequencyVo.modeType);
      this.enableDisablePage();
    });

  }

  enableDisablePage() {
    const controlList = ['zoneGroup', 'channel', 'channelName', 'frequencyRx'
      , 'toneRx', 'frequencyTx', 'toneTx', 'assignment', 'remarks'];

    this.ddFunctionType.dropdownDisabled = this.isFormLocked;
    this.ddModeType.dropdownDisabled = this.isFormLocked;

    if ( this.isFormLocked === true ) {
      controlList.forEach(name => {
        this.compForm.controls[name].disable();
      });
    } else {
      if ( this.iapFrequencyVo.isBlankLine === true ) {
        controlList.forEach(name => {
          this.compForm.controls[name].disable();
        });
        this.ddFunctionType.dropdownDisabled = true;
        this.ddModeType.dropdownDisabled = true;
     } else {
        controlList.forEach(name => {
          this.compForm.controls[name].enable();
        });
        }
    }
  }

  getIapForm205Vo() {
    return this.iapForm205Vo;
  }

  onSelectFrequencyRow(row) {
    if ( row !== undefined && row !== null && row.id > 0 ) {
      this.selectedFrequencyVo = Object.assign({}, row as IapFrequencyVo);
      this.iapFrequencyVo = Object.assign({}, row as IapFrequencyVo);
      this.resetForm();
    } else {
      this.selectedFrequencyVo = null;
    }
  }

  add() {
    this.gridFrequency.clearSelected();
    this.selectedFrequencyVo = null;
    /** 
     * 
     * Commented out this.initFrequencyVo();
     * Fix for QC #83 item 8: 
     * When editing a record and saving the changes, the system should leave 
     * the fields populated with the selected records information.
     */
    // this.initFrequencyVo();

    setTimeout(() => {
      this.resetForm();
    });
  }

  preAddBlankLine() {
    if ( this.selectedFrequencyVo === null || this.selectedFrequencyVo.id === 0) {
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
    let vo = this.iapFormHelper.initNew205FrequencyVo(this.iapForm205Vo.id);

    vo.isBlankLine = true;
    if (location === 'BOTTOM') {
      vo.positionNum = ( this.frequencyList.length + 1 );
    } else {
      const curPosition = this.selectedFrequencyVo.positionNum;

      if ( location === 'ABOVE' ) {
        vo.positionNum = curPosition;
      } else {
        // BELOW
        vo.positionNum = (curPosition + 1);
      }
    }

    this.iapPlanService.saveIapForm205Frequency(this.iapForm205Vo.id, vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_205_FREQUENCY' ) {
        this.frequencyList = [];
        const vos = data['recordset'] as IapFrequencyVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.zoneGroup = '[Blank Line]';
          }
          this.frequencyList.push(f);
        });
        this.gridFrequency.gridOptions.rowData = this.frequencyList;
        this.add();
        this.frequenciesUpdateEvent.emit();
      }
    });
  }

  save() {
    const isNew = ( this.iapFrequencyVo.id > 0 ? false : true );
    this.iapFrequencyVo.iapForm205Id = this.iapForm205Vo.id;
    this.iapFrequencyVo.zoneGroup = this.compForm.get('zoneGroup').value;
    this.iapFrequencyVo.channel = this.compForm.get('channel').value;
    this.iapFrequencyVo.channelName = this.compForm.get('channelName').value;
    this.iapFrequencyVo.frequencyRx = this.compForm.get('frequencyRx').value;
    this.iapFrequencyVo.toneRx = this.compForm.get('toneRx').value;
    this.iapFrequencyVo.frequencyTx = this.compForm.get('frequencyTx').value;
    this.iapFrequencyVo.toneTx = this.compForm.get('toneTx').value;
    this.iapFrequencyVo.assignment = this.compForm.get('assignment').value;
    this.iapFrequencyVo.remarks = this.compForm.get('remarks').value;
    this.iapFrequencyVo.sfunction = this.ddFunctionType.selectedValue.code;
    this.iapFrequencyVo.modeType = this.ddModeType.selectedValue.code;

    if ( this.iapFrequencyVo.id > 0 ) {

    } else {
      this.iapFrequencyVo.positionNum = (this.frequencyList.length + 1);
    }

    this.iapPlanService.saveIapForm205Frequency(this.iapForm205Vo.id, this.iapFrequencyVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_205_FREQUENCY' ) {
        this.gridFrequency.clearSelected();
        this.frequencyList = [];
        const vos = data['recordset'] as IapFrequencyVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.zoneGroup = '[Blank Line]';
          }
          this.frequencyList.push(f);
        });
        this.gridFrequency.gridOptions.rowData = this.frequencyList;
        this.add();
        this.frequenciesUpdateEvent.emit();
      }
    });
  }

  cancel() {
    if ( this.iapFrequencyVo !== undefined && this.iapFrequencyVo.id > 0 ) {
      this.iapFrequencyVo = Object.assign({}, this.selectedFrequencyVo);
      setTimeout(() => {
        this.resetForm();
      });
    } else {
      this.initFrequencyVo();
      setTimeout(() => {
        this.resetForm();
      });
    }
  }

  delete() {
    if ( this.selectedFrequencyVo === null || this.selectedFrequencyVo.id < 1 ) {
      this.showPrompt('', 'Delete Frequency', 'Please select a frequency to delete.' , 'Ok', '', '');
    } else {
      this.iapPlanService.deleteIapForm205Frequency(this.selectedFrequencyVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_FORM_205_FREQUENCY' ) {
          this.gridFrequency.clearSelected();
          this.frequencyList = [];
          const vos = data['recordset'] as IapFrequencyVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.zoneGroup = '[Blank Line]';
            }
            this.frequencyList.push(f);
          });
          this.gridFrequency.gridOptions.rowData = this.frequencyList;
          this.add();
          this.frequenciesUpdateEvent.emit();
          }
      });
    }
  }

  showPrompt(mode, title, msg, btn1, btn2, btn3) {
    this.icsForm205PromptModal.reset();
    this.icsForm205PromptModal.promptMode = mode;
    this.icsForm205PromptModal.promptTitle = title;
    this.icsForm205PromptModal.promptMessage1 = msg;
    this.icsForm205PromptModal.button1Label = btn1;
    this.icsForm205PromptModal.button2Label = btn2;
    this.icsForm205PromptModal.button3Label = btn3;
    this.icsForm205PromptModal.openModal();
  }

  promptActionResult(evt: any) {
    this.icsForm205PromptModal.closeModal();
    if ( this.icsForm205PromptModal.promptMode === 'AddBlankLine') {
      console.log('evt ' + evt);
      this.addBlankLine(evt);
     }
  }

  reorder() {
    if ( this.frequencyList.length < 2) {
      this.showPrompt('', 'Re-Order Frequencies', 'There must be at least 2 Frequencies to Re-Order.' , 'Ok', '', '');
    } else {
      this.ics205ReorderOpenEvent.emit('Radios');
    }
  }

  saveNewOrder(newVos) {
    this.iapPlanService.saveIapForm205FrequencyPositions(this.iapForm205Vo.id, newVos)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_205_FREQUENCY_POSITIONS' ) {
          this.gridFrequency.clearSelected();
          this.frequencyList = [];
          const vos = data['recordset'] as IapFrequencyVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.zoneGroup = '[Blank Line]';
            }
            this.frequencyList.push(f);
          });
          this.gridFrequency.gridOptions.rowData = this.frequencyList;
          this.add();
          this.frequenciesUpdateEvent.emit();
          }
      });
  }

  verifyFrequencies() {
    this.iapPlanService.verifyIapForm205Frequencies(this.iapForm205Vo.iapPlanId, this.iapForm205Vo.id)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'VERIFY_FREQUENCIES' ) {
        const msg1 = data['courseOfActionVo']['messageVo']['parameters'][0];
        const msg2 = data['courseOfActionVo']['messageVo']['parameters'][1];
        this.showMessage('Verify Frequencies', msg1, msg2, 'Ok');
      }
    });
  }

  showMessage(title, msg1, msg2, btn1) {
    this.icsForm205PromptModal.reset();
    this.icsForm205PromptModal.promptTitle = title;
    this.icsForm205PromptModal.promptMessage1 = msg1;
    this.icsForm205PromptModal.promptMessage2 = msg2;
    this.icsForm205PromptModal.button1Label = btn1;
    this.icsForm205PromptModal.openModal();
  }

  openMasterFrequencies() {
    this.iapMasterService.getMasterFrequencyGrid(this.incidentId, this.incidentGroupId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_MASTER_FREQUENCIES' ) {
        const vos = data['recordset'] as IapMasterFrequencyVo[];
        if ( vos.length === 0) {
          // show message no frequencies to add
          this.showPrompt('', 'Add From Master Frequency List', 'No frequencies exist.', 'Ok', '', '');
        } else {
          this.masterFrequencyWindow.mflList = vos;
          this.masterFrequencyWindow.showModal = true;
          this.masterFrequencyWindow.openModal();
        }
      }
    });
  }

  saveFreqEvent() {
    const vosToAdd = this.masterFrequencyWindow.getSelectedFrequencies() as IapMasterFrequencyVo[];
    let vosToSave = [] as IapFrequencyVo[];
    let nextPosition = this.frequencyList.length;
    vosToAdd.forEach(v => {
      let newVo = this.iapFormHelper.initNew205FrequencyVo(this.iapForm205Vo.id);
      newVo.positionNum = nextPosition + 1;
      newVo.sfunction = v.rfunction;
      newVo.zoneGroup = v.group;
      newVo.channel = v.channel;
      newVo.channelName = v.channelName;
      newVo.assignment = v.assignment;
      newVo.frequencyRx = v.rxFreq;
      newVo.frequencyTx = v.txFreq;
      newVo.toneRx = v.rxTone;
      newVo.toneTx = v.txTone;
      newVo.modeType = v.mode;
      newVo.masterFreqId = v.id;
      vosToSave.push(newVo);
      nextPosition = nextPosition + 1;
    });

    this.iapPlanService.addIapForm205Frequencies(this.iapForm205Vo.id, vosToSave)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'ADD_FORM_205_FREQUENCIES' ) {
        /**
         * Commented out this.masterFrequencyWindow.closeModal()
         * 
         * Fix for QC #83 item 6:
         * 
         * The Master Frequency List window should remain open after the user adds frequencies. 
         * It should only close when the user clicks the Close button.
         */
        // this.masterFrequencyWindow.closeModal();
        this.gridFrequency.clearSelected();
        this.frequencyList = [];
        const vos = data['recordset'] as IapFrequencyVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.zoneGroup = '[Blank Line]';
          }
          this.frequencyList.push(f);
        });
        this.gridFrequency.gridOptions.rowData = this.frequencyList;
        this.add();
        this.frequenciesUpdateEvent.emit();
      }
    });

  }
}
