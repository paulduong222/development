import { Component, OnInit, ViewChild } from '@angular/core';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { ResourceHomeUnitContactVo } from 'src/app/data/resource-home-unit-contact-vo';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { DropdownData } from 'src/app/data/dropdowndata';
import { ResourceHomeUnitContactData } from 'src/app/data/rest/resource-home-unit-contact-data';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { AddressVo } from 'src/app/data/address-vo';
import { OrganizationVo } from 'src/app/data/organization-vo';
import { IncidentResourceVo } from 'src/app/data/incident-resource-vo';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingSpecialistService } from 'src/app/service/training-specialist.service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';

@Component({
  selector: 'app-home-unit-contact',
  templateUrl: './home-unit-contact.component.html',
  styleUrls: ['./home-unit-contact.component.css']
})
export class HomeUnitContactComponent implements OnInit {
  @ViewChild('ddUnitCode') ddUnitCode: EisDropdownComponent;
  @ViewChild('ddState') ddState: EisDropdownComponent;
  
  selectedHomeUnitContactVo: ResourceHomeUnitContactVo;
  homeUnitContactVo: ResourceHomeUnitContactVo;
  homeUnitContactForm: FormGroup;
  selectedUnitDropdownData: DropdownData;
  selectedStateDropdownData: DropdownData;
  incidentResourceId: number;

  constructor(public incidentSelectorService: IncidentSelectorService,
              private notificationService: NotificationService,
              private trainingSpecialistService: TrainingSpecialistService,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.initHomeUnitContactForm();
    this.addHomeUnitContact();
  }

  initHomeUnitContactForm() {
    this.homeUnitContactForm = this.formBuilder.group({
      name: new FormControl(''),
      unitDesc: new FormControl({value: '', disabled: true}),
      address1: new FormControl(''),
      unitVo: new FormControl({}),
      city: new FormControl(''),
      countryCodeSubdivisionVo: new FormControl({}),
      zipCode: new FormControl(''),
      phone: new FormControl(''),
      email: new FormControl('')
    });
  }

  addHomeUnitContact() {
    this.initHomeUnitContactVo();
  }

  initHomeUnitContactVo() {
    this.homeUnitContactVo = <ResourceHomeUnitContactVo> {
      id: 0,
      incidentResourceVo: <IncidentResourceVo> {
        id: 0
      },
      contactName: '',
      unitVo: <OrganizationVo> {
        id: 0,
        unitCode: '',
        name: ''
      },
      addressVo: <AddressVo> {
        id: 0,
        addressLine1: '',
        city: '',
        countrySubdivisionVo: <CountryCodeSubdivisionVo>{
          id: 0,
          countrySubAbbreviation: '',
          countrySubName: ''
        },
        postalCode: ''
      },
      phone: '',
      email: ''
    };

    this.populateHomeUnitContactForm();
  }

  getHomeUnitContact() {
    this.trainingSpecialistService.getHomeUnitContact(this.incidentResourceId)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_HOME_UNIT_CONTACT'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
                this.selectedHomeUnitContactVo = data['resultObject'] as ResourceHomeUnitContactVo;
                this.homeUnitContactVo =  data['resultObject'] as ResourceHomeUnitContactVo;
                this.populateHomeUnitContactForm();
        }
      });  
  }

  populateHomeUnitContactForm() {
    this.selectedUnitDropdownData = this.ddUnitCode.getDropdownDataObjectById(-2);
    this.selectedStateDropdownData = this.ddState.getDropdownDataObjectById(-2);

    if (this.homeUnitContactForm != undefined) {
      this.homeUnitContactForm.setValue({
        name: this.homeUnitContactVo.contactName,
        unitVo: {},
        unitDesc: this.homeUnitContactVo.unitVo != null ? this.homeUnitContactVo.unitVo.name : '',
        address1: this.homeUnitContactVo.addressVo != null ? this.homeUnitContactVo.addressVo.addressLine1 : '',
        city: this.homeUnitContactVo.addressVo != null ? this.homeUnitContactVo.addressVo.city : '',
        countryCodeSubdivisionVo: {},
        zipCode: this.homeUnitContactVo.addressVo != null ? this.homeUnitContactVo.addressVo.postalCode : '',
        phone: this.homeUnitContactVo.phone,
        email: this.homeUnitContactVo.email
      });
    }
    
    setTimeout(() => {
      if(this.homeUnitContactVo.unitVo) {
        this.selectedUnitDropdownData = this.ddUnitCode.getDropdownDataObjectById(this.homeUnitContactVo.unitVo.id);
      }
      if(this.homeUnitContactVo.addressVo) {
        if(this.homeUnitContactVo.addressVo.countrySubdivisionVo) {
          this.selectedStateDropdownData = this.ddState.getDropdownDataObjectById(this.homeUnitContactVo.addressVo.countrySubdivisionVo.id);
        }
      }
    });
  }

  unitSelectEvent(event) {
    if ( event.desc !== '') {
      this.homeUnitContactForm.get('unitDesc').patchValue(event.desc);
    } else {
      this.homeUnitContactForm.get('unitDesc').patchValue('');
    }
  }

  saveHomeUnitContact() {
    if (this.homeUnitContactVo.id === null) {
      this.homeUnitContactVo.id = 0;
    }

    this.homeUnitContactVo.incidentResourceVo = <IncidentResourceVo> {id: this.incidentResourceId}

    this.homeUnitContactVo.contactName = this.homeUnitContactForm.get('name').value;

    if(this.ddUnitCode.selectedValue.id > 0) {
      this.homeUnitContactVo.unitVo = <OrganizationVo> {id: this.ddUnitCode.selectedValue.id}
    } else {
      this.homeUnitContactVo.unitVo = null;
    }

    if (this.homeUnitContactVo.addressVo === null) {
      this.homeUnitContactVo.addressVo = <AddressVo> {
        id: 0
      }
    }
    
    this.homeUnitContactVo.addressVo.addressLine1 = this.homeUnitContactForm.get('address1').value;
    this.homeUnitContactVo.addressVo.city = this.homeUnitContactForm.get('city').value;

    if(this.ddState.selectedValue.id > 0) {
      this.homeUnitContactVo.addressVo.countrySubdivisionVo = <CountryCodeSubdivisionVo> {id: this.ddState.selectedValue.id}
    } else {
      this.homeUnitContactVo.addressVo.countrySubdivisionVo = null;
    }

    this.homeUnitContactVo.addressVo.postalCode = this.homeUnitContactForm.get('zipCode').value;
    this.homeUnitContactVo.phone = this.homeUnitContactForm.get('phone').value;
    this.homeUnitContactVo.email = this.homeUnitContactForm.get('email').value;

    const resourceHomeUnitContactData = <ResourceHomeUnitContactData> {
      resourceHomeUnitContactVo: this.homeUnitContactVo,
      dialogueVo: null
    }

    this.trainingSpecialistService.saveHomeUnitContact(resourceHomeUnitContactData)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_HOME_UNIT_CONTACT') {
          this.homeUnitContactVo = data['resultObject'] as ResourceHomeUnitContactVo;
        }
      });   

  }

  cancelHomeUnitContact() {
    this.populateHomeUnitContactForm();
  }

}
