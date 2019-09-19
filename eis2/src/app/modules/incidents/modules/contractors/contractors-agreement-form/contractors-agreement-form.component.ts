import { Component, OnInit, ViewChild } from '@angular/core';
import { ContractorVo } from 'src/app/data/contractor-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { ContractorAgreementVo } from 'src/app/data/contractor-agreement-vo';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { AdminOfficeVo } from 'src/app/data/admin-office-vo';
import { AddressVo } from 'src/app/data/address-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { DropdownData } from 'src/app/data/dropdowndata';
import { AdminOfficeService } from 'src/app/service/admin-office.service';
import { NotificationService } from 'src/app/service/notification-service';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { ContractorAgreementNumberHistoryVo } from 'src/app/data/contractor-agreement-number-history-vo';
import { ContractorService } from 'src/app/service/contractor.service';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';
import { AgreementReasonWindowComponent } from '../modals/agreement-reason-window/agreement-reason-window.component';
import { AdminOfficeWindowComponent } from '../modals/admin-office-window/admin-office-window.component';
import { AdminOfficeGridVo } from 'src/app/data/admin-office-grid-vo';

@Component({
  selector: 'app-contractors-agreement-form',
  templateUrl: './contractors-agreement-form.component.html',
  styleUrls: ['./contractors-agreement-form.component.css']
})
export class ContractorsAgreementFormComponent implements OnInit {
  @ViewChild('agreementPromptModal') agreementPromptModal: PromptModalComponent;
  @ViewChild('ddAdminOffice') ddAdminOffice: EisDropdownComponent;
  @ViewChild('gridAgreement') gridAgreement: EisGridComponent;
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  @ViewChild('agreementReasonWindow') agreementReasonWindow: AgreementReasonWindowComponent;
  @ViewChild('adminOfficeWindow') adminOfficeWindow: AdminOfficeWindowComponent;

  dialogueVo: DialogueVo;

  // arrays for dropdowns
  adminOfficeDropdownData = []; // <-- this is collection of dropdownData based on adminOfficeGridVos
  statesDropdownData = []; // <-- this is collection of dropdownData based on CouuntryCodeSubdivisionVos

  // selected object for dropdown
  selectedAdminOfficeDropdownData: DropdownData;

  promptMode = '';

  contractorAgreementVos = [];
  contractorVo: ContractorVo;
  agreementForm: FormGroup;
  agreementList = [];

  contractorAgreementVo: ContractorAgreementVo = <ContractorAgreementVo>{id : 0};
  unTouchedtractorAgreementVo: ContractorAgreementVo = <ContractorAgreementVo>{id : 0};
  agreementSelected = false;

  gridColumnDefs = [
    {headerName: 'Contract/Agreement #', field: 'agreementNumber', width: 180, sort: 'asc'},
    {headerName: 'Begin Date', field: 'startDateVo.dateString', width: 100},
    {headerName: 'Expire Date', field: 'endDateVo.dateString', width: 100},
    {headerName: 'Admin Office', field: 'adminOfficeVo.officeName', width: 120},
  ];

  constructor(private formBuilder: FormBuilder,
              private notifyService: NotificationService,
               private adminOfficeService: AdminOfficeService,
               private contractorService: ContractorService,
              ) {
    this.agreementForm = this.formBuilder.group({
      id: new FormControl(0),
      agreementNumber: new FormControl(''),
      startDate: new FormControl(''),
      endDate: new FormControl(''),
      adminOfficeVo: new FormControl({}),
    });

  }

  ngOnInit() {
    this.loadAdminOfficeList();
  }

