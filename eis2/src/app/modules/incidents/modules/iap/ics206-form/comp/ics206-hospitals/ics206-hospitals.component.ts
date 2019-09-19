import { Component, OnInit, AfterViewInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { IapForm206Vo } from 'src/app/data/iap-form206-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IapHospitalVo } from 'src/app/data/iap-hospital-vo';
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
  selector: 'app-ics206-hospitals',
  templateUrl: './ics206-hospitals.component.html',
  styleUrls: ['./ics206-hospitals.component.css']
})
export class Ics206HospitalsComponent implements OnInit, AfterViewInit {
  @Output() ics206HospitalUpdateEvent = new EventEmitter();
  @Output() ics206ReorderOpenEvent = new EventEmitter();
  @ViewChild('icsForm206HospPromptModal') icsForm206HospPromptModal: PromptModalComponent;
  @ViewChild('ddState') ddState: EisDropdownComponent;
  @ViewChild('gridHospital') gridHospital: EisGridComponent;
  phonePipe = new PhonePipe();

  // selected object for dropdown
  statesDropdownData = []; // <-- this is collection of dropdownData based on CouuntryCodeSubdivisionVos

  // selected object for dropdown
  selectedStateDropdownData: DropdownData;

  ddStateDisabled = false;
  iapForm206Vo = <IapForm206Vo>{};
  selectedHospitalVo = null;
  iapHospitalVo = <IapHospitalVo>{};

  iapFormHelper = new IapFormHelper();

  isFormLocked = false;
  compForm: FormGroup;

