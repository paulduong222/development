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
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { PhonePipe } from 'src/app/pipes/phonepipe/phone.pipe';

@Component({
  selector: 'app-ics206-amb-services',
  templateUrl: './ics206-amb-services.component.html',
  styleUrls: ['./ics206-amb-services.component.css']
})
export class Ics206AmbServicesComponent implements OnInit, AfterViewInit {
  @Output() ics206AmbulanceUpdateEvent = new EventEmitter();
  @Output() ics206ReorderOpenEvent = new EventEmitter();

  @ViewChild('icsForm206AmbPromptModal') icsForm206AmbPromptModal: PromptModalComponent;
  @ViewChild('ddState') ddState: EisDropdownComponent;
  @ViewChild('gridAmbulance') gridAmbulance: EisGridComponent;
  phonePipe = new PhonePipe();

  // selected object for dropdown
  statesDropdownData = []; // <-- this is collection of dropdownData based on CouuntryCodeSubdivisionVos

  // selected object for dropdown
  selectedStateDropdownData: DropdownData;

  ddStateDisabled = false;
  iapForm206Vo = <IapForm206Vo>{};
  selectedMedicalAidVo = null;
  iapMedicalAidVo = <IapMedicalAidVo>{};

  iapFormHelper = new IapFormHelper();

  isFormLocked = false;
  compForm: FormGroup;

  // grid vars
  ambulanceList = []; // as IapMedicalAidVo[];
  gridColumnDefs = [
    {headerName: '', field: 'positionNum', width: 40, pinned: 'left', sort: 'asc'},
    {headerName: 'Name', field: 'name', width: 200, pinned: 'left'},
    {headerName: 'Address Line 1', field: 'addressVo.addressLine1', width: 140},
    {headerName: 'Address Line 2', field: 'addressVo.addressLine2', width: 140},
    {headerName: 'City', field: 'addressVo.city', width: 120},
    {headerName: 'State', field: 'addressVo.countrySubdivisionVo.countrySubAbbreviation', width: 70},
    {headerName: 'Zip Code', field: 'addressVo.postalCode', width: 90},
    {headerName: 'Phone', field: 'phone', width: 120},
    {headerName: 'EMS Frequency', field: 'emsFrequency', width: 150},
    {headerName: 'Advanced Life Support', field: 'lifeSupport', width: 180, filter: false, cellRenderer: 'checkboxRenderer'},
  ];

  constructor(private formBuilder: FormBuilder,
                private notifyService: NotificationService,
                private iapPlanService: IapPlanService) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      name: new FormControl({value: '', disabled: false})
      , addressLine1: new FormControl({value: '', disabled: false})
      , addressLine2: new FormControl({value: '', disabled: false})
      , city: new FormControl({value: '', disabled: false})
      , state: {}
      , zip: new FormControl({value: '', disabled: false})
      , phone: new FormControl({value: '', disabled: false})
      , emsFrequency: new FormControl({value: '', disabled: false})
      , als: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  initMedicalAidVo() {
    this.iapMedicalAidVo = this.iapFormHelper.initNewIapMedicalAidVo(this.iapForm206Vo.id);
  }

  reloadPage(vo: IapForm206Vo) {
    this.ambulanceList = [];
    this.gridAmbulance.clearSelected();
    this.iapForm206Vo = Object.assign({}, vo);
    vo.iapAmbulanceVos.forEach(f => {
      if ( f.isBlankLine === true ) {
        f.name = '[Blank Line]';
      }
      if ( f.phone !== undefined && f.phone !== null) {
        const p = this.phonePipe.transform(f.phone);
        f.phone = p;
      }
      this.ambulanceList.push(f);
    });

    this.isFormLocked = this.iapForm206Vo.isFormLocked;
    this.initMedicalAidVo();
    this.resetForm();
  }

