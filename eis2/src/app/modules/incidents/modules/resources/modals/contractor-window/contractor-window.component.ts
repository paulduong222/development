import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { NotificationService } from 'src/app/service/notification-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { DropdownData } from 'src/app/data/dropdowndata';
import { ContractorVo } from 'src/app/data/contractor-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { ContractorAgreementVo } from 'src/app/data/contractor-agreement-vo';
import { AddressVo } from 'src/app/data/address-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { ContractorService } from 'src/app/service/contractor.service';

@Component({
  selector: 'app-contractor-window',
  templateUrl: './contractor-window.component.html',
  styleUrls: ['./contractor-window.component.css']
})
export class ContractorWindowComponent implements OnInit {
  modalId = 'contractor-window-modal';
  @Output() saveContractorEvent = new EventEmitter();
  @ViewChild('ddState') ddState: EisDropdownComponent;
  @ViewChild('ddIncident') ddIncident: EisDropdownComponent;
  incidentId = null;
  incidentGroupId = null;
  windowLabel = 'Add Contractor';
  contractorForm: FormGroup;
  selectedState: DropdownData;
  selectedIncident: DropdownData;
  contractorVo: ContractorVo;

  constructor(private modalService: ModalService
              , private formBuilder: FormBuilder
              , private contractorService: ContractorService
              , public incidentSelectorService: IncidentSelectorService
              , private notifyService: NotificationService) {
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

  loadWindow() {
    this.initNewContractorVo();
    this.resetForm();
  }

  getIncidentSelectorClass() {
    if (this.incidentId === null) {
      return '';
    } else {
      return 'hidden';
    }
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
    this.selectedIncident = this.ddIncident.getDropdownDataObjectById(-2);
    this.selectedState = this.ddState.getDropdownDataObjectById(-2);

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

  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

  save() {
    if ( this.incidentId !== null) {
      this.contractorVo.incidentVo.id = this.incidentId;
    } else {
      if ( this.ddIncident.selectedValue.id > 0 ) {
        this.contractorVo.incidentVo.id = this.ddIncident.selectedValue.id;
      }
    }
    this.contractorVo.name = this.contractorForm.get('name').value;

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

    this.contractorService.saveContractor(this.contractorVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_CONTRACTOR') {
        const newContractorVo = data['resultObject'] as ContractorVo;
        this.saveContractorEvent.emit(newContractorVo);
      }
    });
  }
}
