import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { NotificationService } from 'src/app/service/notification-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { AdminOfficeService } from 'src/app/service/admin-office.service';
import { AdminOfficeVo } from 'src/app/data/admin-office-vo';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { AddressVo } from 'src/app/data/address-vo';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { AdminOfficeData } from 'src/app/data/rest/admin-office-data';

@Component({
  selector: 'app-admin-office-window',
  templateUrl: './admin-office-window.component.html',
  styleUrls: ['./admin-office-window.component.css']
})
export class AdminOfficeWindowComponent implements OnInit {
  modalId = 'admin-office-window-modal';
  @ViewChild('ddState') ddState: EisDropdownComponent;
  @Output() saveAdminOfficeEvent = new EventEmitter();
  adminOfficeForm: FormGroup;
  adminOfficeVo = <AdminOfficeVo>{id: 0};
  selectedState: DropdownData;

  constructor(private modalService: ModalService
    , private formBuilder: FormBuilder
    , public incidentSelectorService: IncidentSelectorService
    , private adminOfficeService: AdminOfficeService
    , private notifyService: NotificationService) {
  }

  ngOnInit() {
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

  loadWindow() {
    this.initNewAdminOfficeVo();
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

    setTimeout(() => {
      this.resetForm();
    });
  }

  resetForm() {
    this.selectedState = this.ddState.getDropdownDataObjectById(-2);

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

  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

  save() {
    this.adminOfficeVo.officeName = this.adminOfficeForm.get('officeName').value;
    this.adminOfficeVo.phone = this.adminOfficeForm.get('phone').value;
    this.adminOfficeVo.addressVo.addressLine1 = this.adminOfficeForm.get('addressLine1').value;
    this.adminOfficeVo.addressVo.addressLine2 = this.adminOfficeForm.get('addressLine2').value;
    this.adminOfficeVo.addressVo.city = this.adminOfficeForm.get('city').value;
    this.adminOfficeVo.addressVo.postalCode = this.adminOfficeForm.get('zip').value;
    this.adminOfficeVo.addressVo.countrySubdivisionVo.id = this.ddState.selectedValue.id;

    const adminOfficeData: AdminOfficeData = <AdminOfficeData>{
      adminOfficeVo: this.adminOfficeVo
    }

    this.adminOfficeService.save(adminOfficeData)
     .subscribe(data => {
       this.notifyService.inspectResult(data);
       if ( data['courseOfActionVo']['coaName'] === 'SAVE_ADMIN_OFFICE' ) {
         const savedVo = data['resultObject'] as AdminOfficeVo;

         setTimeout(() => {
           this.saveAdminOfficeEvent.emit(savedVo);
         });
       }
     });
  }
}
