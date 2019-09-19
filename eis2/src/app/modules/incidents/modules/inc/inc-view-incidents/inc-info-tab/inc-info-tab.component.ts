import { Component, OnInit, Input, Output, ViewChild, AfterViewInit, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentVo } from 'src/app/data/incident-vo';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';
import { OrganizationVo } from 'src/app/data/organization-vo';
import { AgencyVo } from 'src/app/data/agency-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { EventTypeVo } from 'src/app/data/event-type-vo';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { Subject } from 'rxjs';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { IncidentData } from 'src/app/data/rest/incident-data';
import { IncidentService } from 'src/app/service/incident.service';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-inc-info-tab',
  templateUrl: './inc-info-tab.component.html',
  styleUrls: ['./inc-info-tab.component.css']
})
export class IncInfoTabComponent implements OnInit, AfterViewInit {
  @ViewChild('promptModalIncInfo') promptModalIncInfo: PromptModalComponent;
  @Input() incidentVo: IncidentVo = <IncidentVo>{id: 0, incidentName: ''};
  @Output() incInfoTabProcessingEvent = new EventEmitter();
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  @ViewChild('dtEnd') dtEnd: EisDatepickerComponent;
  @ViewChild('ddEventType') ddEventType: EisDropdownComponent;
  @ViewChild('ddStateType') ddStateType: EisDropdownComponent;
  @ViewChild('ddOrgType') ddOrgType: EisDropdownComponent;
  @ViewChild('ddAgencyType') ddAgencyType: EisDropdownComponent;
  dialogueVo: DialogueVo;
  incForm: FormGroup;
  incidentTag = new Subject<string>();
  incidentTag2 = 'US-';
  currentEvent = '';

  // selected object holders for dropdown
  eventTypeDropdownData: DropdownData;
  stateTypeDropdownData: DropdownData;
  orgTypeDropdownData: DropdownData;
  agencyTypeDropdownData: DropdownData;

  constructor(private fb: FormBuilder
              , public incidentSelectorService: IncidentSelectorService
              , private incidentService: IncidentService
              , private notifyService: NotificationService) {
    this.incForm = this.fb.group({
        id: new FormControl(0),
        incidentName: new FormControl(''),
        incidentNumber: new FormControl(''),
        countryCodeSubdivisionVo: new FormControl({}),
        homeUnitVo: new FormControl({}),
        agencyVo: new FormControl({}),
        startDate: new FormControl(''),
        endDate: new FormControl(''),
        rossId: new FormControl({value: '', disabled: true}),
        incidentDesc: new FormControl(''),
      });
  }

  ngOnInit() {
    // get the default incidentVo from the service
//    this.incidentVo = Object.assign({}, this.incidentSelectorService.currentVo as IncidentVo);
    this.initNewIncidentVo();
  }

  ngAfterViewInit() {
  }

  updateIncidentTag(event) {
    this.incidentTag.next('US-' +
      (this.ddOrgType.selectedValue !== undefined ? this.ddOrgType.selectedValue.code : '') + '-' + this.incidentVo.incidentNumber);
    this.incidentTag2 = 'US-' +
      (this.ddOrgType.selectedValue !== undefined ? this.ddOrgType.selectedValue.code : '') + '-' + this.incidentVo.incidentNumber;
  }

  initNewIncidentVo() {
    this.incidentVo = <IncidentVo>{
      id: 0
      , incidentName: ''
      , incidentNumber: ''
      , rossIncId: ''
      , incidentDescription: ''
      , eventTypeVo: <EventTypeVo>{
        id: 0
      }
      , countryCodeSubdivisionVo: <CountryCodeSubdivisionVo>{
        id: 0
        , countrySubAbbreviation: ''
        , countrySubName: ''
      }
      , homeUnitVo: <OrganizationVo>{
        id: 0
        , unitCode: ''
        , name: ''
      }
      , agencyVo: <AgencyVo>{
        id: 0
        , agencyCd: ''
        , agencyNm: ''
      }
      , incStartDateTransferVo: <DateTransferVo>{
        dateString: ''
        , timeString: ''
      }, incEndDateTransferVo: <DateTransferVo>{
        dateString: ''
        , timeString: ''
      }
    };

    this.incidentTag.next('US-' + '-' + this.incidentVo.incidentNumber);
    this.incidentTag2 = 'US-' + '-' + this.incidentVo.incidentNumber;
  }

  addNew() {
    this.initNewIncidentVo();
    this.resetForm();
  }

