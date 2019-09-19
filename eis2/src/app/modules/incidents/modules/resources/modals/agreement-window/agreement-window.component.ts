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
import { ContractorService } from 'src/app/service/contractor.service';
import { AdminOfficeService } from 'src/app/service/admin-office.service';
import { ContractorAgreementNumberHistoryVo } from 'src/app/data/contractor-agreement-number-history-vo';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { AdminOfficeVo } from 'src/app/data/admin-office-vo';
import { AddressVo } from 'src/app/data/address-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { AdminOfficeWindowComponent } from '../admin-office-window/admin-office-window.component';

@Component({
  selector: 'app-agreement-window',
  templateUrl: './agreement-window.component.html',
  styleUrls: ['./agreement-window.component.css']
})
export class AgreementWindowComponent implements OnInit {
  modalId = 'agreement-window-modal';
  @ViewChild('adminOfficeWindow') adminOfficeWindow: AdminOfficeWindowComponent;
  @ViewChild('ddAdminOffice') ddAdminOffice: EisDropdownComponent;
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  @Output() saveAgreementEvent = new EventEmitter();
  windowLabel = 'Add Agreement ';
  agreementForm: FormGroup;
  contractorVo: ContractorVo;
  contractorAgreementVo;
  adminOfficeData = [];
  selectedAdminOffice = null;

  constructor(private modalService: ModalService
        , private formBuilder: FormBuilder
        , private contractorService: ContractorService
        , private adminOfficeService: AdminOfficeService
        , public incidentSelectorService: IncidentSelectorService
        , private notifyService: NotificationService) {

     this.agreementForm = this.formBuilder.group({
            id: new FormControl(0),
            agreementNumber: new FormControl(''),
            startDate: new FormControl(''),
            endDate: new FormControl(''),
            adminOfficeVo: new FormControl({}),
          });
   }

  ngOnInit() {
  }

  loadWindow() {
    this.windowLabel = 'Add Contract/Agreement for ' + this.contractorVo.name;
    this.loadAdminOfficeList();
  }

  loadAdminOfficeList() {
    this.adminOfficeService.getDropdownList()
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_DD_LIST_ADMIN_OFFICES' ) {
        this.adminOfficeData = data['recordset'] as DropdownData[];
        this.initAgreementVo();
      }
    });
  }

  initAgreementVo() {
    // init new vo
    this.contractorAgreementVo = <ContractorAgreementVo>{
      id: 0
      , agreementNumber: ''
      , startDateVo: <DateTransferVo> {dateString: '', timeString: ''}
      , endDateVo: <DateTransferVo> {dateString: '', timeString: ''}
      , pointOfHire: ''
      , enabled: true
      , deletedDate: null
      , contractorVo: <ContractorVo>{
        id: this.contractorVo.id
      }
      , adminOfficeId: 0
      , adminOfficeVo: <AdminOfficeVo>{
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
      }
      , contractorAgreementNumberHistoryVo: <ContractorAgreementNumberHistoryVo>{
        id: 0
        , contractorAgreementVo: <ContractorAgreementVo>{
          id: 0
        }
        , contractorAgreementId: 0
        , reasonText: ''
        , newAgreementNumber: ''
        , oldAgreementNumber: ''
      }
    };

    setTimeout(() => {
      this.resetForm();
    });
  }

  resetForm() {
    this.selectedAdminOffice = this.ddAdminOffice.getDropdownDataObjectById(-2);
    this.agreementForm.setValue(
      {
        id: this.contractorAgreementVo.id
        , agreementNumber: this.contractorAgreementVo.agreementNumber
        , startDate: this.contractorAgreementVo.startDateVo.dateString
        , endDate: this.contractorAgreementVo.endDateVo.dateString
        , adminOfficeVo: {}
      }
    );

  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

  openAdminOfficeWindow() {
    this.adminOfficeWindow.openModal();
    this.adminOfficeWindow.loadWindow();
  }

  saveAdminOfficeEvent(newAdminOfficeVo: AdminOfficeVo) {
    this.adminOfficeWindow.closeModal();
    let newAdminOfficeRow = <DropdownData>{
      id: newAdminOfficeVo.id
      , code: newAdminOfficeVo.officeName
      , desc: ''
    };

    this.adminOfficeData.push(newAdminOfficeRow);
    this.selectedAdminOffice = this.ddAdminOffice.getDropdownDataObjectById(newAdminOfficeRow.id);
  }

  save() {
    this.contractorAgreementVo.agreementNumber = this.agreementForm.get('agreementNumber').value;
    this.contractorAgreementVo.startDateVo.dateString = this.dtStart.getFormattedDate();
    this.contractorAgreementVo.endDateVo.dateString = this.dtEnd.getFormattedDate();
    this.contractorAgreementVo.adminOfficeVo.id = this.ddAdminOffice.selectedValue.id;
    this.contractorAgreementVo.adminOfficeId = this.ddAdminOffice.selectedValue.id;

    this.contractorService.saveAgreement(this.contractorAgreementVo, null)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_CONTRACTOR_AGREEMENT') {
        let vo = data['resultObject'] as ContractorAgreementVo;

        this.saveAgreementEvent.emit(vo);
      }
    });

  }
}
