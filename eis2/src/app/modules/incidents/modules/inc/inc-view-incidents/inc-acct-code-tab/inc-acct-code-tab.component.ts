import { Component, OnInit, Input, Output, ViewChild, EventEmitter, AfterViewInit } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { IncidentAccountCodeVo} from 'src/app/data/incident-account-code-vo';
import { IncidentAccountCodeGridVo} from 'src/app/data/incident-account-code-grid-vo';
import { AccountCodeVo} from 'src/app/data/account-code-vo';
import { AgencyVo} from 'src/app/data/agency-vo';
import { RegionCodeVo} from 'src/app/data/region-code-vo';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { IncidentVo } from 'src/app/data/incident-vo';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { IncidentService } from 'src/app/service/incident.service';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-inc-acct-code-tab',
  templateUrl: './inc-acct-code-tab.component.html',
  styleUrls: ['./inc-acct-code-tab.component.css']
})
export class IncAcctCodeTabComponent implements OnInit, AfterViewInit {
  @ViewChild('promptModalIncAcctCode') promptModalIncAcctCode: PromptModalComponent;
  @Output() incAcctCodeProcessingEvent = new EventEmitter();
  @ViewChild('acctCodeGrid') acctCodeGrid: EisGridComponent;
  @ViewChild('ddAgencyType') ddAgencyType: EisDropdownComponent;
  @ViewChild('ddRegionType') ddRegionType: EisDropdownComponent;
  @ViewChild('ddAccrualType') ddAccrualType: EisDropdownComponent;
  @Input() incidentAccountCodeVo: IncidentAccountCodeVo = <IncidentAccountCodeVo>{id: 0};
  incidentAccountingCodeGridVos: IncidentAccountCodeGridVo[];
  dialogueVo: DialogueVo;
  acctCodeForm: FormGroup;
  maxCodeLength = 50;
  maxCodeLengthWfFed = 4;
  maxCodeLengthUsfs = 6;
  agencyTypeData: DropdownData[] = [];
  regionTypeData: DropdownData[] = [];
  accrualTypeData: DropdownData[] = [];
  incidentVo: IncidentVo;
  currentAgency = '';
  currentEvent = '';

  // selected object holders for dropdown
  agencyTypeDropdownData: DropdownData;
  regionTypeDropdownData: DropdownData;
  accrualTypeDropdownData: DropdownData;

  public gridColumnDefs2 = [
    {headerName: 'Accounting Code', field: 'accountCode', width: 220, sort: 'asc'},
    {headerName: 'Agency', field: 'agencyCode', width: 220},
    {headerName: 'FS Region/Unit', field: 'region', width: 220},
    {headerName: 'Default', field: 'defaultFlag', width: 220, filter: false, cellRenderer: 'checkboxRenderer'},
  ];

  constructor(private fb: FormBuilder
              , private incidentSelectorService: IncidentSelectorService
              , private incidentService: IncidentService
              , private referenceDataService: ReferenceDataService
              , private notifyService: NotificationService) {
    this.acctCodeForm = this.fb.group({
      id: new FormControl(0),
      incidentDefault: new FormControl(false),
      agencyVo: new FormControl({}),
      regionVo: new FormControl({}),
      accountCode: new FormControl(''),
      accrualCodeVo: new FormControl({})
    });
  }

  ngOnInit() {
    this.getRegionCodes();
  }

  ngAfterViewInit() {
  }

  getRegionCodes() {
    this.referenceDataService.getRegionList()
      .subscribe(data => {
        if ( data['courseOfActionVo']['coaName'] === 'GET_REGIONS') {
          this.regionTypeData = data['recordset'] as DropdownData[];
        }
      });
  }

  resetAgencyList() {
    this.agencyTypeData = [];
    const is_wf = (this.incidentVo.eventTypeVo.eventTypeCd === 'WF' ? true : false);
    const wf_excludedList = 'USFS,BLM,BIA,FWS,NPS';
    this.incidentSelectorService.agencyTypeData.forEach(row => {
      if (is_wf === true ) {
        if ( wf_excludedList.indexOf(row.code) > -1 ) {
          // exclude it
        } else {
          this.agencyTypeData.push(Object.assign({}, row));
        }
      } else {
        this.agencyTypeData.push(Object.assign({}, row));
      }
    });

  }