  resetForm() {
    // reset Form
    setTimeout(() => {
      this.selectedStateDropdownData = this.ddState.getDropdownDataObjectById(-1);

      this.compForm.setValue({
        name: (this.iapMedicalAidVo.isBlankLine === true ? '' : this.iapMedicalAidVo.name),
        addressLine1: this.iapMedicalAidVo.addressVo.addressLine1,
        addressLine2: this.iapMedicalAidVo.addressVo.addressLine2,
        city: this.iapMedicalAidVo.addressVo.city,
        state: {},
        zip: this.iapMedicalAidVo.addressVo.postalCode,
        phone: this.iapMedicalAidVo.phone,
        emsFrequency: this.iapMedicalAidVo.emsFrequency,
        als: ( this.iapMedicalAidVo.lifeSupport === true ? 'Yes' : 'No' ),
      });
    });

    setTimeout(() => {
      if ( this.iapMedicalAidVo.addressVo.countrySubdivisionVo !== null
        && this.iapMedicalAidVo.addressVo.countrySubdivisionVo.id > 0 ) {
        this.selectedStateDropdownData = this.ddState.getDropdownDataObjectById(this.iapMedicalAidVo.addressVo.countrySubdivisionVo.id);
      }
      this.enableDisablePage();
    });

  }

