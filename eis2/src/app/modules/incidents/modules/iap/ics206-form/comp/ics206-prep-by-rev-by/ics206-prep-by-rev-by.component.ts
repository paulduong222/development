import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { IapForm206Vo } from 'src/app/data/iap-form206-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';

@Component({
  selector: 'app-ics206-prep-by-rev-by',
  templateUrl: './ics206-prep-by-rev-by.component.html',
  styleUrls: ['./ics206-prep-by-rev-by.component.css']
})
export class Ics206PrepByRevByComponent implements OnInit, AfterViewInit {
  @ViewChild('dtPrepDate') dtPrepDate: EisDatepickerComponent;
  @ViewChild('dtRevDate') dtRevDate: EisDatepickerComponent;

  iapForm206Vo = <IapForm206Vo>{};
  isFormLocked = false;

  compForm: FormGroup;

  constructor(private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      preparedBy: new FormControl({value: '', disabled: false})
      , preparedDate: new FormControl({value: '', disabled: false})
      , preparedTime: new FormControl({value: '', disabled: false})
      , reviewedBy: new FormControl({value: '', disabled: false})
      , reviewedDate: new FormControl({value: '', disabled: false})
      , reviewedTime: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm206Vo) {
    this.iapForm206Vo = vo;
    this.isFormLocked = this.iapForm206Vo.isFormLocked;

    if ( this.iapForm206Vo.preparedDateVo === undefined) {
      this.iapForm206Vo.preparedDateVo = <DateTransferVo>{dateString: '', timeString: ''};
    }
    if ( this.iapForm206Vo.reviewedDateVo === undefined) {
      this.iapForm206Vo.reviewedDateVo = <DateTransferVo>{dateString: '', timeString: ''};
    }

    // reset Form
    this.compForm.setValue({
      preparedBy: this.iapForm206Vo.preparedBy
      , preparedDate: this.iapForm206Vo.preparedDateVo.dateString
      , preparedTime: this.iapForm206Vo.preparedDateVo.timeString
      , reviewedBy: this.iapForm206Vo.reviewedBy
      , reviewedDate: this.iapForm206Vo.reviewedDateVo.dateString
      , reviewedTime: this.iapForm206Vo.reviewedDateVo.timeString
    });

    this.enableDisablePage();
  }

  enableDisablePage() {
    const controlList = [
      'preparedBy', 'reviewedBy', 'preparedDate', 'preparedTime', 'reviewedDate', 'reviewedTime'
    ];

    if ( this.isFormLocked === true ) {
      controlList.forEach(name => {
        this.compForm.controls[name].disable();
      });
    } else {
      controlList.forEach(name => {
        this.compForm.controls[name].enable();
      });
    }
  }

  getIapForm206Vo() {
    if ( this.iapForm206Vo.preparedDateVo === undefined) {
      this.iapForm206Vo.preparedDateVo = <DateTransferVo>{dateString: '', timeString: ''};
    }
    if ( this.iapForm206Vo.reviewedDateVo === undefined) {
      this.iapForm206Vo.reviewedDateVo = <DateTransferVo>{dateString: '', timeString: ''};
    }

    this.iapForm206Vo.preparedBy = this.compForm.get('preparedBy').value;
    this.iapForm206Vo.preparedDateVo.dateString = this.dtPrepDate.getFormattedDate();
    this.iapForm206Vo.preparedTime = this.compForm.get('preparedTime').value;

    this.iapForm206Vo.reviewedBy = this.compForm.get('reviewedBy').value;
    this.iapForm206Vo.reviewedDateVo.dateString = this.dtRevDate.getFormattedDate();
    this.iapForm206Vo.reviewedTime = this.compForm.get('reviewedTime').value;
    return this.iapForm206Vo;
  }

}
