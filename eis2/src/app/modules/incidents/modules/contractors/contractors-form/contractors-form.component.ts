import { Component, OnInit, Input, ViewChild, Output, EventEmitter } from '@angular/core';
import { ContractorVo } from 'src/app/data/contractor-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { AddressVo } from 'src/app/data/address-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { DropdownData } from 'src/app/data/dropdowndata';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { OuterSubscriber } from 'rxjs/internal/OuterSubscriber';
import { PhoneInputComponent } from 'src/app/components/phone-input/phone-input.component';
import { ContractorAgreementVo } from 'src/app/data/contractor-agreement-vo';

@Component({
  selector: 'app-contractors-form',
  templateUrl: './contractors-form.component.html',
  styleUrls: ['./contractors-form.component.css']
})
export class ContractorsFormComponent implements OnInit {
  @Output() contractorFormEvent = new EventEmitter();
  @ViewChild('ddState') ddState: EisDropdownComponent;
  @ViewChild('ddIncident') ddIncident: EisDropdownComponent;
  @ViewChild('txPhone') txPhone: PhoneInputComponent;

  @Input() currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};

  // selected object for dropdown
  statesDropdownData = []; // <-- this is collection of dropdownData based on CouuntryCodeSubdivisionVos
  incidentDropdownData = [];

  // selected object for dropdown
  selectedStateDropdownData: DropdownData;
  selectedIncidentDropdownData: DropdownData;

  contractorForm: FormGroup;
  contractorVo: ContractorVo;

  constructor( private formBuilder: FormBuilder) {
    this.contractorForm = this.formBuilder.group({
      id: new FormControl(0),
      incidentVo: new FormControl({ value: {}, disabled: false}),
      name: new FormControl(''),
      tin: new FormControl(''),
      verifyTin: new FormControl(''),
      duns: new FormControl(''),
      phone: new FormControl(''),
      fax: new FormControl(''),
      addressLine1: new FormControl(''),
      addressLine2: new FormControl(''),
      city: new FormControl(''),
      countryCodeSubdivisionVo: new FormControl({}),
      zip: new FormControl(''),
    });
  }

  ngOnInit() {
  }

  getIncidentSelectorClass() {
    if (this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      return '';
    } else {
      return 'hidden';
    }
  }

  addNew() {
    this.initNewContractorVo();
    this.resetForm();
  }

  initNewContractorVo() {
    this.contractorVo = <ContractorVo>{
      id: 0
      , incidentVo: <IncidentVo>{
        id: 0
      }
      , name: ''
      , tin: ''
      , verifyTin: ''
      , actualTin: ''
      , duns: ''
      , phone: ''
      , fax: ''
      , deletedDate: null
      , incidentVos: [] as IncidentVo[]
      , contractorAgreementVos: [] as ContractorAgreementVo[]
      , enabled: true
      , addressVo: <AddressVo>{
        id: 0
        , addressLine1: ''
        , addressLine2: ''
        , city: ''
        , countrySubdivisionVo: <CountryCodeSubdivisionVo>{
          id: 0
        }
        , postalCode: ''
      }
    };
  }

  resetForm() {
    this.selectedStateDropdownData = this.ddState.getDropdownDataObjectById(-1);
    this.selectedIncidentDropdownData = this.ddIncident.getDropdownDataObjectById(-1);

    this.contractorForm.setValue(
      {
        id: this.contractorVo.id
        , incidentVo: {}
        , name: this.contractorVo.name
        , tin: this.contractorVo.tin
        , verifyTin: this.contractorVo.verifyTin
        , phone: this.contractorVo.phone
        , fax: this.contractorVo.fax
        , duns: this.contractorVo.duns
        , addressLine1: this.contractorVo.addressVo.addressLine1
        , addressLine2: this.contractorVo.addressVo.addressLine2
        , city: this.contractorVo.addressVo.city
        , zip: this.contractorVo.addressVo.postalCode
        , countryCodeSubdivisionVo: {}
      }
    );

    if ( this.contractorVo.id < 1 ) {
      this.ddIncident.dropdownDisabled = false;
    } else {
      this.ddIncident.dropdownDisabled = true;
    }

    setTimeout(() => {
      if ( this.contractorVo.addressVo.countrySubdivisionVo !== null 
        && this.contractorVo.addressVo.countrySubdivisionVo.id > 0 ) {
        this.selectedStateDropdownData = this.ddState.getDropdownDataObjectById(this.contractorVo.addressVo.countrySubdivisionVo.id);
      }

      if ( this.contractorVo.incidentVo.id > 0 ) {
        this.selectedIncidentDropdownData = this.ddIncident.getDropdownDataObjectById(this.contractorVo.incidentVo.id);
      }

    });

  }

  save() {
    this.contractorVo.name = this.contractorForm.get('name').value;
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      this.contractorVo.incidentVo.id = this.ddIncident.selectedValue.id;
    } else {
      this.contractorVo.incidentVo.id = this.currentSelectedIncidentSelectorVo.incidentId;
    }

    this.contractorVo.duns = this.contractorForm.get('duns').value;
    this.contractorVo.phone = this.contractorForm.get('phone').value;
    this.contractorVo.addressVo.addressLine1 = this.contractorForm.get('addressLine1').value;
    this.contractorVo.addressVo.addressLine2 = this.contractorForm.get('addressLine2').value;
    this.contractorVo.addressVo.city = this.contractorForm.get('city').value;
    this.contractorVo.addressVo.postalCode = this.contractorForm.get('zip').value;
    if ( this.contractorVo.addressVo.countrySubdivisionVo === null ) {
      this.contractorVo.addressVo.countrySubdivisionVo = <CountryCodeSubdivisionVo>{id: 0};
    }

    this.contractorVo.addressVo.countrySubdivisionVo.id = this.ddState.selectedValue.id;

    this.contractorFormEvent.emit({
      name: 'SAVE_CONTRACTOR'
      , contractorVo: this.contractorVo
    });

  }

  cancel() {
    if ( this.contractorVo.id < 1 ) {
      this.addNew();
    } else {
      // reload selected from contractors-view
      this.contractorFormEvent.emit({name: 'RELOAD_SELECTED'});
    }
  }
}
