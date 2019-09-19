import { Component, Input, Output, OnInit, ViewChild } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentResourceVo } from 'src/app/data/incident-resource-vo';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { AssignmentTimePostVo } from 'src/app/data/assignment-time-post-vo';
import { IncidentAccountCodeVo } from 'src/app/data/incident-account-code-vo';
import { KindVo } from 'src/app/data/kind-vo';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { SpecialPayVo } from 'src/app/data/special-pay-vo';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';

@Component({
  selector: 'app-time-overhead',
  templateUrl: './time-overhead.component.html',
  styleUrls: ['./time-overhead.component.css']
})
export class TimeOverheadComponent implements OnInit {
  @Input() timeData = [];
  @Input() rateClassRateData = [];
  @Input() rateClassRateVos = [];
  @Input() specialPayVos = [];

  @ViewChild('ddIncAcctCode') ddIncAcctCode: EisDropdownComponent;
  @ViewChild('ddItemCode') ddItemCode: EisDropdownComponent;
  @ViewChild('ddStart') ddStart: EisDropdownComponent;
  @ViewChild('ddStop') ddStop: EisDropdownComponent;
  @ViewChild('ddSpecialPay') ddSpecialPay: EisDropdownComponent;
  @ViewChild('ddAdRate') ddAdRate: EisDropdownComponent;
  @ViewChild('gridOverhead') gridOverhead: EisGridComponent;
  @ViewChild('dtPostDate') dtPostDate: EisDatepickerComponent;

  specialPayData = [];

  public incidentResourceVo: IncidentResourceVo = null;

  resourceLabel = '';
  timePostingList = [];
  gridColumnDefs = [
    {headerName: 'Date', field: 'postDateString', width: 100, sort: 'asc'},
    {headerName: 'Start', field: 'postStartTime', width: 100},
    {headerName: 'Stop', field: 'postStopTime', width: 100},
    {headerName: 'Item Code', field: 'itemCode', width: 135},
    {headerName: 'Special Rate', field: 'specialPay', width: 140},
    {headerName: 'Quantity', field: 'quantity', width: 100},
    {headerName: 'Accounting Code', field: 'iac', width: 150 },
    {headerName: 'Invoiced', field: 'invoiced' },
  ];

  timePostForm: FormGroup;
  assignmentTimePostVo = <AssignmentTimePostVo>{id: 0};
  selectedIncidentAccountCode: DropdownData;
  selectedItemCode: DropdownData;
  selectedStartTime: DropdownData;
  selectedStopTime: DropdownData;
  selectedSpecialPay: DropdownData;
  selectedAdClass: DropdownData;

  constructor( private formBuilder: FormBuilder
              , public incidentSelectorService: IncidentSelectorService
              , private notifyService: NotificationService) {}

  ngOnInit() {
    this.timePostForm = this.formBuilder.group({
      postDate: new FormControl({value: '', disabled: false})
      , startTime: new FormControl({value: '', disabled: false})
      , stopTime: new FormControl({value: '', disabled: false})
      , quantity: new FormControl({value: '', disabled: true})
      , otherRate: new FormControl({value: '', disabled: false})
      , adRate: new FormControl({value: '', disabled: true})
      , trainee: new FormControl({value: false, disabled: false})
      , postStartTimeOnly: new FormControl({value: false, disabled: false})
    });
  }

  getOtherFieldsClass() {
    if ( this.incidentResourceVo !== null && 
        this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType.valueOf() === 'OTHER') {
          return '';
    } else { return 'hidden'; }
  }

  getAdFieldsClass() {
    if ( this.incidentResourceVo !== null && 
      this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType.valueOf() === 'AD') {
      return '';
    } else { return 'hidden'; }
  }

  getStartStopClass() {
    if ( this.ddSpecialPay.selectedValue !== undefined && this.ddSpecialPay.selectedValue !== null ) {
      const code = this.ddSpecialPay.selectedValue.code;
      if ( code === 'GUARANTEE') {
        this.timePostForm.controls['quantity'].enable();
        return 'hidden';
      }
    }
    this.timePostForm.controls['quantity'].disable();
    return '';
  }

