import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { IapForm220Vo } from 'src/app/data/iap-form220-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-ics220-ready-alert',
  templateUrl: './ics220-ready-alert.component.html',
  styleUrls: ['./ics220-ready-alert.component.css']
})
export class Ics220ReadyAlertComponent implements OnInit, AfterViewInit {

  iapForm220Vo = <IapForm220Vo>{};
  isFormLocked = false;

  compForm: FormGroup;

  constructor(private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
       medivac: new FormControl({value: '', disabled: false})
      , newIncident: new FormControl({value: '', disabled: false})
      , altitude: new FormControl({value: '', disabled: false})
      , centerPoint: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm220Vo) {
    this.iapForm220Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm220Vo.isFormLocked;

    // reset Form
    this.compForm.setValue({
      medivac: this.iapForm220Vo.medivac
      , newIncident: this.iapForm220Vo.newIncident
      , altitude: this.iapForm220Vo.altitude
      , centerPoint: this.iapForm220Vo.centralPoint
    });

    this.enableDisablePage();
  }

  enableDisablePage() {
    const controlList = ['medivac', 'newIncident', 'altitude', 'centerPoint'];

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
    this.iapForm220Vo.medivac = this.compForm.get('medivac').value;
    this.iapForm220Vo.newIncident = this.compForm.get('newIncident').value;
    this.iapForm220Vo.altitude = this.compForm.get('altitude').value;
    this.iapForm220Vo.centralPoint = this.compForm.get('centerPoint').value;
    return this.iapForm220Vo;
  }

}