  buildGridVos(vos: IncidentAccountCodeVo[]) {
    this.incidentAccountingCodeGridVos = [];
    vos.forEach(row => {
      this.incidentAccountingCodeGridVos.push(<IncidentAccountCodeGridVo>{
        id: row.id,
        accountCode: row.accountCodeVo.accountCode,
        defaultFlag: row.defaultFlag,
        agencyCode: row.accountCodeVo.agencyVo.agencyCd,
        region: row.accountCodeVo.regionUnitVo.code
      });
      let accrualDropdownData = new DropdownData();
      accrualDropdownData.id = row.id;
      accrualDropdownData.code = row.accountCodeVo.accountCode;
      accrualDropdownData.desc = row.accountCodeVo.agencyVo.agencyCd + ' ' + this.incidentVo.incidentName;
      this.accrualTypeData.push(accrualDropdownData);
    });
  }

  isDropdownDisabled(dropdownName): boolean {
    if ( this.currentAgency === '') {
      return true;
    }

    if ( dropdownName === 'ddAccrualCode') {
      return false;
    }

    if ( dropdownName === 'ddRegionType') {
      if ( this.currentAgency !== 'FED' && this.currentAgency !== 'USFS') {
        return true;
      }
    }

    return false;
  }

  initNewVo() {
    this.currentAgency = '';

    this.incidentAccountCodeVo = <IncidentAccountCodeVo>{
      id: 0
      , defaultFlag: false
      , accountCodeVo: <AccountCodeVo>{
        id: 0
        , accountCode: ''
        , agencyVo: <AgencyVo>{
          id: 0
          , agencyCd: ''
          , agencyNm: ''
        }, regionUnitVo: <RegionCodeVo>{
          id: 0
          , code: ''
        }
      }
      , accrualAccountCode: ''
      , accrualIncidentAccountCodeVo: <IncidentAccountCodeVo>{
        id: 0
        , accountCodeVo: <AccountCodeVo>{
          id: 0
          , accountCode: ''
        }
      }
    }
  }

  resetForm() {
    // this.ddAgencyType.activeItem = -1;
    this.agencyTypeDropdownData = this.ddAgencyType.getDropdownDataObjectById(-2);
    this.regionTypeDropdownData = this.ddRegionType.getDropdownDataObjectById(-2);
    this.accrualTypeDropdownData = this.ddAccrualType.getDropdownDataObjectById(-2);
    this.maxCodeLength = 50;
    this.acctCodeForm.setValue(
      {
        id: this.incidentAccountCodeVo.id,
        incidentDefault: this.incidentAccountCodeVo.defaultFlag,
        regionVo: {},
        agencyVo: {},
        accountCode: this.incidentAccountCodeVo.accountCodeVo.accountCode,
        accrualCodeVo: {}
      }
    );

    this.setRegionDisabledState(this.incidentAccountCodeVo.accountCodeVo.agencyVo.agencyCd);

    if ( this.incidentAccountCodeVo.id === 0 ) {
      this.acctCodeForm.controls['accountCode'].disable();
    } else {
      this.acctCodeForm.controls['accountCode'].enable();
    }

    if ( this.incidentVo.eventTypeVo.eventTypeCd === 'WF' &&
          this.incidentAccountCodeVo.accountCodeVo.agencyVo.agencyCd === 'FED') {
      this.maxCodeLength = this.maxCodeLengthWfFed;
    }
    if ( this.incidentAccountCodeVo.accountCodeVo.agencyVo.agencyCd === 'USFS') {
      this.maxCodeLength = this.maxCodeLengthUsfs;
    }

   // console.log(this.incidentAccountCodeVo.accrualIncidentAccountCodeVo.accountCodeVo.accountCode);
    /* wrapping resetting dropwdown values to get around
        a refresh issue on subsequent select/saves */
    setTimeout(() => {
      this.agencyTypeDropdownData = this.ddAgencyType.getDropdownDataObjectById(this.incidentAccountCodeVo.accountCodeVo.agencyVo.id);
      this.regionTypeDropdownData = this.ddRegionType.getDropdownDataObjectById(this.incidentAccountCodeVo.accountCodeVo.regionUnitVo.id);
      this.accrualTypeDropdownData =
      this.ddAccrualType.getDropdownDataObjectById(this.incidentAccountCodeVo.accrualIncidentAccountCodeVo.id);
    });

  }