  // grid vars
  hospitalList = []; // as IapHospitalVo[];
  gridColumnDefs = [
    {headerName: '', field: 'positionNum', width: 40, pinned: 'left', sort: 'asc'},
    {headerName: 'Name', field: 'name', width: 200, pinned: 'left'},
    {headerName: 'Address Line 1', field: 'addressVo.addressLine1', width: 140},
    {headerName: 'Address Line 2', field: 'addressVo.addressLine2', width: 140},
    {headerName: 'City', field: 'addressVo.city', width: 120},
    {headerName: 'State', field: 'addressVo.countrySubdivisionVo.countrySubAbbreviation', width: 70},
    {headerName: 'Zip Code', field: 'addressVo.postalCode', width: 90},
    {headerName: 'Phone', field: 'phone', width: 120},
    {headerName: 'Air Travel Time', field: 'airTravelTime', width: 150},
    {headerName: 'Ground Travel Time', field: 'groundTravelTime', width: 150},
    {headerName: 'Helipad', field: 'helipad', width: 180, filter: false, cellRenderer: 'checkboxRenderer'},
    {headerName: 'Level of Care', field: 'levelOfCare', width: 150},
    {headerName: 'Lattitude/Longitude', field: '', width: 150},
    {headerName: 'VHF', field: 'vhf', width: 100},
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
      , airTravelTime: new FormControl({value: '', disabled: false})
      , groundTravelTime: new FormControl({value: '', disabled: false})
      , helipad: new FormControl({value: '', disabled: false})
      , levelOfCare: new FormControl({value: '', disabled: false})
      , latitude: new FormControl({value: '', disabled: false})
      , longitude: new FormControl({value: '', disabled: false})
      , vhf: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  initHospitalVo() {
    this.iapHospitalVo = this.iapFormHelper.initNewIapHospitalVo(this.iapForm206Vo.id);
  }

  reloadPage(vo: IapForm206Vo) {
    this.hospitalList = [];
    this.gridHospital.clearSelected();
    this.iapForm206Vo = Object.assign({}, vo);
    vo.iapHospitalVos.forEach(f => {
      if ( f.isBlankLine === true ) {
        f.name = '[Blank Line]';
      }
      if ( f.phone !== undefined && f.phone !== null) {
        const p = this.phonePipe.transform(f.phone);
        f.phone = p;
      }
      this.hospitalList.push(f);
    });

    this.isFormLocked = this.iapForm206Vo.isFormLocked;
    this.initHospitalVo();
    this.resetForm();
  }

  resetForm() {
    // reset Form
    setTimeout(() => {
      this.selectedStateDropdownData = this.ddState.getDropdownDataObjectById(-1);

      this.compForm.setValue({
        name: (this.iapHospitalVo.isBlankLine === true ? '' : this.iapHospitalVo.name),
        addressLine1: this.iapHospitalVo.addressVo.addressLine1,
        addressLine2: this.iapHospitalVo.addressVo.addressLine2,
        city: this.iapHospitalVo.addressVo.city,
        state: {},
        zip: this.iapHospitalVo.addressVo.postalCode,
        phone: this.iapHospitalVo.phone,
        airTravelTime: this.iapHospitalVo.airTravelTime,
        groundTravelTime: this.iapHospitalVo.groundTravelTime,
        levelOfCare: this.iapHospitalVo.levelOfCare,
        helipad: ( this.iapHospitalVo.helipad === true ? 'Yes' : 'No' ),
        latitude: this.iapHospitalVo.latitude,
        longitude: this.iapHospitalVo.longitude,
        vhf: this.iapHospitalVo.vhf,
        });
    });

    setTimeout(() => {
      if ( this.iapHospitalVo.addressVo.countrySubdivisionVo !== null
        && this.iapHospitalVo.addressVo.countrySubdivisionVo.id > 0 ) {
        this.selectedStateDropdownData = this.ddState.getDropdownDataObjectById(this.iapHospitalVo.addressVo.countrySubdivisionVo.id);
      }
      this.enableDisablePage();
    });

  }

  enableDisablePage() {
    const controlList = ['name', 'addressLine1', 'addressLine2', 'city'
      , 'phone', 'zip', 'airTravelTime', 'groundTravelTime', 'levelOfCare', 'latitude', 'longitude'
     , 'helipad', 'vhf'];


    if ( this.isFormLocked === true ) {
      this.ddStateDisabled = true;
      controlList.forEach(name => {
        this.compForm.controls[name].disable();
      });
    } else {
      if ( this.iapHospitalVo.isBlankLine === true ) {
        this.ddStateDisabled = true;
        controlList.forEach(name => {
          this.compForm.controls[name].disable();
        });
        this.ddState.dropdownDisabled = true;
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
      this.selectedHospitalVo = Object.assign({}, row as IapHospitalVo);
      this.iapHospitalVo = Object.assign({}, row as IapHospitalVo);
      this.resetForm();
    } else {
      this.selectedHospitalVo = null;
    }
  }

  add() {
    this.gridHospital.clearSelected();
    this.selectedHospitalVo = null;
    this.initHospitalVo();

    setTimeout(() => {
      this.resetForm();
    });
  }

  preAddBlankLine() {
    if ( this.selectedHospitalVo === null || this.selectedHospitalVo.id === 0) {
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
    let vo = this.iapFormHelper.initNewIapHospitalVo(this.iapForm206Vo.id);

    vo.isBlankLine = true;
    if (location === 'BOTTOM') {
      vo.positionNum = ( this.hospitalList.length + 1 );
    } else {
      const curPosition = this.selectedHospitalVo.positionNum;

      if ( location === 'ABOVE' ) {
        vo.positionNum = curPosition;
      } else {
        // BELOW
        vo.positionNum = (curPosition + 1);
      }
    }

    this.iapPlanService.saveIapForm206Hospital(this.iapForm206Vo.id, vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_HOSPITAL' ) {
        this.hospitalList = [];
        const vos = data['recordset'] as IapHospitalVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.name = '[Blank Line]';
          }
          if ( f.phone !== undefined && f.phone !== null) {
            const p = this.phonePipe.transform(f.phone);
            f.phone = p;
          }
          this.hospitalList.push(f);
        });
        this.gridHospital.gridOptions.rowData = this.hospitalList;
        this.add();
        this.ics206HospitalUpdateEvent.emit();
      }
    });
  }

  save() {
    if ( this.iapHospitalVo.isBlankLine === true ) {
      return;
    }

    const isNew = ( this.iapHospitalVo.id > 0 ? false : true );
    this.iapHospitalVo.iapForm206Id = this.iapForm206Vo.id;
    this.iapHospitalVo.name = this.compForm.get('name').value;
    this.iapHospitalVo.addressVo.addressLine1 = this.compForm.get('addressLine1').value;
    this.iapHospitalVo.addressVo.addressLine2 = this.compForm.get('addressLine2').value;
    this.iapHospitalVo.addressVo.city = this.compForm.get('city').value;
    this.iapHospitalVo.addressVo.postalCode = this.compForm.get('zip').value;
    this.iapHospitalVo.phone = this.compForm.get('phone').value;
    this.iapHospitalVo.airTravelTime = this.compForm.get('airTravelTime').value;
    this.iapHospitalVo.groundTravelTime = this.compForm.get('groundTravelTime').value;
    this.iapHospitalVo.levelOfCare = this.compForm.get('levelOfCare').value;
    this.iapHospitalVo.latitude = this.compForm.get('latitude').value;
    this.iapHospitalVo.longitude = this.compForm.get('longitude').value;
    this.iapHospitalVo.vhf = this.compForm.get('vhf').value;
    this.iapHospitalVo.helipad =
      ( this.compForm.get('helipad').value === 'Yes' ? true : false);

    if ( this.iapHospitalVo.addressVo.countrySubdivisionVo === null ) {
        this.iapHospitalVo.addressVo.countrySubdivisionVo = <CountryCodeSubdivisionVo>{id: 0};
    }

    this.iapHospitalVo.addressVo.countrySubdivisionVo.id = this.ddState.selectedValue.id;

    if ( this.iapHospitalVo.id > 0 ) {

    } else {
      this.iapHospitalVo.positionNum = (this.hospitalList.length + 1);
    }

    this.iapPlanService.saveIapForm206Hospital(this.iapForm206Vo.id, this.iapHospitalVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_HOSPITAL' ) {
        this.gridHospital.clearSelected();
        this.hospitalList = [];
        const vos = data['recordset'] as IapHospitalVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.name = '[Blank Line]';
          }
          if ( f.phone !== undefined && f.phone !== null) {
            const p = this.phonePipe.transform(f.phone);
            f.phone = p;
          }
          this.hospitalList.push(f);
        });

        this.gridHospital.gridOptions.rowData = this.hospitalList;
        this.add();
        this.ics206HospitalUpdateEvent.emit();
      }
    });
  }

  cancel() {
    if ( this.iapHospitalVo !== undefined && this.iapHospitalVo.id > 0 ) {
      this.iapHospitalVo = Object.assign({}, this.selectedHospitalVo);
      setTimeout(() => {
        this.resetForm();
      });
    } else {
      this.initHospitalVo();
      setTimeout(() => {
        this.resetForm();
      });
    }
  }

  delete() {
    if ( this.selectedHospitalVo === null || this.selectedHospitalVo.id < 1 ) {
      this.showPrompt('', 'Delete Hospital', 'Please select a Hospital to delete.' , 'Ok', '', '');
    } else {
      this.iapPlanService.deleteIapForm206Hospital(this.iapForm206Vo.id, this.selectedHospitalVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_FORM_206_HOSPITAL' ) {
          this.gridHospital.clearSelected();
          this.hospitalList = [];
          const vos = data['recordset'] as IapHospitalVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.name = '[Blank Line]';
            }
            if ( f.phone !== undefined && f.phone !== null) {
              const p = this.phonePipe.transform(f.phone);
              f.phone = p;
            }
            this.hospitalList.push(f);
          });
          this.gridHospital.gridOptions.rowData = this.hospitalList;
          this.add();
          this.ics206HospitalUpdateEvent.emit();
          }
      });
    }
  }

  reorder() {
    if ( this.hospitalList.length < 2) {
      this.showPrompt('', 'Re-Order Hospitals', 'There must be at least 2 Hospitals to Re-Order.' , 'Ok', '', '');
    } else {
      this.ics206ReorderOpenEvent.emit('Hospitals');
    }
  }

  saveNewOrder(newVos) {
    this.iapPlanService.saveIapForm206HospitalPositions(this.iapForm206Vo.id, newVos)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_HOSPITAL_POSITIONS' ) {
          this.gridHospital.clearSelected();
          this.hospitalList = [];
          const vos = data['recordset'] as IapHospitalVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.name = '[Blank Line]';
            }
            if ( f.phone !== undefined && f.phone !== null) {
              const p = this.phonePipe.transform(f.phone);
              f.phone = p;
            }
            this.hospitalList.push(f);
          });
          this.gridHospital.gridOptions.rowData = this.hospitalList;
          this.add();
          this.ics206HospitalUpdateEvent.emit();
          }
      });
  }

  showPrompt(mode, title, msg, btn1, btn2, btn3) {
    this.icsForm206HospPromptModal.reset();
    this.icsForm206HospPromptModal.promptMode = mode;
    this.icsForm206HospPromptModal.promptTitle = title;
    this.icsForm206HospPromptModal.promptMessage1 = msg;
    this.icsForm206HospPromptModal.button1Label = btn1;
    this.icsForm206HospPromptModal.button2Label = btn2;
    this.icsForm206HospPromptModal.button3Label = btn3;
    this.icsForm206HospPromptModal.openModal();
  }

  promptActionResult(evt: any) {
    this.icsForm206HospPromptModal.closeModal();
    if ( this.icsForm206HospPromptModal.promptMode === 'AddBlankLine') {
      this.addBlankLine(evt);
     }
  }

}