  enableDisablePage() {
    const controlList = ['name', 'addressLine1', 'addressLine2', 'city'
      , 'phone', 'zip', 'emsFrequency', 'als'];


    if ( this.isFormLocked === true ) {
      this.ddStateDisabled = true;
      controlList.forEach(name => {
        this.compForm.controls[name].disable();
      });
    } else {
      if ( this.iapMedicalAidVo.isBlankLine === true ) {
        this.ddStateDisabled = true;
        controlList.forEach(name => {
          this.compForm.controls[name].disable();
        });
        setTimeout(() => {
          this.ddState.dropdownDisabled = true;
        });
     } else {
      this.ddStateDisabled = false;
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
    this.gridAmbulance.clearSelected();
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
    vo.type = 'AMBULANCE';

    vo.isBlankLine = true;
    if (location === 'BOTTOM') {
      vo.positionNum = ( this.ambulanceList.length + 1 );
    } else {
      const curPosition = this.selectedMedicalAidVo.positionNum;

      if ( location === 'ABOVE' ) {
        vo.positionNum = curPosition;
      } else {
        // BELOW
        vo.positionNum = (curPosition + 1);
      }
    }

    this.iapPlanService.saveIapForm206Ambulance(this.iapForm206Vo.id, vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_AMBULANCE' ) {
        this.ambulanceList = [];
        const vos = data['recordset'] as IapMedicalAidVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.name = '[Blank Line]';
          }
          if ( f.phone !== undefined && f.phone !== null) {
            const p = this.phonePipe.transform(f.phone);
            f.phone = p;
          }
          this.ambulanceList.push(f);
        });
        this.gridAmbulance.gridOptions.rowData = this.ambulanceList;
        this.add();
        this.ics206AmbulanceUpdateEvent.emit();
      }
    });
  }

  save() {
    if ( this.iapMedicalAidVo.isBlankLine === true ) {
      return;
    }
    const isNew = ( this.iapMedicalAidVo.id > 0 ? false : true );
    this.iapMedicalAidVo.type = 'AMBULANCE';
    this.iapMedicalAidVo.iapForm206Id = this.iapForm206Vo.id;
    this.iapMedicalAidVo.name = this.compForm.get('name').value;
    this.iapMedicalAidVo.addressVo.addressLine1 = this.compForm.get('addressLine1').value;
    this.iapMedicalAidVo.addressVo.addressLine2 = this.compForm.get('addressLine2').value;
    this.iapMedicalAidVo.addressVo.city = this.compForm.get('city').value;
    this.iapMedicalAidVo.addressVo.postalCode = this.compForm.get('zip').value;
    this.iapMedicalAidVo.phone = this.compForm.get('phone').value;
    this.iapMedicalAidVo.emsFrequency = this.compForm.get('emsFrequency').value;
    this.iapMedicalAidVo.lifeSupport =
      ( this.compForm.get('als').value === 'Yes' ? true : false);

    if ( this.iapMedicalAidVo.addressVo.countrySubdivisionVo === null ) {
        this.iapMedicalAidVo.addressVo.countrySubdivisionVo = <CountryCodeSubdivisionVo>{id: 0};
    }

    this.iapMedicalAidVo.addressVo.countrySubdivisionVo.id = this.ddState.selectedValue.id;

    if ( this.iapMedicalAidVo.id > 0 ) {

    } else {
      this.iapMedicalAidVo.positionNum = (this.ambulanceList.length + 1);
    }

    this.iapPlanService.saveIapForm206Ambulance(this.iapForm206Vo.id, this.iapMedicalAidVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_AMBULANCE' ) {
        this.gridAmbulance.clearSelected();
        this.ambulanceList = [];
        const vos = data['recordset'] as IapMedicalAidVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.name = '[Blank Line]';
          }
          if ( f.phone !== undefined && f.phone !== null) {
            const p = this.phonePipe.transform(f.phone);
            f.phone = p;
          }
          this.ambulanceList.push(f);
        });

        this.gridAmbulance.gridOptions.rowData = this.ambulanceList;
        this.add();
        this.ics206AmbulanceUpdateEvent.emit();
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
      this.showPrompt('', 'Delete Ambulance', 'Please select an Ambulance to delete.' , 'Ok', '', '');
    } else {
      this.iapPlanService.deleteIapForm206Ambulance(this.iapForm206Vo.id, this.selectedMedicalAidVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_FORM_206_AMBULANCE' ) {
          this.gridAmbulance.clearSelected();
          this.ambulanceList = [];
          const vos = data['recordset'] as IapMedicalAidVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.name = '[Blank Line]';
            }
            if ( f.phone !== undefined && f.phone !== null) {
              const p = this.phonePipe.transform(f.phone);
              f.phone = p;
            }
            this.ambulanceList.push(f);
          });
          this.gridAmbulance.gridOptions.rowData = this.ambulanceList;
          this.add();
          this.ics206AmbulanceUpdateEvent.emit();
          }
      });
    }
  }

  reorder() {
    if ( this.ambulanceList.length < 2) {
      this.showPrompt('', 'Re-Order Ambulances', 'There must be at least 2 Ambulances to Re-Order.' , 'Ok', '', '');
    } else {
      this.ics206ReorderOpenEvent.emit('Ambulance');
    }
  }

  saveNewOrder(newVos) {
    this.iapPlanService.saveIapForm206AmbulancePositions(this.iapForm206Vo.id, newVos)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_AMBULANCE_POSITIONS' ) {
          this.gridAmbulance.clearSelected();
          this.ambulanceList = [];
          const vos = data['recordset'] as IapMedicalAidVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.name = '[Blank Line]';
            }
            if ( f.phone !== undefined && f.phone !== null) {
              const p = this.phonePipe.transform(f.phone);
              f.phone = p;
            }
            this.ambulanceList.push(f);
          });
          this.gridAmbulance.gridOptions.rowData = this.ambulanceList;
          this.add();
          this.ics206AmbulanceUpdateEvent.emit();
          }
      });

  }

  showPrompt(mode, title, msg, btn1, btn2, btn3) {
    this.icsForm206AmbPromptModal.reset();
    this.icsForm206AmbPromptModal.promptMode = mode;
    this.icsForm206AmbPromptModal.promptTitle = title;
    this.icsForm206AmbPromptModal.promptMessage1 = msg;
    this.icsForm206AmbPromptModal.button1Label = btn1;
    this.icsForm206AmbPromptModal.button2Label = btn2;
    this.icsForm206AmbPromptModal.button3Label = btn3;
    this.icsForm206AmbPromptModal.openModal();
  }

  promptActionResult(evt: any) {
    this.icsForm206AmbPromptModal.closeModal();
    if ( this.icsForm206AmbPromptModal.promptMode === 'AddBlankLine') {
      this.addBlankLine(evt);
     }
  }

}
