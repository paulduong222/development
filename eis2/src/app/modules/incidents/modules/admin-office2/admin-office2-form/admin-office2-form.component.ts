import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { AdminOfficeVo } from 'src/app/data/admin-office-vo';
import { AddressVo } from 'src/app/data/address-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { DropdownData } from 'src/app/data/dropdowndata';

@Component({
  selector: 'app-admin-office2-form',
  templateUrl: './admin-office2-form.component.html',
  styleUrls: ['./admin-office2-form.component.css']
})
export class AdminOffice2FormComponent implements OnInit {
  @Output() saveEvent = new EventEmitter();
  @Output() cancelEvent = new EventEmitter();
  @ViewChild('ddState') ddState: EisDropdownComponent;
  statesDropdownData = []; // <-- this is collection of dropdownData based on CouuntryCodeSubdivisionVos
  adminOfficeVo: AdminOfficeVo = <AdminOfficeVo>{};
  adminOfficeForm: FormGroup;
  stateDropdownData: DropdownData;
  constructor( private formBuilder: FormBuilder) { 
    this.adminOfficeForm = this.formBuilder.group({
      id: new FormControl(0),
      officeName: new FormControl(''),
      addressLine1: new FormControl(''),
      addressLine2: new FormControl(''),
      city: new FormControl(''),
      countryCodeSubdivisionVo: new FormControl({}),
      zip: new FormControl(''),
      phone: new FormControl(''),
    });

  }

  ngOnInit() {
  }

  initNewAdminOfficeVo() {
    this.adminOfficeVo = <AdminOfficeVo>{
      id: 0
      , officeName: ''
      , addressVo: <AddressVo>{
        id: 0
        , addressLine1: ''
        , addressLine2: ''
        , city: ''
        , countrySubdivisionVo: <CountryCodeSubdivisionVo>{
          id: 0
          , countrySubAbbreviation: ''
          , countrySubName: ''
        }
        , postalCode: ''
      }
      , phone: ''
      , standard: false
    };
  }

  addNew() {
    this.initNewAdminOfficeVo();
    this.resetForm();
  }

  resetForm() {
    this.adminOfficeForm.setValue(
      {
        id: this.adminOfficeVo.id
        , officeName: this.adminOfficeVo.officeName
        , addressLine1: this.adminOfficeVo.addressVo.addressLine1
        , addressLine2: this.adminOfficeVo.addressVo.addressLine2
        , city: this.adminOfficeVo.addressVo.city
        , countryCodeSubdivisionVo: {}
        , zip: this.adminOfficeVo.addressVo.postalCode
        , phone: this.adminOfficeVo.phone
      }
    );

    if ( this.adminOfficeVo.addressVo.countrySubdivisionVo.id > 0 ) {
      this.stateDropdownData = this.ddState.getDropdownDataObjectById(this.adminOfficeVo.addressVo.countrySubdivisionVo.id);
    } else {
      this.stateDropdownData = this.ddState.getDropdownDataObjectById(0);
    }

    const controlList = ['officeName', 'addressLine1', 'addressLine2', 'city', 'zip', 'phone'];
    if ( this.adminOfficeVo.standard === true ) {
      for ( let name of controlList ) {
        this.adminOfficeForm.controls[name].disable();
      }
      this.ddState.dropdownDisabled = true;
    } else {
      for ( let name of controlList ) {
        this.adminOfficeForm.controls[name].enable();
      }
      this.ddState.dropdownDisabled = false;
    }

  }

  save() {
    this.adminOfficeVo.officeName = this.adminOfficeForm.get('officeName').value;
    this.adminOfficeVo.phone = this.adminOfficeForm.get('phone').value;
    this.adminOfficeVo.addressVo.addressLine1 = this.adminOfficeForm.get('addressLine1').value;
    this.adminOfficeVo.addressVo.addressLine2 = this.adminOfficeForm.get('addressLine2').value;
    this.adminOfficeVo.addressVo.city = this.adminOfficeForm.get('city').value;
    this.adminOfficeVo.addressVo.postalCode = this.adminOfficeForm.get('zip').value;
    this.adminOfficeVo.addressVo.countrySubdivisionVo.id = this.ddState.selectedValue.id;
    this.saveEvent.emit(this.adminOfficeVo);
  }

  cancel() {
    if ( this.adminOfficeVo.id < 1 ) {
      this.addNew();
    } else {
      this.cancelEvent.emit();
    }
  }

}