  setRegionDisabledState(agencyCode) {
    const val = (agencyCode === 'USFS' || agencyCode === 'FED' ? true : false);
    if ( val === true) { this.ddRegionType.dropdownDisabled = false; }
    if ( val === false) { this.ddRegionType.dropdownDisabled = true; }
  }

  onSelectAccountingCode(row) {
    if ( row !== undefined ) {
      this.getIncidentAccountCodeById(row.id);
    }
  }

  getIncidentAccountCodeById(id) {
    if ( id !== undefined && id > 0 ) {
      this.incidentVo.incidentAccountCodeVos.forEach(row => {
        if (row.id === id ) {
          this.incidentAccountCodeVo = Object.assign({}, row);
          this.currentAgency = this.incidentAccountCodeVo.accountCodeVo.agencyVo.agencyCd;
          if ( this.incidentAccountCodeVo.accrualIncidentAccountCodeVo === null ) {
            console.log('getIncidentAccountCodeById is accrual vo is null');
            this.incidentAccountCodeVo.accrualIncidentAccountCodeVo = <IncidentAccountCodeVo>{
              id: 0
              , accountCodeVo: <AccountCodeVo>{
                id: 0
                , accountCode: ''
              }
            };
          }
          this.resetForm();
        }
      });
    }
  }

  add() {
    this.acctCodeGrid.clearSelected();
    this.initNewVo();
    this.resetForm();
  }

