import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { IapForm205Vo } from 'src/app/data/iap-form205-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';

@Component({
  selector: 'app-ics205-prep-by',
  templateUrl: './ics205-prep-by.component.html',
  styleUrls: ['./ics205-prep-by.component.css']
})
export class Ics205PrepByComponent implements OnInit, AfterViewInit {
  @ViewChild('dtPrepDate') dtPrepDate: EisDatepickerComponent;

  iapForm205Vo = <IapForm205Vo>{};
  isFormLocked = false;

  compForm: FormGroup;

  constructor(private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      preparedBy: new FormControl({value: '', disabled: false})
      , preparedDateString: new FormControl({value: '', disabled: false})
      , preparedTime: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm205Vo) {
    this.iapForm205Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm205Vo.isFormLocked;

    // reset Form
    this.compForm.setValue({
      preparedBy: this.iapForm205Vo.preparedBy
      , preparedDateString: this.iapForm205Vo.preparedDateVo.dateString
      , preparedTime: this.iapForm205Vo.preparedDateVo.timeString
    });

    this.enableDisablePage();
  }

  enableDisablePage() {
    const controlList = ['preparedBy', 'preparedDateString', 'preparedTime'];

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

  getIapForm205Vo() {
    this.iapForm205Vo.preparedBy = this.compForm.get('preparedBy').value;
    this.iapForm205Vo.preparedDateVo.dateString = this.dtPrepDate.getFormattedDate();
    this.iapForm205Vo.preparedDateVo.timeString = this.compForm.get('preparedTime').value;

    return this.iapForm205Vo;
  }

}
