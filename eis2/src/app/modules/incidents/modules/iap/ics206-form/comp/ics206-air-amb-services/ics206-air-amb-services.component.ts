import { Component, OnInit, AfterViewInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { IapForm206Vo } from 'src/app/data/iap-form206-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IapMedicalAidVo } from 'src/app/data/iap-medical-aid-vo';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { IapFormHelper } from '../../../helpers/iap-form-helper';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { NotificationService } from 'src/app/service/notification-service';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { PhonePipe } from 'src/app/pipes/phonepipe/phone.pipe';

@Component({
  selector: 'app-ics206-air-amb-services',
  templateUrl: './ics206-air-amb-services.component.html',
  styleUrls: ['./ics206-air-amb-services.component.css']
})
export class Ics206AirAmbServicesComponent implements OnInit, AfterViewInit {
  @Output() ics206AirAmbulanceUpdateEvent = new EventEmitter();
  @Output() ics206ReorderOpenEvent = new EventEmitter();
  @ViewChild('icsForm206AirAmbPromptModal') icsForm206AirAmbPromptModal: PromptModalComponent;
  @ViewChild('gridAirAmbulance') gridAirAmbulance: EisGridComponent;
  phonePipe = new PhonePipe();

  iapForm206Vo = <IapForm206Vo>{};
  selectedMedicalAidVo = null;
  iapMedicalAidVo = <IapMedicalAidVo>{};

  iapFormHelper = new IapFormHelper();

  isFormLocked = false;
  compForm: FormGroup;

  // grid vars
  airAmbulanceList = []; // as IapMedicalAidVo[];
  gridColumnDefs = [
    {headerName: '', field: 'positionNum', width: 40, pinned: 'left', sort: 'asc'},
    {headerName: 'Name', field: 'name', width: 300, pinned: 'left'},
    {headerName: 'Phone', field: 'phone', width: 120},
    {headerName: 'Type of Aircraft', field: 'airType', width: 250},
    {headerName: 'Capability', field: 'capability', width: 250},
  ];

  constructor(private formBuilder: FormBuilder,
                private notifyService: NotificationService,
                private iapPlanService: IapPlanService) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      name: new FormControl({value: '', disabled: false})
      , airtype: new FormControl({value: '', disabled: false})
      , capability: new FormControl({value: '', disabled: false})
      , phone: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  initMedicalAidVo() {
    this.iapMedicalAidVo = this.iapFormHelper.initNewIapMedicalAidVo(this.iapForm206Vo.id);
  }

  reloadPage(vo: IapForm206Vo) {
    this.gridAirAmbulance.clearSelected();
    this.airAmbulanceList = [];
    this.iapForm206Vo = Object.assign({}, vo);
    vo.iapAirAmbulanceVos.forEach(f => {
      if ( f.isBlankLine === true ) {
        f.name = '[Blank Line]';
      }
      if ( f.phone !== undefined && f.phone !== null) {
        const p = this.phonePipe.transform(f.phone);
        f.phone = p;
      }
      this.airAmbulanceList.push(f);
    });

    this.isFormLocked = this.iapForm206Vo.isFormLocked;
    this.initMedicalAidVo();
    this.resetForm();
  }

  resetForm() {
    // reset Form
    setTimeout(() => {

      this.compForm.setValue({
        name: (this.iapMedicalAidVo.isBlankLine === true ? '' : this.iapMedicalAidVo.name),
        airtype: this.iapMedicalAidVo.airType,
        capability: this.iapMedicalAidVo.capability,
        phone: this.iapMedicalAidVo.phone,
      });
    });

    setTimeout(() => {
      this.enableDisablePage();
    });

  }

  enableDisablePage() {
    const controlList = ['name', 'airtype', 'capability', 'phone'];

    if ( this.isFormLocked === true ) {
      controlList.forEach(name => {
        this.compForm.controls[name].disable();
      });
    } else {
      if ( this.iapMedicalAidVo.isBlankLine === true ) {
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

  getiapForm206Vo() {
    return this.iapForm206Vo;
  }

  onSelectRow(row) {
    if ( row !== undefined && row !== null && row.id > 0 ) {
      this.selectedMedicalAidVo = Object.assign({}, row as IapMedicalAidVo);
      this.iapMedicalAidVo = Object.assign({}, row as IapMedicalAidVo);
      this.resetForm();
    } else {
      this.selectedMedicalAidVo = null;
    }
  }

  add() {
    this.gridAirAmbulance.clearSelected();
    this.selectedMedicalAidVo = null;
    this.initMedicalAidVo();

    setTimeout(() => {
      this.resetForm();
    });
  }

  preAddBlankLine() {
    if ( this.selectedMedicalAidVo === null || this.selectedMedicalAidVo.id === 0) {
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
    let vo = this.iapFormHelper.initNewIapMedicalAidVo(this.iapForm206Vo.id);
    vo.type = 'AIRAMBULANCE';

    vo.isBlankLine = true;
    if (location === 'BOTTOM') {
      vo.positionNum = ( this.airAmbulanceList.length + 1 );
    } else {
      const curPosition = this.selectedMedicalAidVo.positionNum;

      if ( location === 'ABOVE' ) {
        vo.positionNum = curPosition;
      } else {
        // BELOW
        vo.positionNum = (curPosition + 1);
      }
    }

    this.iapPlanService.saveIapForm206AirAmbulance(this.iapForm206Vo.id, vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_AIR_AMBULANCE' ) {
        this.airAmbulanceList = [];
        const vos = data['recordset'] as IapMedicalAidVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.name = '[Blank Line]';
          }
          if ( f.phone !== undefined && f.phone !== null) {
            const p = this.phonePipe.transform(f.phone);
            f.phone = p;
          }
         this.airAmbulanceList.push(f);
        });
        this.gridAirAmbulance.gridOptions.rowData = this.airAmbulanceList;
        this.add();
        this.ics206AirAmbulanceUpdateEvent.emit();
      }
    });
  }

  save() {
    if ( this.iapMedicalAidVo.isBlankLine === true ) {
      return;
    }
    const isNew = ( this.iapMedicalAidVo.id > 0 ? false : true );
    this.iapMedicalAidVo.type = 'AIRAMBULANCE';
    this.iapMedicalAidVo.iapForm206Id = this.iapForm206Vo.id;
    this.iapMedicalAidVo.name = this.compForm.get('name').value;
    this.iapMedicalAidVo.airType = this.compForm.get('airtype').value;
    this.iapMedicalAidVo.capability = this.compForm.get('capability').value;
    this.iapMedicalAidVo.phone = this.compForm.get('phone').value;


    if ( this.iapMedicalAidVo.id > 0 ) {

    } else {
      this.iapMedicalAidVo.positionNum = (this.airAmbulanceList.length + 1);
    }

    this.iapPlanService.saveIapForm206AirAmbulance(this.iapForm206Vo.id, this.iapMedicalAidVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_AIR_AMBULANCE' ) {
        this.gridAirAmbulance.clearSelected();
        this.airAmbulanceList = [];
        const vos = data['recordset'] as IapMedicalAidVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.name = '[Blank Line]';
          }
          if ( f.phone !== undefined && f.phone !== null) {
            const p = this.phonePipe.transform(f.phone);
            f.phone = p;
          }
          this.airAmbulanceList.push(f);
        });
        this.gridAirAmbulance.gridOptions.rowData = this.airAmbulanceList;
        this.add();
        this.ics206AirAmbulanceUpdateEvent.emit();
      }
    });
  }

  cancel() {
    if ( this.iapMedicalAidVo !== undefined && this.iapMedicalAidVo.id > 0 ) {
      this.iapMedicalAidVo = Object.assign({}, this.selectedMedicalAidVo);
      setTimeout(() => {
        this.resetForm();
      });
    } else {
      this.initMedicalAidVo();
      setTimeout(() => {
        this.resetForm();
      });
    }
  }

  delete() {
    if ( this.selectedMedicalAidVo === null || this.selectedMedicalAidVo.id < 1 ) {
      this.showPrompt('', 'Delete Air Ambulance', 'Please select an Air Ambulance to delete.' , 'Ok', '', '');
    } else {
      this.iapPlanService.deleteIapForm206AirAmbulance(this.iapForm206Vo.id, this.selectedMedicalAidVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_FORM_206_AIR_AMBULANCE' ) {
          this.gridAirAmbulance.clearSelected();
          this.airAmbulanceList = [];
          const vos = data['recordset'] as IapMedicalAidVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.name = '[Blank Line]';
            }
            if ( f.phone !== undefined && f.phone !== null) {
              const p = this.phonePipe.transform(f.phone);
              f.phone = p;
            }
            this.airAmbulanceList.push(f);
          });
          this.gridAirAmbulance.gridOptions.rowData = this.airAmbulanceList;
          this.add();
          this.ics206AirAmbulanceUpdateEvent.emit();
          }
      });
    }
  }

  reorder() {
    if ( this.airAmbulanceList.length < 2) {
      this.showPrompt('', 'Re-Order Air Ambulances', 'There must be at least 2 Air Ambulances to Re-Order.' , 'Ok', '', '');
    } else {
      this.ics206ReorderOpenEvent.emit('AirAmbulance');
    }
  }

  saveNewOrder(newVos) {
    this.iapPlanService.saveIapForm206AirAmbulancePositions(this.iapForm206Vo.id, newVos)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_AIR_AMBULANCE_POSITIONS' ) {
          this.gridAirAmbulance.clearSelected();
          this.airAmbulanceList = [];
          const vos = data['recordset'] as IapMedicalAidVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.name = '[Blank Line]';
            }
            this.airAmbulanceList.push(f);
          });
          this.gridAirAmbulance.gridOptions.rowData = this.airAmbulanceList;
          this.add();
          this.ics206AirAmbulanceUpdateEvent.emit();
          }
      });
  }

  showPrompt(mode, title, msg, btn1, btn2, btn3) {
    this.icsForm206AirAmbPromptModal.reset();
    this.icsForm206AirAmbPromptModal.promptMode = mode;
    this.icsForm206AirAmbPromptModal.promptTitle = title;
    this.icsForm206AirAmbPromptModal.promptMessage1 = msg;
    this.icsForm206AirAmbPromptModal.button1Label = btn1;
    this.icsForm206AirAmbPromptModal.button2Label = btn2;
    this.icsForm206AirAmbPromptModal.button3Label = btn3;
    this.icsForm206AirAmbPromptModal.openModal();
  }

  promptActionResult(evt: any) {
    this.icsForm206AirAmbPromptModal.closeModal();
    if ( this.icsForm206AirAmbPromptModal.promptMode === 'AddBlankLine') {
      this.addBlankLine(evt);
     }
  }

}
