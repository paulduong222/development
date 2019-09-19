import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { AdminOfficeService } from 'src/app/service/admin-office.service';
import { AdminOfficeVo } from 'src/app/data/admin-office-vo';
import { AddressVo } from 'src/app/data/address-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { DropdownData } from 'src/app/data/dropdowndata';
import { AdminOfficeData } from 'src/app/data/rest/admin-office-data';
import { NotificationService } from 'src/app/service/notification-service';
import { AdminOfficeGridVo } from 'src/app/data/admin-office-grid-vo';

@Component({
  selector: 'app-admin-office-window',
  templateUrl: './admin-office-window.component.html',
  styleUrls: ['./admin-office-window.component.css']
})
export class AdminOfficeWindowComponent implements OnInit {
  @Output() saveModalEvent = new EventEmitter();
  @ViewChild('ddState') ddState: EisDropdownComponent;
  statesDropdownData = []; // <-- this is collection of dropdownData based on CouuntryCodeSubdivisionVos
  selectedStateDropdownData: DropdownData;

  adminOfficeForm: FormGroup;
  adminOfficeVo = <AdminOfficeVo>{id: 0};
  savedAdminOfficeGridVo = <AdminOfficeGridVo>{id: 0};

  constructor(private modalService: ModalService,
                private formBuilder: FormBuilder,
                private notifyService: NotificationService,
                private adminOfficeService: AdminOfficeService,
                ) {

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

  loadPage() {
    this.initNewAdminOfficeVo();
    setTimeout(() => {
      this.resetForm();
      this.ddState.dropdownData = this.statesDropdownData;
    });
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

  resetForm() {
    this.selectedStateDropdownData = this.ddState.getDropdownDataObjectById(-2);

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
         this.savedAdminOfficeGridVo = data['resultObjectAlternate'] as AdminOfficeGridVo;
         this.modalService.close('admin-office-modal');
         setTimeout(() => {
            this.saveModalEvent.emit(true);
         });
       }
     });

  }

  cancel() {
    this.initNewAdminOfficeVo();
    setTimeout(() => {
      this.resetForm();
    });
  }

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
    this.saveModalEvent.emit(false);
  }
}