  loadAdminOfficeList() {
    this.adminOfficeService.getDropdownList()
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_DD_LIST_ADMIN_OFFICES' ) {
        this.adminOfficeDropdownData = data['recordset'] as DropdownData[];
      }
    });
  }

  getDisplayClass() {
    if ( this.contractorVo === undefined || this.contractorVo.id < 1 ) {
      return 'hidden';
    } else {
      return '';
    }
  }

  getTopBarButtonClass(btnName) {
    if ( btnName === 'add') {return ( this.agreementSelected === false ? 'w3-small btn-selected' : 'w3-small' ); }
  }

  setContractorVo(vo: ContractorVo) {
    this.contractorVo = vo;
    this.agreementList = vo.contractorAgreementVos as ContractorAgreementVo[];
    this.add();
  }

  add() {
    // clear selected in grid
    this.gridAgreement.clearSelected();
    this.agreementSelected = false;

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
          id: this.contractorAgreementVo.id
        }
        , contractorAgreementId: this.contractorAgreementVo.id
        , reasonText: ''
        , newAgreementNumber: ''
        , oldAgreementNumber: this.contractorAgreementVo.agreementNumber
      }
    };

    setTimeout(() => {
      this.resetForm();
    });
  }

  delete() {
    if ( this.contractorAgreementVo !== undefined && this.contractorAgreementVo.id > 0 ) {
      // show message to select agreement
      this.promptMode = 'DeleteAgreement';
      this.showPrompt('Contractor Agreement'
          , 'Please you really want to delete the Agreement?'
          , 'Yes'
          , 'No');

    } else {
      // show message to select agreement
      this.promptMode = 'SelectAgreement';
      this.showPrompt('Contractor Agreement'
          , 'Please select an Agreement to delete.'
          , 'Ok'
          , '');
    }
  }

  onSelectAgreement(row: any) {
    this.agreementSelected = false;

    if ( row !== undefined ) {
      this.agreementSelected = true;
      this.unTouchedtractorAgreementVo = Object.assign({}, row);
      this.contractorAgreementVo = Object.assign({}, row);
      this.resetForm();
    }
  }

  resetForm() {
    this.selectedAdminOfficeDropdownData = this.ddAdminOffice.getDropdownDataObjectById(-1);

    if ( this.contractorAgreementVo !== undefined) {

      this.agreementForm.setValue(
        {
          id: this.contractorAgreementVo.id
          , agreementNumber: this.contractorAgreementVo.agreementNumber
          , startDate: this.contractorAgreementVo.startDateVo.dateString
          , endDate: this.contractorAgreementVo.endDateVo.dateString
          , adminOfficeVo: {}
        }
      );

      if ( this.contractorAgreementVo.adminOfficeVo !== null &&
         this.contractorAgreementVo.adminOfficeVo.id > 0 ) {
        this.selectedAdminOfficeDropdownData = this.ddAdminOffice.getDropdownDataObjectById(this.contractorAgreementVo.adminOfficeVo.id);
      }

    }
  }

  saveAgreement(resubmit: boolean) {
    if (resubmit === false ) {
      this.dialogueVo = <DialogueVo>{};
    }

    console.log('vo to save');
    console.log(JSON.stringify(this.contractorAgreementVo));
    this.contractorAgreementVo.agreementNumber = this.agreementForm.get('agreementNumber').value;
    this.contractorAgreementVo.startDateVo.dateString = this.dtStart.getFormattedDate();
    this.contractorAgreementVo.endDateVo.dateString = this.dtEnd.getFormattedDate();
    this.contractorAgreementVo.adminOfficeVo.id = this.ddAdminOffice.selectedValue.id;
    this.contractorAgreementVo.adminOfficeId = this.ddAdminOffice.selectedValue.id;

    this.contractorService.saveAgreement(this.contractorAgreementVo, this.dialogueVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'NUMBER_HISTORY') {
        this.dialogueVo = data as DialogueVo;
        this.numberHistoryPromptHandler(data);
      }
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_CONTRACTOR_AGREEMENT') {
        // updated grid vo
        let vo = data['resultObject'] as ContractorAgreementVo;

        this.gridAgreement.updateRowById(vo);

        this.add();
        // this.gridAgreement.setSelectedRow('', vo.id);

      }
    });
  }

  numberHistoryPromptHandler(data) {
    this.promptMode = 'NumberHistoryPrompt';
    this.showPrompt('Contractor Agreement'
                  , data['courseOfActionVo']['promptVo']['messageProperty']
                  , 'Yes'
                  , 'No');

  }

  cancel() {
    if ( this.contractorAgreementVo !== undefined && this.contractorAgreementVo.id > 0 ) {
      this.contractorAgreementVo = Object.assign({}, this.unTouchedtractorAgreementVo);
      this.resetForm();
    } else {
      this.add();
    }

  }

  proceedWithDelete() {
    this.contractorService.deleteAgreement(this.contractorAgreementVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'DELETE_CONTRACTOR_AGREEMENT') {
        const vo = data['resultObject'] as ContractorAgreementVo;

        const idx = this.agreementList.findIndex(row => row.id === vo.id);

        // remove from grid
         this.gridAgreement.removeSelectedRows();

         // remove from contractor agreement List
        if ( idx > -1 ) {
          this.agreementList.splice(idx , 1);
        }

        // add new
        this.add();
      }
    });
  }

  showMessage(title, msg) {
    this.agreementPromptModal.reset();
    this.agreementPromptModal.promptTitle = title;
    this.agreementPromptModal.promptMessage1 = msg;
    this.agreementPromptModal.openModal();
  }

  showPrompt(title, msg, btn1, btn2) {
    this.agreementPromptModal.reset();
    this.agreementPromptModal.promptTitle = title;
    this.agreementPromptModal.promptMessage1 = msg;
    this.agreementPromptModal.button1Label = btn1;
    this.agreementPromptModal.button2Label = btn2;
    this.agreementPromptModal.openModal();
  }

  openAdminOfficeWindow() {
    this.adminOfficeWindow.statesDropdownData = this.statesDropdownData;
    this.adminOfficeWindow.openModal('admin-office-modal');
    this.adminOfficeWindow.loadPage();
  }

  saveAdminOfficeEvent(event) {
    if ( event === true ) {
      const gridVo = this.adminOfficeWindow.savedAdminOfficeGridVo as AdminOfficeGridVo;
      let newAdminOfficeRow = new DropdownData();
      newAdminOfficeRow.id = gridVo.id;
      newAdminOfficeRow.code = gridVo.officeName;
      newAdminOfficeRow.desc = '';
      newAdminOfficeRow.reftype = 'adminoffice';

      this.adminOfficeDropdownData.push(newAdminOfficeRow);

      setTimeout(() => {
        this.selectedAdminOfficeDropdownData = this.ddAdminOffice.getDropdownDataObjectById(newAdminOfficeRow.id);
      });
    }
  }

  promptActionResult(evt: any) {
    this.agreementPromptModal.closeModal();
    if ( this.promptMode === 'DeleteAgreement') {
      if (evt === 'Yes') {
        this.proceedWithDelete();
      }
    }
    if ( this.promptMode === 'NumberHistoryPrompt') {
      if (evt === 'Yes') {
        // PromptResult.Yes == 1
        this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 1;

        // open window to capture change reason
        this.agreementReasonWindow.openModal('agreement-reason-modal');
        this.agreementReasonWindow.loadPage();

      }
    }
  }

  saveAgreementReason(event) {
    if ( event === true ) {
        // todo: need to build numberhistoryvo in agreementvo since it is null
        // from onSelectRow
        this.contractorAgreementVo.contractorAgreementNumberHistoryVo = <ContractorAgreementNumberHistoryVo>{
          id: 0
          , contractorAgreementVo: <ContractorAgreementVo>{
            id: this.contractorAgreementVo.id
          }
          , contractorAgreementId: this.contractorAgreementVo.id
          , reasonText: this.agreementReasonWindow.changeReason
          , newAgreementNumber: this.agreementForm.get('agreementNumber').value
          , oldAgreementNumber: this.unTouchedtractorAgreementVo.agreementNumber
        };

        this.saveAgreement(true);
    }
  }
}
