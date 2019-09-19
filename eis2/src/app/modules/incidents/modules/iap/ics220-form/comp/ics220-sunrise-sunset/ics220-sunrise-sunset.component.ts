import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { IapForm220Vo } from 'src/app/data/iap-form220-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';

@Component({
  selector: 'app-ics220-sunrise-sunset',
  templateUrl: './ics220-sunrise-sunset.component.html',
  styleUrls: ['./ics220-sunrise-sunset.component.css']
})
export class Ics220SunriseSunsetComponent implements OnInit, AfterViewInit {

  iapForm220Vo = <IapForm220Vo>{};
  isFormLocked = false;

  compForm: FormGroup;

  constructor(private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      sunrise: new FormControl({value: '', disabled: false})
      , sunset: new FormControl({value: '', disabled: false})
      , remarks: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm220Vo) {
    this.iapForm220Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm220Vo.isFormLocked;

    // reset Form
    this.compForm.setValue({
      sunrise: this.iapForm220Vo.sunrise
      , sunset: this.iapForm220Vo.sunset
      , remarks: this.iapForm220Vo.remarks
    });

    this.enableDisablePage();
  }

  enableDisablePage() {
    const controlList = ['sunrise', 'sunset', 'remarks'];

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

  getIapForm220Vo() {
    this.iapForm220Vo.sunrise = this.compForm.get('sunrise').value;
    this.iapForm220Vo.sunset = this.compForm.get('sunset').value;
    this.iapForm220Vo.remarks = this.compForm.get('remarks').value;
    return this.iapForm220Vo;
  }

}