  reset() {
    this.specialPayData = [];
    if ( this.incidentResourceVo !== null) {
      this.specialPayVos.forEach(sp => {
        const spData = {
            id: sp.id
            , code: sp.description
            , desc: ''
        };
        if (this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType.valueOf() === 'FED'
              && sp.availableToFed === true) {
          this.specialPayData.push(spData);
        }
        if (this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType.valueOf() === 'AD'
              && sp.availableToAd === true) {
          this.specialPayData.push(spData);
        }
        if (this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType.valueOf() === 'OTHER'
              && sp.availableToOther === true) {
          this.specialPayData.push(spData);
        }
      });
    }

    this.resourceLabel = this.incidentResourceVo.workPeriodVo.currentAssignmentVo.requestNumber +
      ' ' + this.incidentResourceVo.resourceVo.lastName + ', ' + this.incidentResourceVo.resourceVo.firstName;
    this.timePostingList = [];
    this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.assignmentTimePostVos.forEach(tpvo => {
      const v = {
        id: tpvo.id
        , postDateString: tpvo.postStartDateString
        , postStartTime: tpvo.postStartTime
        , postStopTime: tpvo.postStopTime
        , itemCode: tpvo.kindVo.code
        , specialPay: (tpvo.specialPayVo !== null ? tpvo.specialPayVo.code : '')
        , quantity: tpvo.quantity
        , iac: tpvo.incidentAccountCodeVo.accountCodeVo.accountCode
        , invoiced: false
      };
      this.timePostingList.push(v);
    });


    this.gridOverhead.clearSelected();
    this.initNew();

    setTimeout(() => {
      this.populateForm();
    });
  }

  initNew() {
    // todo: get default code
    this.incidentSelectorService.resourceIncAcctCodeTypeData.forEach(iac => {
    });
    this.assignmentTimePostVo = <AssignmentTimePostVo>{
      id: 0
      , rateClassRateVo: null
      , employmentType: null
      , incidentAccountCodeVo: <IncidentAccountCodeVo>{
          id: this.incidentResourceVo.workPeriodVo.wpDefaultIncidentAccountCodeVo.id
      }
      , kindVo: <KindVo>{
        id: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.kindVo.id
        , code: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.kindVo.code
      }
      , refContractorRateVo: null
      , specialPayVo: <SpecialPayVo>{id: 0}
      , postStartDateVo: <DateTransferVo>{dateString: '', timeString: ''}
      , postStartTime: ''
      , postStopDateVo: <DateTransferVo>{dateString: '', timeString: ''}
      , postStopTime: ''
      , otherRate: 0
      , rateType: ''
      , unitOfMeasure: ''
      , rateAmount: 0
      , guaranteeAmount: 0
      , description: ''
      , assignmentTimeId: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.id
      , isHalfRate: false
      , quantity: 0
      , training: false
      , returnTravelStartOnly: false
      , primaryPosting: false
      , specialPosting: false
      , guaranteePosting: false
      , invoicedAmount: 0
      , timeInvoiceVos: []
      , lastInvoiceDate: null
      , contractorPostType: ''
      , postStartDateString: ''
      , postStopDateString: ''
    }
  }

  onAdClassSelect(event) {
    if ( event.code !== '') {
      const idx = this.rateClassRateVos.findIndex(f => f.id === event.id);
      if ( idx > -1 ) {
        this.timePostForm.get('adRate').patchValue(this.rateClassRateVos[idx].rate);
      } else {
        this.timePostForm.get('adRate').patchValue('');
      }
    } else {
      this.timePostForm.get('adRate').patchValue('');
    }

  }

  populateForm() {
    this.selectedIncidentAccountCode = this.ddIncAcctCode.getDropdownDataObjectById(-2);
    this.selectedItemCode = this.ddItemCode.getDropdownDataObjectById(-2);
    this.selectedStartTime = this.ddStart.getDropdownDataObjectById(-2);
    this.selectedStopTime = this.ddStart.getDropdownDataObjectById(-2);
    this.selectedAdClass = this.ddAdRate.getDropdownDataObjectById(-2);
    this.selectedSpecialPay = this.ddSpecialPay.getDropdownDataObjectById(-2);

    this.timePostForm.setValue(
      {
        postDate: this.assignmentTimePostVo.postStartDateVo.dateString
        , quantity: this.assignmentTimePostVo.quantity
        , otherRate: this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.otherDefaultRate
        , adRate: this.assignmentTimePostVo.rateAmount
        , startTime: ''
        , stopTime: ''
        , trainee: this.assignmentTimePostVo.training
        , postStartTimeOnly: this.assignmentTimePostVo.returnTravelStartOnly
        }
    );

    setTimeout(() => {
      if ( this.assignmentTimePostVo.incidentAccountCodeVo !== null && this.assignmentTimePostVo.incidentAccountCodeVo.id > 0 ) {
        this.selectedIncidentAccountCode = this.ddIncAcctCode.getDropdownDataObjectById(this.assignmentTimePostVo.incidentAccountCodeVo.id);
      }

      if ( this.assignmentTimePostVo.kindVo !== null && this.assignmentTimePostVo.kindVo.id > 0 ) {
        this.selectedItemCode = this.ddItemCode.getDropdownDataObjectById(this.assignmentTimePostVo.kindVo.id);
      }
    });
  }

  save() {

  }

  delete() {

  }

  edit() {

  }

  clear() {
    this.reset();
  }

  cancel() {

  }
}
