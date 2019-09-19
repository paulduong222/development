import { Component, OnInit, AfterViewInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { IapForm206Vo } from 'src/app/data/iap-form206-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IapAreaLocationCapabilityVo } from 'src/app/data/iap-area-location-capability-vo';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { IapFormHelper } from '../../../helpers/iap-form-helper';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { NotificationService } from 'src/app/service/notification-service';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
 
@Component({
  selector: 'app-ics206-area-loc-cap',
  templateUrl: './ics206-area-loc-cap.component.html',
  styleUrls: ['./ics206-area-loc-cap.component.css']
})
export class Ics206AreaLocCapComponent implements OnInit, AfterViewInit {
  @Output() ics206AlcUpdateEvent = new EventEmitter();
  @Output() ics206ReorderOpenEvent = new EventEmitter();
  @ViewChild('icsForm206AlcPromptModal') icsForm206AlcPromptModal: PromptModalComponent;
  @ViewChild('gridAlc') gridAlc: EisGridComponent;

  iapForm206Vo = <IapForm206Vo>{};
  selectedAreaLocationCapabilityVo = null;
  iapAreaLocationCapabilityVo = <IapAreaLocationCapabilityVo>{};

  iapFormHelper = new IapFormHelper();

  isFormLocked = false;
  compForm: FormGroup;

  // grid vars
  alcList = []; // as iapAreaLocationCapabilityVo[];
  gridColumnDefs = [
    {headerName: '', field: 'positionNum', width: 40, pinned: 'left', sort: 'asc'},
    {headerName: 'Branch', field: 'branchName', width: 200, pinned: 'left'},
    {headerName: 'Division/Group', field: 'divisionName', width: 140},
    {headerName: 'EMS Responders', field: 'emsResponders', width: 140},
    {headerName: 'Capability', field: 'capability', width: 140},
    {headerName: 'Available Equipment', field: 'availEquipment', width: 120},
    {headerName: 'Emergency Channel', field: 'emergencyChannel', width: 70},
    {headerName: 'Ambulance ETA (Air)', field: 'ambAirEta', width: 90},
    {headerName: 'Ambulance ETA (Ground)', field: 'ambGroundEta', width: 120},
    {headerName: 'Approved Helispot Latitude', field: 'latitude', width: 150},
    {headerName: 'Approved Helispot Longitude', field: 'longitdue', width: 150},
  ];

  constructor(private formBuilder: FormBuilder,
                private notifyService: NotificationService,
                private iapPlanService: IapPlanService) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      branchName: new FormControl({value: '', disabled: false})
      , divisionName: new FormControl({value: '', disabled: false})
      , emsResponders: new FormControl({value: '', disabled: false})
      , capability: new FormControl({value: '', disabled: false})
      , availEquipment: new FormControl({value: '', disabled: false})
      , emergencyChannel: new FormControl({value: '', disabled: false})
      , approvedHelispot: new FormControl({value: '', disabled: false})
      , ambAirEta: new FormControl({value: '', disabled: false})
      , ambGroundEta: new FormControl({value: '', disabled: false})
      , latitude: new FormControl({value: '', disabled: false})
      , longitude: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  initAreaLocationCapabilityVo() {
    this.iapAreaLocationCapabilityVo = this.iapFormHelper.initNewIapAreaLocationCapabilityVo(this.iapForm206Vo.id);
  }

  reloadPage(vo: IapForm206Vo) {
    this.alcList = [];
    this.gridAlc.clearSelected();
    this.iapForm206Vo = Object.assign({}, vo);
    vo.iapAreaLocationCapabilityVos.forEach(f => {
      if ( f.isBlankLine === true ) {
        f.branchName = '[Blank Line]';
      }
      this.alcList.push(f);
    });

    this.isFormLocked = this.iapForm206Vo.isFormLocked;
    this.initAreaLocationCapabilityVo();
    this.resetForm();
  }

  resetForm() {
    // reset Form
    setTimeout(() => {

      this.compForm.setValue({
        branchName: (this.iapAreaLocationCapabilityVo.isBlankLine === true ? '' : this.iapAreaLocationCapabilityVo.branchName),
        divisionName: this.iapAreaLocationCapabilityVo.divisionName,
        emsResponders: this.iapAreaLocationCapabilityVo.emsResponders,
        capability: this.iapAreaLocationCapabilityVo.capability,
        availEquipment: this.iapAreaLocationCapabilityVo.availEquipment,
        approvedHelispot: this.iapAreaLocationCapabilityVo.approvedHelispot,
        emergencyChannel: this.iapAreaLocationCapabilityVo.emergencyChannel,
        ambAirEta: this.iapAreaLocationCapabilityVo.ambAirEta,
        ambGroundEta: this.iapAreaLocationCapabilityVo.ambGroundEta,
        latitude: this.iapAreaLocationCapabilityVo.latitude,
        longitude: this.iapAreaLocationCapabilityVo.longitude,
      });
    });
 
    setTimeout(() => {
      this.enableDisablePage();
    });

  }

  enableDisablePage() {
    const controlList = ['branchName', 'divisionName', 'emsResponders', 'capability', 'approvedHelispot'
      , 'availEquipment', 'emergencyChannel', 'latitude', 'longitude', 'ambAirEta', 'ambGroundEta'];


    if ( this.isFormLocked === true ) {
      controlList.forEach(name => {
        this.compForm.controls[name].disable();
      });
    } else {
      if ( this.iapAreaLocationCapabilityVo.isBlankLine === true ) {
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
      this.selectedAreaLocationCapabilityVo = Object.assign({}, row as IapAreaLocationCapabilityVo);
      this.iapAreaLocationCapabilityVo = Object.assign({}, row as IapAreaLocationCapabilityVo);
      this.resetForm();
    } else {
      this.selectedAreaLocationCapabilityVo = null;
    }
  }

  add() {
    this.gridAlc.clearSelected();
    this.selectedAreaLocationCapabilityVo = null;
    this.initAreaLocationCapabilityVo();

    setTimeout(() => {
      this.resetForm();
    });
  }

  preAddBlankLine() {
    if ( this.selectedAreaLocationCapabilityVo === null || this.selectedAreaLocationCapabilityVo.id === 0) {
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
    let vo = this.iapFormHelper.initNewIapAreaLocationCapabilityVo(this.iapForm206Vo.id);

    vo.isBlankLine = true;
    if (location === 'BOTTOM') {
      vo.positionNum = ( this.alcList.length + 1 );
    } else {
      const curPosition = this.selectedAreaLocationCapabilityVo.positionNum;

      if ( location === 'ABOVE' ) {
        vo.positionNum = curPosition;
      } else {
        // BELOW
        vo.positionNum = (curPosition + 1);
      }
    }

    this.iapPlanService.saveIapForm206AreaLocCap(this.iapForm206Vo.id, vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_AREA_LOC_CAP' ) {
        this.alcList = [];
        const vos = data['recordset'] as IapAreaLocationCapabilityVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.branchName = '[Blank Line]';
          }
          this.alcList.push(f);
        });
        this.gridAlc.gridOptions.rowData = this.alcList;
        this.add();
        this.ics206AlcUpdateEvent.emit();
      }
    });
  }

  save() {
    if ( this.iapAreaLocationCapabilityVo.isBlankLine === true ) {
      return;
    }
    const isNew = ( this.iapAreaLocationCapabilityVo.id > 0 ? false : true );
    this.iapAreaLocationCapabilityVo.iapForm206Id = this.iapForm206Vo.id;
    this.iapAreaLocationCapabilityVo.branchName = this.compForm.get('branchName').value;
    this.iapAreaLocationCapabilityVo.divisionName = this.compForm.get('divisionName').value;
    this.iapAreaLocationCapabilityVo.emsResponders = this.compForm.get('emsResponders').value;
    this.iapAreaLocationCapabilityVo.capability = this.compForm.get('capability').value;
    this.iapAreaLocationCapabilityVo.emergencyChannel = this.compForm.get('emergencyChannel').value;
    this.iapAreaLocationCapabilityVo.availEquipment = this.compForm.get('availEquipment').value;
    this.iapAreaLocationCapabilityVo.ambAirEta = this.compForm.get('ambAirEta').value;
    this.iapAreaLocationCapabilityVo.ambGroundEta = this.compForm.get('ambGroundEta').value;
    this.iapAreaLocationCapabilityVo.latitude = this.compForm.get('latitude').value;
    this.iapAreaLocationCapabilityVo.longitude = this.compForm.get('longitude').value;


    if ( this.iapAreaLocationCapabilityVo.id > 0 ) {

    } else {
      this.iapAreaLocationCapabilityVo.positionNum = (this.alcList.length + 1);
    }

    this.iapPlanService.saveIapForm206AreaLocCap(this.iapForm206Vo.id, this.iapAreaLocationCapabilityVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_AREA_LOC_CAP' ) {
        this.gridAlc.clearSelected();
        this.alcList = [];
        const vos = data['recordset'] as IapAreaLocationCapabilityVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.branchName = '[Blank Line]';
          }
          this.alcList.push(f);
        });

        this.gridAlc.gridOptions.rowData = this.alcList;
        this.add();
        this.ics206AlcUpdateEvent.emit();
      }
    });
  }

  cancel() {
    if ( this.iapAreaLocationCapabilityVo !== undefined && this.iapAreaLocationCapabilityVo.id > 0 ) {
      this.iapAreaLocationCapabilityVo = Object.assign({}, this.selectedAreaLocationCapabilityVo);
      setTimeout(() => {
        this.resetForm();
      });
    } else {
      this.initAreaLocationCapabilityVo();
      setTimeout(() => {
        this.resetForm();
      });
    }
  }

  delete() {
    if ( this.selectedAreaLocationCapabilityVo === null || this.selectedAreaLocationCapabilityVo.id < 1 ) {
      this.showPrompt('', 'Delete Area Location', 'Please select an Area Location to delete.' , 'Ok', '', '');
    } else {
      this.iapPlanService.deleteIapForm206AreaLocCap(this.iapForm206Vo.id, this.selectedAreaLocationCapabilityVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_FORM_206_AREA_LOC_CAP' ) {
          this.gridAlc.clearSelected();
          this.alcList = [];
          const vos = data['recordset'] as IapAreaLocationCapabilityVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.branchName = '[Blank Line]';
            }
            this.alcList.push(f);
          });
          this.gridAlc.gridOptions.rowData = this.alcList;
          this.add();
          this.ics206AlcUpdateEvent.emit();
          }
      });
    }
  }

  reorder() {
    if ( this.alcList.length < 2) {
      this.showPrompt('', 'Re-Order Area Locations', 'There must be at least 2 Area Locations to Re-Order.' , 'Ok', '', '');
    } else {
      this.ics206ReorderOpenEvent.emit('AreaLocCap');
    }
  }

  saveNewOrder(newVos) {
    this.iapPlanService.saveIapForm206AreaLocCapPositions(this.iapForm206Vo.id, newVos)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_ALC_POSITIONS' ) {
          this.gridAlc.clearSelected();
          this.alcList = [];
          const vos = data['recordset'] as IapAreaLocationCapabilityVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.branchName = '[Blank Line]';
            }
            this.alcList.push(f);
          });
          this.gridAlc.gridOptions.rowData = this.alcList;
          this.add();
          this.ics206AlcUpdateEvent.emit();
          }
      });
  }

  showPrompt(mode, title, msg, btn1, btn2, btn3) {
    this.icsForm206AlcPromptModal.reset();
    this.icsForm206AlcPromptModal.promptMode = mode;
    this.icsForm206AlcPromptModal.promptTitle = title;
    this.icsForm206AlcPromptModal.promptMessage1 = msg;
    this.icsForm206AlcPromptModal.button1Label = btn1;
    this.icsForm206AlcPromptModal.button2Label = btn2;
    this.icsForm206AlcPromptModal.button3Label = btn3;
    this.icsForm206AlcPromptModal.openModal();
  }

  promptActionResult(evt: any) {
    this.icsForm206AlcPromptModal.closeModal();
    if ( this.icsForm206AlcPromptModal.promptMode === 'AddBlankLine') {
      this.addBlankLine(evt);
     }
  }

}