  resetForm() {
    // reset these to get around refresh issues when selecting between incidents
    this.eventTypeDropdownData = this.ddEventType.getDropdownDataObjectById(-2);
    this.stateTypeDropdownData = this.ddStateType.getDropdownDataObjectById(-2);
    this.orgTypeDropdownData = this.ddOrgType.getDropdownDataObjectById(-2);
    this.agencyTypeDropdownData = this.ddAgencyType.getDropdownDataObjectById(-2);

    this.incForm.setValue(
      {
        id: this.incidentVo.id
        , incidentName: this.incidentVo.incidentName
        , incidentNumber: this.incidentVo.incidentNumber
        , countryCodeSubdivisionVo: {}
        , homeUnitVo: {}
        , agencyVo: {}
        , startDate: this.incidentVo.incStartDateTransferVo.dateString
        , endDate: this.incidentVo.incEndDateTransferVo.dateString
        , rossId: this.incidentVo.rossIncId
        , incidentDesc: this.incidentVo.incidentDescription
      }
    );
   /* wrapping resetting dropwdown values to get around
      a refresh issue on subsequent select/saves */
   setTimeout(() => {
    this.eventTypeDropdownData = this.ddEventType.getDropdownDataObjectById(this.incidentVo.eventTypeVo.id);
    this.stateTypeDropdownData = this.ddStateType.getDropdownDataObjectById(this.incidentVo.countryCodeSubdivisionVo.id);
    this.orgTypeDropdownData = this.ddOrgType.getDropdownDataObjectById(this.incidentVo.homeUnitVo.id);
    this.agencyTypeDropdownData = this.ddAgencyType.getDropdownDataObjectById(this.incidentVo.agencyVo.id);
    this.incidentTag.next('US-' + this.incidentVo.homeUnitVo.unitCode + '-' + this.incidentVo.incidentNumber);
    this.incidentTag2 = 'US-' + this.incidentVo.homeUnitVo.unitCode + '-' + this.incidentVo.incidentNumber;
   });
  }

  save(resubmit: boolean) {
    if (resubmit === false ) {
      this.dialogueVo = <DialogueVo>{};
    }
    this.incidentVo.incidentName = this.incForm.get('incidentName').value;
    this.incidentVo.incidentNumber = this.incForm.get('incidentNumber').value;
    this.incidentVo.incidentDescription = this.incForm.get('incidentDesc').value;
    this.incidentVo.eventTypeVo.id = this.ddEventType.selectedValue.id;
    this.incidentVo.countryCodeSubdivisionVo.id = this.ddStateType.selectedValue.id;
    this.incidentVo.agencyVo.id = this.ddAgencyType.selectedValue.id;
    this.incidentVo.homeUnitVo.id = this.ddOrgType.selectedValue.id;
    this.incidentVo.incStartDateTransferVo.dateString = this.dtStart.getFormattedDate();
    this.incidentVo.incEndDateTransferVo.dateString = this.dtEnd.getFormattedDate();

    // console.log(JSON.stringify(this.incidentVo));
    const incidentData: IncidentData = <IncidentData>{
      incidentVo: this.incidentVo
      , dialogueVo: this.dialogueVo
    }

    const newRecord = (this.incidentVo.id > 0 ? 'no' : 'yes' );

    this.showMessage('Processing Request', 'Saving Incident Data');
    this.incidentService.save(incidentData)
      .subscribe(data => {
        this.promptModalIncInfo.closeModal();
        this.notifyService.inspectResult(data);
        this.dialogueVo = data as DialogueVo;
        if ( data['courseOfActionVo']['coaName'] === 'CHECK_START_AFTER_SYSTEM_DATE') {
          this.checkStartAfterSystemDateHandler(data);
        }
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_INCIDENT' ) {
          this.incidentVo = data['resultObject'] as IncidentVo;
          this.incInfoTabProcessingEvent.emit({action: 'INCIDENT_SAVED', incidentVo: this.incidentVo, newrecord: newRecord});
        }
      });
  }

  showPrompt(title, msg, btn1, btn2) {
    this.promptModalIncInfo.reset();
    this.promptModalIncInfo.promptTitle = title;
    this.promptModalIncInfo.promptMessage1 = msg;
    this.promptModalIncInfo.button1Label = btn1;
    this.promptModalIncInfo.button2Label = btn2;
    this.promptModalIncInfo.openModal();
  }

  showMessage(title, msg) {
    this.promptModalIncInfo.reset();
    this.promptModalIncInfo.promptTitle = title;
    this.promptModalIncInfo.promptMessage1 = msg;
    this.promptModalIncInfo.openModal();
  }

  checkStartAfterSystemDateHandler(data) {
    this.currentEvent = 'CHECK_START_AFTER_SYSTEM_DATE';
    this.showPrompt('Incident'
                  , data['courseOfActionVo']['promptVo']['messageProperty']
                  , 'Yes'
                  , 'No');
  }

  cancel() {
    if ( this.incidentVo.id > 0 ) {
      this.resetForm();
    } else {
      this.addNew();
    }
  }

  promptActionResultIncInfo(btnEvent) {
    this.promptModalIncInfo.closeModal();
    if ( this.currentEvent === 'CHECK_START_AFTER_SYSTEM_DATE') {
      if ( btnEvent === 'Yes') {
        // PromptResult.Yes == 1
        this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 1;
        this.save(true);
      }
    }
  }

  promptResult(currentEvent, btnEvent ) {
    if ( currentEvent['coaName'] === 'CHECK_START_AFTER_SYSTEM_DATE') {
      if ( btnEvent === 'Yes') {
        // PromptResult.Yes == 1
        this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 1;
        this.save(true);
      }
    }
  }
}