  save(resubmit: boolean) {
    this.incidentAccountCodeVo.incidentVo = <IncidentVo>{id: this.incidentVo.id};
    this.incidentAccountCodeVo.defaultFlag = this.acctCodeForm.get('incidentDefault').value;
    this.incidentAccountCodeVo.accountCodeVo.accountCode = this.acctCodeForm.get('accountCode').value;
    this.incidentAccountCodeVo.accountCodeVo.agencyVo.id = this.ddAgencyType.selectedValue.id;
    this.incidentAccountCodeVo.accountCodeVo.agencyVo.agencyCd = this.ddAgencyType.selectedValue.code;
    this.incidentAccountCodeVo.accountCodeVo.agencyVo.agencyNm = this.ddAgencyType.selectedValue.desc;
    this.incidentAccountCodeVo.accountCodeVo.regionUnitVo.id = this.ddRegionType.selectedValue.id;
    if ( this.incidentAccountCodeVo.accrualIncidentAccountCodeVo === null ) {
      this.incidentAccountCodeVo.accrualIncidentAccountCodeVo = <IncidentAccountCodeVo>{
        id: 0
        , accountCodeVo: <AccountCodeVo>{
          id: 0
          , accountCode: ''
        }
      };
    }
    if ( this.ddAccrualType.selectedValue.id > 0 ) {
      this.incidentAccountCodeVo.accrualIncidentAccountCodeVo.id = this.ddAccrualType.selectedValue.id;
      this.incidentAccountCodeVo.accrualIncidentAccountCodeVo.accountCodeVo.accountCode = this.ddAccrualType.selectedValue.code;
      this.incidentAccountCodeVo.accrualAccountCode = this.ddAccrualType.selectedValue.code;
    } else {
      this.incidentAccountCodeVo.accrualIncidentAccountCodeVo.id = 0;
      this.incidentAccountCodeVo.accrualIncidentAccountCodeVo.accountCodeVo.accountCode =
          this.acctCodeForm.get('accountCode').value;
      this.incidentAccountCodeVo.accrualAccountCode = this.acctCodeForm.get('accountCode').value;
      // this.incidentAccountCodeVo.accrualAccountCode = this.acctCodeForm.get('accountCode').value;
    }

    if (resubmit === false ) {
      this.dialogueVo = <DialogueVo>{};
    }

    this.incidentService.saveIncidentAccountCode(
      this.incidentVo.id, this.incidentAccountCodeVo, this.dialogueVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        this.dialogueVo = data as DialogueVo;
        if ( data['courseOfActionVo']['coaName'] === 'CHECK_FIRST_CODE_IS_DEFAULT'
              && data['courseOfActionVo']['coaType'] === 'PROMPT') {
            this.checkFirstCodeIsDefaultHandler(data);
        }
        if ( data['courseOfActionVo']['coaName'] === 'CHECK_NEW_DEFAULT'
              && data['courseOfActionVo']['coaType'] === 'PROMPT') {
            this.checkNewDefaultHandler(data);
        }
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_INCIDENT_ACCOUNT_CODE') {
          this.saveIncidentAccountCodeHandler(data);
        }
        if ( data['courseOfActionVo']['coaName'] === 'CHECK_ACCT_CODE_ALREADY_DEFINED_FOR_AGENCY') {
          this.checkAcctCodeForAgencyHandler(data);
        }
        if ( data['courseOfActionVo']['coaName'] === 'CHECK_ACCT_CODE_NOT_POSTED') {
          this.checkAcctCodeNotPostedHandler(data);
        }
        if ( data['courseOfActionVo']['coaName'] === 'CHECK_SAME_AGENCY_CODE') {
          this.checkSameAgencyCodeHandler(data);
        }
      });
  }

  showPrompt(title, msg, btn1, btn2) {
    this.promptModalIncAcctCode.reset();
    this.promptModalIncAcctCode.promptTitle = title;
    this.promptModalIncAcctCode.promptMessage1 = msg;
    this.promptModalIncAcctCode.button1Label = btn1;
    this.promptModalIncAcctCode.button2Label = btn2;
    this.promptModalIncAcctCode.openModal();
  }

  checkFirstCodeIsDefaultHandler(data) {
    this.currentEvent = 'CHECK_FIRST_CODE_IS_DEFAULT';
    this.showPrompt('Incident Account Codes'
                  , data['courseOfActionVo']['promptVo']['messageProperty']
                  , 'Ok'
                  , '');
  }

  checkNewDefaultHandler(data) {
    this.currentEvent = 'CHECK_NEW_DEFAULT';
    this.showPrompt('Incident Account Codes'
                  , data['courseOfActionVo']['promptVo']['messageProperty']
                  , 'Yes'
                  , 'No');
  }

  checkAcctCodeForAgencyHandler(data) {
    this.currentEvent = 'CHECK_ACCT_CODE_ALREADY_DEFINED_FOR_AGENCY';
    this.showPrompt('Incident Account Codes'
                  , data['courseOfActionVo']['promptVo']['messageProperty']
                  , 'Yes'
                  , 'No');
  }

  checkSameAgencyCodeHandler(data) {
    this.currentEvent = 'CHECK_SAME_AGENCY_CODE';
    this.showPrompt('Incident Account Codes'
                  , data['courseOfActionVo']['promptVo']['messageProperty']
                  , 'Yes'
                  , 'No');
  }
  checkAcctCodeNotPostedHandler(data) {
    this.currentEvent = 'CHECK_ACCT_CODE_NOT_POSTED';
    this.showPrompt('Incident Account Codes'
                  , data['courseOfActionVo']['promptVo']['messageProperty']
                  , 'Yes'
                  , 'No');
  }

  saveIncidentAccountCodeHandler(data) {
    let bfound = false;
    this.incidentAccountCodeVo = data['resultObject'] as IncidentAccountCodeVo;
    let originalDefaultRow = {};
    let gridRow = {
      id: this.incidentAccountCodeVo.id
      , accountCode: this.incidentAccountCodeVo.accountCodeVo.accountCode
      , defaultFlag: this.incidentAccountCodeVo.defaultFlag
      , agencyCode: this.incidentAccountCodeVo.accountCodeVo.agencyVo.agencyCd
      , region: this.incidentAccountCodeVo.accountCodeVo.regionUnitVo.code
    };
    this.incidentAccountingCodeGridVos.forEach(row => {
      if ( row.id === this.incidentAccountCodeVo.id ) {
        row.accountCode = this.incidentAccountCodeVo.accountCodeVo.accountCode;
        row.defaultFlag = this.incidentAccountCodeVo.defaultFlag;
        row.agencyCode = this.incidentAccountCodeVo.accountCodeVo.agencyVo.agencyCd;
        row.region = this.incidentAccountCodeVo.accountCodeVo.regionUnitVo.code;
      } else {
        if (row.defaultFlag === true && this.incidentAccountCodeVo.defaultFlag === true ) {
          // new row is the default, set orginal row with flag false
          originalDefaultRow = Object.assign({}, row);
          originalDefaultRow['defaultFlag'] = false;
        }
      }
    });

    this.acctCodeGrid.updateRowById(gridRow);
    if ( originalDefaultRow !== {}) {
      this.acctCodeGrid.updateRowById(originalDefaultRow);

      // update in incidentvo.vos
      const origDefaultIdx = this.incidentVo.incidentAccountCodeVos.findIndex(row => row.id === originalDefaultRow['id']);
      if ( origDefaultIdx > -1 ) {
        this.incidentVo.incidentAccountCodeVos[origDefaultIdx].defaultFlag = false;
        }
      }


    // update accrual dropdown data list
    this.accrualTypeData.forEach(row => {
      if (row.id === this.incidentAccountCodeVo.id) {
        bfound = true;
        row.code = this.incidentAccountCodeVo.accountCodeVo.accountCode;
        row.desc = this.incidentAccountCodeVo.accountCodeVo.agencyVo.agencyCd + ' ' + this.incidentVo.incidentName;
      }
    });

    if (bfound === false ) {
      this.accrualTypeData.push({
        id: this.incidentAccountCodeVo.id
        , code: this.incidentAccountCodeVo.accountCodeVo.accountCode
        , desc: this.incidentAccountCodeVo.accountCodeVo.agencyVo.agencyCd + ' ' + this.incidentVo.incidentName
      });
    }

    // update incidentvo.iacs list
    // update incidentAccountCodeVo in incidentVo.vos
    bfound = false;
    const iacIdx = this.incidentVo.incidentAccountCodeVos.findIndex(row => row.id === this.incidentAccountCodeVo.id);
    if ( iacIdx > -1 ) {
      bfound = true;
      this.incidentVo.incidentAccountCodeVos[iacIdx] = Object.assign({}, this.incidentAccountCodeVo);
    }

    if (bfound === false ) {
      this.incidentVo.incidentAccountCodeVos.push(this.incidentAccountCodeVo);
    }

    // update incident grid
    if ( this.incidentAccountCodeVo.defaultFlag === true ) {
      // update incident row in incident grid
      this.incAcctCodeProcessingEvent.emit({action: 'updateIncAcctCodeInGrid'
        , incidentId: this.incidentVo.id
        , incidentAccountCodeVo: this.incidentAccountCodeVo});
    }

    // notify parent incidentVo of updated list
    this.incAcctCodeProcessingEvent.emit({action: 'updateIncAcctCodeVos'
    , iacVos: this.incidentVo.incidentAccountCodeVos});

    this.add();
  }

  promptActionResultAcctCode(btnEvent ) {
    this.promptModalIncAcctCode.closeModal();
    if ( this.currentEvent === 'CHECK_FIRST_CODE_IS_DEFAULT') {
      if ( btnEvent === 'Ok') {
        // PromptResult.OK == 4
        this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 4;
        this.save(true);
      }
    }
    if ( this.currentEvent === 'CHECK_NEW_DEFAULT') {
      if ( btnEvent === 'Yes') {
        // PromptResult.Yes == 1
        this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 1;
        this.save(true);
      }
    }
    if ( this.currentEvent === 'CHECK_ACCT_CODE_ALREADY_DEFINED_FOR_AGENCY') {
      if ( btnEvent === 'Yes') {
        // PromptResult.Yes == 1
        this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 1;
        this.save(true);
      }
    }
    if ( this.currentEvent === 'CHECK_ACCT_CODE_NOT_POSTED') {
      if ( btnEvent === 'Yes') {
        // PromptResult.Yes == 1
        this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 1;
        this.save(true);
      }
    }
    if ( this.currentEvent === 'CHECK_SAME_AGENCY_CODE') {
      if ( btnEvent === 'Yes') {
        // PromptResult.Yes == 1
        this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 1;
        this.save(true);
      }
    }
    if ( this.currentEvent === 'PROMPT_DELETE_IAC') {
      if ( btnEvent === 'Yes') {
        this.proceedWithDelete();
      }
    }
  }

  proceedWithDelete() {
    this.incidentService.deleteIncidentAccountCode(this.incidentAccountCodeVo.id)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_INCIDENT_ACCOUNT_CODE') {

          // check other incident account code vos to see if its accrual code is this record
          // if so, reset back to original code
          this.incidentVo.incidentAccountCodeVos.forEach(row => {
            if ( row.accrualIncidentAccountCodeVo.id === this.incidentAccountCodeVo.id ) {
              row.accrualIncidentAccountCodeVo.id = row.id;
              row.accrualAccountCode = row.accountCodeVo.accountCode;
            }
          });

          // remvoe from iacgridvos list
          let index = this.incidentAccountingCodeGridVos.findIndex(row => row.id === this.incidentAccountCodeVo.id);
          this.incidentAccountingCodeGridVos.splice(index, 1);

          // remvoe from iacvos list
          index = this.incidentVo.incidentAccountCodeVos.findIndex( row => row.id === this.incidentAccountCodeVo.id);
          if ( index > -1) {
            this.incidentVo.incidentAccountCodeVos.splice(index, 1);
          }

          // remove from accrual list
          index = this.accrualTypeData.findIndex(row => row.id === this.incidentAccountCodeVo.id);
          this.accrualTypeData.splice(index, 1);

          // remove from grid
          this.acctCodeGrid.removeSelectedRows();

          // notify parent incidentVo of updated list
          this.incAcctCodeProcessingEvent.emit({action: 'updateIncAcctCodeVos'
            , iacVos: this.incidentVo.incidentAccountCodeVos});

          // update incident grid if this was only record
          this.incAcctCodeProcessingEvent.emit({action: 'updateIncAcctCodeInGrid'
            , incidentId: this.incidentVo.id
            , incidentAccountCodeVo: {
              id: 0
              , accountCodeVo: <AccountCodeVo>{
                id: 0
                , accountCode: ''
                , agencyVo: <AgencyVo>{
                  id: 0
                  , agencyCd: ''
                }
              }
              , accrualAccountCode: ''
            }});


          this.add();
        }
    });
  }

  cancel() {
    if ( this.incidentAccountCodeVo.id > 0 ) {
      this.incidentVo.incidentAccountCodeVos.forEach(row => {
        if (row.id === this.incidentAccountCodeVo.id ) {
          this.incidentAccountCodeVo = Object.assign({}, row);
          this.resetForm();
        }
      });
    } else {
      this.add();
    }
  }

  delete() {
    if (this.incidentAccountCodeVo.id < 1 ) {
      this.showPrompt('Incident Account Codes'
                      , 'Please select an Incident Accounting Code to delete.'
                      , 'Ok'
                      , '');
    } else {
      this.currentEvent = 'PROMPT_DELETE_IAC';
      this.showPrompt('Confirm Delete'
                      , 'Do you really want to remove the Incident Account Code '
                        + this.incidentAccountCodeVo.accountCodeVo.accountCode + '?'
                      , 'Yes'
                      , 'No');
    }
  }

  agencySelectEvent(event) {
    let accountCodeValue = this.acctCodeForm.get('accountCode').value as string;
    this.maxCodeLength = 50; // default
//    console.log(event.code);
    if ( event.code !== undefined && event.code !== '') {
      this.currentAgency = event.code;
      if ( this.incidentAccountCodeVo.id === 0 ) {
        this.acctCodeForm.controls['accountCode'].enable();
      }
    } else {
      this.currentAgency = event.code;
      if ( this.incidentAccountCodeVo.id === 0 ) {
        this.acctCodeForm.controls['accountCode'].disable();
      }
    }

   if (event.code !== undefined && event.code === 'FED' && this.incidentVo.eventTypeVo.eventTypeCd === 'WF') {
      this.maxCodeLength = this.maxCodeLengthWfFed;
      if ( accountCodeValue.length > this.maxCodeLength) {
        accountCodeValue = accountCodeValue.substr(0, this.maxCodeLength);
        this.acctCodeForm.controls['accountCode'].setValue(accountCodeValue);
      }
    }
    if ( event.code !== undefined && event.code === 'USFS') {
      this.maxCodeLength = this.maxCodeLengthUsfs;
      if ( accountCodeValue.length > this.maxCodeLength) {
        accountCodeValue = accountCodeValue.substr(0, this.maxCodeLength);
        this.acctCodeForm.controls['accountCode'].setValue(accountCodeValue);
      }
    }
//    this.setRegionDisabledState(event.code as string);
 }
}
