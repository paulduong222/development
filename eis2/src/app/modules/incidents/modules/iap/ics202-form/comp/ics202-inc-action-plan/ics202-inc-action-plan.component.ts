import { Component, OnInit, AfterViewInit } from '@angular/core';
import { IapForm202Vo } from 'src/app/data/iap-form202-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-ics202-inc-action-plan',
  templateUrl: './ics202-inc-action-plan.component.html',
  styleUrls: ['./ics202-inc-action-plan.component.css']
})
export class Ics202IncActionPlanComponent implements OnInit, AfterViewInit {

  iapForm202Vo = <IapForm202Vo>{};
  isFormLocked = false;

  compForm: FormGroup;

  constructor(private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      isForm202Attached: new FormControl({value: false, disabled: false})
      , isForm203Attached: new FormControl({value: false, disabled: false})
      , isForm204Attached: new FormControl({value: false, disabled: false})
      , isForm205Attached: new FormControl({value: false, disabled: false})
      , isForm205aAttached: new FormControl({value: false, disabled: false})
      , isForm206Attached: new FormControl({value: false, disabled: false})
      , isForm207Attached: new FormControl({value: false, disabled: false})
      , isForm208Attached: new FormControl({value: false, disabled: false})
      , isForm220Attached: new FormControl({value: false, disabled: false})
      , isIncidentMapAttached: new FormControl({value: false, disabled: false})
      , isWeatherForecastAttached: new FormControl({value: false, disabled: false})
      , isOtherFormAttached1: new FormControl({value: false, disabled: false})
      , isOtherFormAttached2: new FormControl({value: false, disabled: false})
      , isOtherFormAttached3: new FormControl({value: false, disabled: false})
      , isOtherFormAttached4: new FormControl({value: false, disabled: false})
      , otherFormName1: new FormControl({value: false, disabled: false})
      , otherFormName2: new FormControl({value: false, disabled: false})
      , otherFormName3: new FormControl({value: false, disabled: false})
      , otherFormName4: new FormControl({value: false, disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm202Vo) {
    this.iapForm202Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm202Vo.isFormLocked;

    // reset Form
    this.compForm.setValue({
      isForm202Attached: this.iapForm202Vo.isForm202Attached
      , isForm203Attached: this.iapForm202Vo.isForm203Attached
      , isForm204Attached: this.iapForm202Vo.isForm205Attached
      , isForm205Attached: this.iapForm202Vo.isForm205Attached
      , isForm205aAttached: this.iapForm202Vo.isForm205aAttached
      , isForm206Attached: this.iapForm202Vo.isForm206Attached
      , isForm207Attached: this.iapForm202Vo.isForm207Attached
      , isForm208Attached: this.iapForm202Vo.isForm208Attached
      , isForm220Attached: this.iapForm202Vo.isForm220Attached
      , isIncidentMapAttached: this.iapForm202Vo.isIncidentMapAttached
      , isWeatherForecastAttached: this.iapForm202Vo.isWeatherForecastAttached
      , isOtherFormAttached1: this.iapForm202Vo.isOtherFormAttached1
      , isOtherFormAttached2: this.iapForm202Vo.isOtherFormAttached2
      , isOtherFormAttached3: this.iapForm202Vo.isOtherFormAttached3
      , isOtherFormAttached4: this.iapForm202Vo.isOtherFormAttached4
      , otherFormName1: this.iapForm202Vo.otherFormName1
      , otherFormName2: this.iapForm202Vo.otherFormName2
      , otherFormName3: this.iapForm202Vo.otherFormName3
      , otherFormName4: this.iapForm202Vo.otherFormName4
    });

    this.enableDisablePage();
  }

  enableDisablePage() {
    const controlList = ['isForm202Attached', 'isForm203Attached', 'isForm204Attached'
      , 'isForm205Attached', 'isForm205aAttached', 'isForm206Attached', 'isForm207Attached'
      , 'isForm208Attached', 'isForm220Attached', 'isIncidentMapAttached', 'isWeatherForecastAttached'
      , 'isOtherFormAttached1', 'isOtherFormAttached2', 'isOtherFormAttached3', 'isOtherFormAttached4'
      , 'otherFormName1', 'otherFormName2', 'otherFormName3', 'otherFormName4'];

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

  getIapForm202Vo() {
    this.iapForm202Vo.isForm202Attached = this.compForm.get('isForm202Attached').value;
    this.iapForm202Vo.isForm203Attached = this.compForm.get('isForm203Attached').value;
    this.iapForm202Vo.isForm204Attached = this.compForm.get('isForm204Attached').value;
    this.iapForm202Vo.isForm205Attached = this.compForm.get('isForm205Attached').value;
    this.iapForm202Vo.isForm205aAttached = this.compForm.get('isForm205aAttached').value;
    this.iapForm202Vo.isForm206Attached = this.compForm.get('isForm206Attached').value;
    this.iapForm202Vo.isForm207Attached = this.compForm.get('isForm207Attached').value;
    this.iapForm202Vo.isForm208Attached = this.compForm.get('isForm208Attached').value;
    this.iapForm202Vo.isForm220Attached = this.compForm.get('isForm220Attached').value;
    this.iapForm202Vo.isIncidentMapAttached = this.compForm.get('isIncidentMapAttached').value;
    this.iapForm202Vo.isWeatherForecastAttached = this.compForm.get('isWeatherForecastAttached').value;
    this.iapForm202Vo.isOtherFormAttached1 = this.compForm.get('isOtherFormAttached1').value;
    this.iapForm202Vo.isOtherFormAttached2 = this.compForm.get('isOtherFormAttached2').value;
    this.iapForm202Vo.isOtherFormAttached3 = this.compForm.get('isOtherFormAttached3').value;
    this.iapForm202Vo.isOtherFormAttached4 = this.compForm.get('isOtherFormAttached4').value;
    this.iapForm202Vo.otherFormName1 = this.compForm.get('otherFormName1').value;
    this.iapForm202Vo.otherFormName2 = this.compForm.get('otherFormName2').value;
    this.iapForm202Vo.otherFormName3 = this.compForm.get('otherFormName3').value;
    this.iapForm202Vo.otherFormName4 = this.compForm.get('otherFormName4').value;
    return this.iapForm202Vo;
  }
}
